package com.syphan.practice.auth.controller;

import com.syphan.common.api.response.OpenApiWithDataResponse;
import com.syphan.common.api.utils.EntityValidationUtils;
import com.syphan.practice.auth.dto.PermissionCreateDto;
import com.syphan.practice.auth.model.Permission;
import com.syphan.practice.auth.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Permission Management V1")
@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @ApiOperation("Create New Permission")
//    @PreAuthorize("hasAuthority('UPMS_PERMISSION_EDIT')")
    @PostMapping
    public ResponseEntity<OpenApiWithDataResponse<Permission>> create(@Valid @RequestBody PermissionCreateDto reqPram,
                                                                      BindingResult bindingResult) {
        EntityValidationUtils.processBindingResults(bindingResult);
        return ResponseEntity.ok(new OpenApiWithDataResponse<Permission>(service.create(reqPram)));
    }

}
