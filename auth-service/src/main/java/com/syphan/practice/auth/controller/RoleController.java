package com.syphan.practice.auth.controller;

import com.syphan.common.api.utils.EntityValidationUtils;
import com.syphan.common.rest.response.OpenApiWithDataResponse;
import com.syphan.practice.auth.dto.RoleCreateDto;
import com.syphan.practice.auth.model.Role;
import com.syphan.practice.auth.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Role Management V1")
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("Create New Role")
//    @PreAuthorize("hasAuthority('UPMS_ROLE_READ')")
    @PostMapping
    public ResponseEntity<OpenApiWithDataResponse<Role>> addRole(@Valid @RequestBody RoleCreateDto reqParam,
                                                                 BindingResult bindingResult) {
        EntityValidationUtils.processBindingResults(bindingResult);
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(roleService.create(reqParam)));
    }
}
