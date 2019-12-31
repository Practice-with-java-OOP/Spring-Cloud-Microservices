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
public class UserDto extends BaseEntity  {
    private String fullName;

    private String userSocial;

    private String username;

    private String password;

    private String email;

    private String phoneNum;

    private String avatar;

    private Set<RoleDto> roles = new HashSet<>();
}
