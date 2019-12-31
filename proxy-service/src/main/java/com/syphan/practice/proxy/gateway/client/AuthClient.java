package com.syphan.practice.proxy.gateway.client;

import com.syphan.common.api.response.OpenApiWithDataResponse;
import com.syphan.practice.proxy.gateway.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("api/v1/users/{id}")
    OpenApiWithDataResponse<UserDto> getUserById(@PathVariable("id") Integer id);

    @GetMapping("api/v1/users")
    UserDto getByUsername(@RequestParam("username") String username);

}
