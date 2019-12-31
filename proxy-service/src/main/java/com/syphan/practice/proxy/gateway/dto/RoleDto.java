package com.syphan.practice.proxy.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto extends BaseEntity  {
    private String name;

    private String code;

    private Set<PermissionDto> permissions = new HashSet<>();

    private Set<UserDto> users = new HashSet<>();
}
