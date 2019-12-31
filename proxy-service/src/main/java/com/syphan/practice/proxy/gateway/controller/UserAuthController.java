package com.syphan.practice.proxy.gateway.controller;

import com.syphan.practice.proxy.gateway.dto.UserSignIn;
import com.syphan.practice.proxy.gateway.security.JwtTokenProperties;
import com.syphan.practice.proxy.gateway.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "Authentication Management V1")
@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProperties jwtProperties;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('UPMS_ROLE_READ111')")
    public String test() {
        httpServletRequest.getHeader("Authorization");
        System.out.println();
        return "oke";
    }

    @ApiOperation("User SignIn")
    @PostMapping("/sign-in")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody UserSignIn reqParam) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(reqParam.getUsername(), reqParam.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(String.format("%s %s", jwtProperties.getTokenPrefix(), jwtTokenProvider.generateToken(authentication)));
    }
}
