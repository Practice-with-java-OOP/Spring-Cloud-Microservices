package com.syphan.practice.auth.controller;

import com.syphan.practice.auth.base.OpenApiWithDataResponse;
import com.syphan.practice.auth.dto.UserSignIn;
import com.syphan.practice.auth.security.CurrentUser;
import com.syphan.practice.auth.security.JwtTokenProperties;
import com.syphan.practice.auth.security.JwtTokenProvider;
import com.syphan.practice.auth.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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

    @ApiOperation("User SignIn")
    @PostMapping("/sign-in")
    public ResponseEntity<OpenApiWithDataResponse<String>> authenticateUser(@Valid @RequestBody UserSignIn reqParam) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(reqParam.getUsername(), reqParam.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(
                String.format("%s %s", jwtProperties.getTokenPrefix(), jwtTokenProvider.generateToken(authentication))));
    }

    @GetMapping("/user")
    public UserPrincipal user(@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        return userPrincipal;
    }
}
