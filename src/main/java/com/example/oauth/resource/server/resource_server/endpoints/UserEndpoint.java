package com.example.oauth.resource.server.resource_server.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/")
public class UserEndpoint
{
    @RequestMapping(value = "principal")
    public Principal principal(Principal principal)
    {
//        Map<String, String> user = new HashMap<>();
//        user.put("email", principal.getName());
//        user.put("picture", "/pictures/".concat(principal.getName().concat("/avatar.png")));
//
//        return ResponseEntity.ok(user);
        return  principal;
    }

}

