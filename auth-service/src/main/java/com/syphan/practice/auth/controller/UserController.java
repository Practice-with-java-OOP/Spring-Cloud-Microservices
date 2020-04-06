package com.syphan.practice.auth.controller;

import com.syphan.common.api.utils.EntityValidationUtils;
import com.syphan.common.rest.response.OpenApiWithDataResponse;
import com.syphan.common.rest.response.OpenApiWithPageResponse;
import com.syphan.practice.auth.dto.AdminCreateUserDto;
import com.syphan.practice.auth.dto.UserCreateDto;
import com.syphan.practice.auth.model.User;
import com.syphan.practice.auth.security.UserPrincipal;
import com.syphan.practice.auth.service.UserService;
import com.syphan.practice.auth.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Api(tags = "User Join Management V1")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("Get user current")
    @GetMapping("/current")
    public UserPrincipal getUser() {
        return Utils.getUserPrincipal();
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

    @GetMapping("all")
    @ApiImplicitParams({@ApiImplicitParam(
            name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(
                    name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "20"),
            @ApiImplicitParam(
                    name = "sort", dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). "
                            + "Default sort order is ascending. "
                            + "Multiple sort criteria are supported.",
                    defaultValue = "createdAt,desc"
            )})
    public OpenApiWithDataResponse<OpenApiWithPageResponse<?>> getWarehouses(
            @RequestParam(value = "name", required = false) String name,
            @ApiIgnore Pageable pageable) {
        return null;
    }
}
