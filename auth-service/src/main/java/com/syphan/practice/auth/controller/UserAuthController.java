package com.syphan.practice.auth.controller;

import com.syphan.common.api.utils.EntityValidationUtils;
import com.syphan.common.rest.response.OpenApiWithDataResponse;
import com.syphan.practice.auth.dto.UserSignIn;
import com.syphan.practice.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Authentication Management V1")
@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @ApiOperation("User SignIn")
    @PostMapping("/sign-in")
    public ResponseEntity<OpenApiWithDataResponse<OAuth2AccessToken>> authenticateUser(@Valid @RequestBody UserSignIn reqParam,
                                                                                       BindingResult bindingResult) {
        EntityValidationUtils.processBindingResults(bindingResult);
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(userService.signIn(reqParam)));
    }
}
