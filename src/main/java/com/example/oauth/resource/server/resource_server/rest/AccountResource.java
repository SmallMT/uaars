package com.example.oauth.resource.server.resource_server.rest;


import com.alibaba.fastjson.JSONObject;
import com.example.oauth.resource.server.resource_server.ApplicationProperties;
import com.example.oauth.resource.server.resource_server.Constants;
import com.example.oauth.resource.server.resource_server.SecurityUtils;
import com.example.oauth.resource.server.resource_server.domain.RealName;
import com.example.oauth.resource.server.resource_server.domain.User;
import com.example.oauth.resource.server.resource_server.repository.RealNameRepository;
import com.example.oauth.resource.server.resource_server.repository.UserRepository;
import com.example.oauth.resource.server.resource_server.rest.errors.*;
import com.example.oauth.resource.server.resource_server.rest.vm.KeyAndPasswordVM;
import com.example.oauth.resource.server.resource_server.rest.vm.LoginAndPassword;
import com.example.oauth.resource.server.resource_server.rest.vm.ManagedUserVM;
import com.example.oauth.resource.server.resource_server.rest.vm.PhoneAndCodeVM;
import com.example.oauth.resource.server.resource_server.service.MailService;
import com.example.oauth.resource.server.resource_server.service.UserService;
import com.example.oauth.resource.server.resource_server.service.dto.UserDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    private final RealNameRepository realNameRepository;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private ApplicationProperties applicationProperties;

    public AccountResource(ApplicationProperties applicationProperties, UserRepository userRepository, RealNameRepository realNameRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.realNameRepository = realNameRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.applicationProperties = applicationProperties;
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @throws InvalidPasswordException  400 (Bad Request) if the password is incorrect
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already used
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).ifPresent(u -> {
            throw new LoginAlreadyUsedException();
        });
//        userRepository.findOneByEmail(managedUserVM.getEmail()).ifPresent(u -> {
//            throw new EmailAlreadyUsedException();
//        });
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")

    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")

    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
                .map(UserDTO::new)
                .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws RuntimeException          500 (Internal Server Error) if the user login wasn't found
     */
    @PostMapping("/account")

    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                userDTO.getLangKey(), userDTO.getImageUrl());
    }

    /**
     * POST  /account/change-password : changes the current user's password
     *
     * @param  loginAndPassword the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PostMapping(path = "/account/change-password")

    public void changePassword(@RequestBody LoginAndPassword loginAndPassword) {
        if (!checkPasswordLength(loginAndPassword.getPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(loginAndPassword.getPassword(),loginAndPassword.getLogin());
    }

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        mailService.sendPasswordResetMail(
                userService.requestPasswordReset(mail)
                        .orElseThrow(EmailNotFoundException::new)
        );
    }

    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws RuntimeException         500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
                userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
                password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }

    /**
     * 给电话号码上发送短信验证码
     *
     * @param phone 电话号码
     */
    @PostMapping(path = "/account/set-tel/init")
    public void requestTelSet(@RequestBody String phone) throws IOException {

        //请求地址
        String url = "https://api.leancloud.cn/1.1/requestSmsCode";

        //请求体参数
        JSONObject obj = new JSONObject();
        obj.put("mobilePhoneNumber", phone);
        obj.put("name", "三亚市政务中心");
        obj.put("op", "手机绑定");
        obj.put("ttl", 1);

        OkHttpClient client = new OkHttpClient();
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, obj.toJSONString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("X-LC-Id", "iej5bQfs0fnQ1jUiIoCRVNLS-gzGzoHsz")
                .addHeader("X-LC-Key", "YjEaXzNDJpha3MRlNleVxuDF")
                .addHeader("Content-Type", "application/json")
                .build();

        String bodyString = client.newCall(request).execute().body().string();
        JSONObject json = com.alibaba.fastjson.JSON.parseObject(bodyString);

        if (json.containsKey("error")) {
            throw new VerificationCodeUpperLimitException(json.getString("error"));
        }
    }

    /**
     * 验证短信验证码
     *
     * @param phoneAndCodeVM
     */
    @PostMapping(path = "/account/set-tel/finish")
    public void finishRequestTelSet(@RequestBody @Valid PhoneAndCodeVM phoneAndCodeVM) throws IOException {
        //请求地址
        String url = "https://api.leancloud.cn/1.1/verifySmsCode/" + phoneAndCodeVM.getCode();


        JSONObject obj = new JSONObject();
        obj.put("mobilePhoneNumber", phoneAndCodeVM.getPhone());

        /*http请求*/
        OkHttpClient client = new OkHttpClient();
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, obj.toJSONString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("X-LC-Id", "iej5bQfs0fnQ1jUiIoCRVNLS-gzGzoHsz")
                .addHeader("X-LC-Key", "YjEaXzNDJpha3MRlNleVxuDF")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        JSONObject json = com.alibaba.fastjson.JSON.parseObject(responseBody);
        //验证失败
        if (!json.isEmpty()) {
            throw new VerficationCodeInvalidException(json.getString("error"));
        }
        //将结果写入
        userService.setTel(phoneAndCodeVM.getLogin(), phoneAndCodeVM.getPhone());
    }

    /**
     * 上传文件
     *
     * @param back
     * @param front
     */
    @PostMapping(path = "/account/uploadImage",consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RealName uploadImage(@RequestParam(value = "frontFile",required = true)  MultipartFile front, @RequestParam(value = "backFile" ,required = true) MultipartFile back, @Valid RealName realName) throws IOException {

        //查看是否有该用户的认证信息
        if(null!=realNameRepository.findByLogin(realName.getLogin())&&realName.getId()==null){
            throw new RealNameExistException();
        }else {
            String filePath = applicationProperties.getRealName().getFilePath();
            File dir = new File(filePath + "//" + realName.getLogin()+"//");
            if (!dir.exists()) {
                dir.mkdir();
            }

            File frontFile, backFile;

            frontFile = new File(dir.getAbsolutePath()+"//"+UUID.randomUUID().toString() + front.getOriginalFilename());
            backFile = new File(dir.getAbsolutePath()+"//"+UUID.randomUUID().toString()+back.getOriginalFilename());

            front.transferTo(frontFile);
            back.transferTo(backFile);

            realName.setBackImage(backFile.getName());
            realName.setFrontImage(frontFile.getName());
            RealName result= realNameRepository.save(realName);
            userRepository.updateVerified(true,realName.getLogin(),realName.getName(),realName.getIdentity());

            return result;
        }


    }

    /**
     * 下载图片
     * @param login
     * @param imageName
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/account/realNameInfor/{login:" + Constants.LOGIN_REGEX + "}/{imageName}", method = RequestMethod.GET)
    public void testphoto(@PathVariable String login, @PathVariable String imageName, HttpServletResponse response) throws IOException {
        String dir=applicationProperties.getRealName().getFilePath()+"//"+login+"//"+imageName;
        File file=new File(dir);
        InputStream inputStream=new FileInputStream(file);
        IOUtils.copy(inputStream,response.getOutputStream());
    }

    /**
     * 根据用户名查看当前用户的实名认证信息
     * @param login
     * @return
     */
    @GetMapping(path = "/account/realNameInfor/{login:" + Constants.LOGIN_REGEX + "}")
    public RealName getRealNameInfo(@PathVariable String login){
        return  realNameRepository.findByLogin(login);
    }
}
