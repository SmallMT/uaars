package com.example.oauth.resource.server.resource_server.rest;


import com.alibaba.fastjson.JSONObject;
import com.example.oauth.resource.server.resource_server.SecurityUtils;
import com.example.oauth.resource.server.resource_server.domain.User;
import com.example.oauth.resource.server.resource_server.repository.UserRepository;
import com.example.oauth.resource.server.resource_server.rest.errors.*;
import com.example.oauth.resource.server.resource_server.rest.vm.KeyAndPasswordVM;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

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

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already used
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).ifPresent(u -> {throw new LoginAlreadyUsedException();});
        userRepository.findOneByEmail(managedUserVM.getEmail()).ifPresent(u -> {throw new EmailAlreadyUsedException();});
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
     * @throws RuntimeException 500 (Internal Server Error) if the user login wasn't found
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
     * @param password the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PostMapping(path = "/account/change-password")
 
    public void changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(password);
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
     * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
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

        if (json.containsKey("error")){
            throw new VerificationCodeUpperLimitException(json.getString("error"));
        }
    }

    /**
     * 验证短信验证码
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
        if (!json.isEmpty()){
            throw new VerficationCodeInvalidException(json.getString("error"));
        }
        //将结果写入
       userService.setTel(phoneAndCodeVM.getLogin(),phoneAndCodeVM.getPhone());
    }
}
