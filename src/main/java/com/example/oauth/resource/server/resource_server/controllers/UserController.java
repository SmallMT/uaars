package com.example.oauth.resource.server.resource_server.controllers;

import com.example.oauth.resource.server.resource_server.domain.User;
import com.example.oauth.resource.server.resource_server.repository.UserRepository;
import com.example.oauth.resource.server.resource_server.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/api")
public class UserController
{
	/*
	 * @PreAuthorize
	 * http://docs.spring.io/spring-security/site/docs/current/reference/html/el-access.html
	 *
	 * hasRole([role])
	 * hasAnyRole([role1,role2])
	 *
	 * hasAuthority([authority])
	 * hasAnyAuthority([authority1,authority2])
	 *
	 * */

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "me",method = RequestMethod.GET)
	public Object me(Principal principal){
	   return userRepository.findOneByLogin(principal.getName()).get();
    }

//    @PreAuthorize(value = "hasRole('USER') and #oauth2.hasScope('read')")
////	@GetMapping(value = "me")
//    @RequestMapping(value = "me")
//    public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal OAuth2Authentication authentication)
//    {
//        String username = authentication.getUserAuthentication().getPrincipal().toString();
//        Set<String> scopes = authentication.getOAuth2Request().getScope();
//
//        Map<String, Object> me = new HashMap<>();
//        me.put("message", "Hello, Authenticator");
//        me.put("username", username);
//        me.put("scopes", scopes);
//
//        return ResponseEntity.ok(me);
//    }

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "accessable/by/authorities/admin")
    public ResponseEntity<Map<String, String>> accessableByAuthoritiesAdmin()
    {
        Map<String, String> admin = new HashMap<>();
        admin.put("message", "Hello, Admin");

        return ResponseEntity.ok(admin);
    }

    @PreAuthorize(value = "hasAuthority('USER')")
    @RequestMapping(value = "accessable/by/authorities/user")
    public ResponseEntity<Map<String, String>> accessableByAuthoritiesUser()
    {
        Map<String, String> admin = new HashMap<>();
        admin.put("message", "Hello, User");

        return ResponseEntity.ok(admin);
    }

    @PreAuthorize(value = "#oauth2.hasScope('read')")
    @RequestMapping(value = "accessable/by/scopes/read")
    public ResponseEntity<Map<String, String>> accessableByScopesRead()
    {
        Map<String, String> admin = new HashMap<>();
        admin.put("message", "Hello, Reader");

        return ResponseEntity.ok(admin);
    }

    @PreAuthorize(value = "#oauth2.hasScope('write')")
    @RequestMapping(value = "accessable/by/scopes/write")
    public ResponseEntity<Map<String, String>> accessableByScopesWrite(@RequestParam(value = "text") String text)
    {
        Map<String, String> admin = new HashMap<>();
        admin.put("message", "Hello, Writer");
        admin.put("param", text);

        return ResponseEntity.ok(admin);
    }

//    /**
//     * 注册
//     * @param registerVM
//     * @return
//     */
//    @RequestMapping(value = "/register")
//    public Object register(@RequestBody @Valid RegisterVM registerVM) throws URISyntaxException {
//
//        User user=userRepository.findOneByLogin("lyt108825").get();
//        mailService.sendPasswordResetMail(user);
//	   return registerVM;
//
//    }
}

