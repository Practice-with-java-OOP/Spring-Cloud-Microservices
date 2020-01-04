package com.syphan.practice.auth.controller;

import com.syphan.common.api.utils.EntityValidationUtils;
import com.syphan.common.rest.response.OpenApiWithDataResponse;
import com.syphan.common.rest.security.CurrentUser;
import com.syphan.common.rest.security.UserPrincipal;
import com.syphan.practice.auth.dto.AdminCreateUserDto;
import com.syphan.practice.auth.dto.UserCreateDto;
import com.syphan.practice.auth.model.User;
import com.syphan.practice.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.security.Principal;

@Api(tags = "User Join Management V1")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public Principal getUser(Principal principal,@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        return principal;
    }

    @PostMapping("mail-captcha")
    public ResponseEntity<OpenApiWithDataResponse<String>> mailCaptcha(@NotBlank @Email @RequestParam String email) {
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(userService.sendUserSignUpMailCaptcha(email)));
    }

    @ApiOperation("User SignUp")
    @PostMapping
    public ResponseEntity<OpenApiWithDataResponse<User>> signUp(@Valid @RequestBody UserCreateDto reqPram,
                                                                BindingResult bindingResult) {
        EntityValidationUtils.processBindingResults(bindingResult);
        return ResponseEntity.ok(new OpenApiWithDataResponse<User>(userService.signUp(reqPram)));
    }

    @ApiOperation("Admin create user")
    @PostMapping("admin-create")
    public ResponseEntity<OpenApiWithDataResponse<User>> adminCreateUser(@Valid @RequestBody AdminCreateUserDto reqPram,
                                                                         BindingResult bindingResult) {
        EntityValidationUtils.processBindingResults(bindingResult);
        return ResponseEntity.ok(new OpenApiWithDataResponse<User>(userService.adminCreateUser(reqPram)));
    }

    @GetMapping("{id}")
    public ResponseEntity<OpenApiWithDataResponse<User>> getByUserId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(userService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<OpenApiWithDataResponse<User>> getByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(userService.getByUsername(username)));
    }
}
