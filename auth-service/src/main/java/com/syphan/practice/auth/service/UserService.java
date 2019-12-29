package com.syphan.practice.auth.service;

import com.syphan.practice.auth.base.BaseService;
import com.syphan.practice.auth.dto.AdminCreateUserDto;
import com.syphan.practice.auth.dto.UserCreateDto;
import com.syphan.practice.auth.model.User;

public interface UserService extends BaseService<User, Integer> {

    String sendUserSignUpMailCaptcha(String email);

    User signUp(UserCreateDto data);

    User adminCreateUser(AdminCreateUserDto data);

    User findByUsername(String username);

}
