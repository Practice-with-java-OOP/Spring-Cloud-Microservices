package com.syphan.practice.proxy.gateway.security;

import com.syphan.common.api.response.OpenApiWithDataResponse;
import com.syphan.practice.proxy.gateway.client.AuthClient;
import com.syphan.practice.proxy.gateway.dto.RoleDto;
import com.syphan.practice.proxy.gateway.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private AuthClient authClient;

    @Override
    public UserDetails loadUserById(Integer id) throws UsernameNotFoundException {
        OpenApiWithDataResponse<UserDto> user = authClient.getUserById(id);
        if (user != null) return create(user.getData());
        else throw new UsernameNotFoundException(String.format("%s%s", "User not found with id: ", id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) throw new UsernameNotFoundException("username can not be empty.");
        UserDto user = authClient.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s %s", "User not found with username:", username));
        }
        return create(user);
    }

    public static UserPrincipal create(UserDto user) {
        Set<String> roleNameSet = new HashSet<>();
        Set<GrantedAuthority> authorities = user.getRoles().stream().map(RoleDto::getPermissions)
                .flatMap(Collection::stream).collect(Collectors.toSet()).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getCode())).collect(Collectors.toSet());

        for (RoleDto role : user.getRoles()) {
            roleNameSet.add(role.getCode());
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getAvatar(),
                user.getPassword(),
                roleNameSet,
                authorities
        );
    }
}
