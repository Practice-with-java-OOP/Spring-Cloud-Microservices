package com.syphan.practice.auth.service;

import com.syphan.common.api.exception.BIZException;
import com.syphan.common.dao.service.BaseService;
import com.syphan.practice.auth.dto.AdminCreateUserDto;
import com.syphan.practice.auth.dto.UserCreateDto;
import com.syphan.practice.auth.model.User;

public interface UserService extends BaseService<User, Integer> {

    String sendUserSignUpMailCaptcha(String email) throws BIZException;

    User signUp(UserCreateDto data) throws BIZException;

    User adminCreateUser(AdminCreateUserDto data) throws BIZException;

    User findByUsername(String username) throws BIZException;

    User getByUsername(String username) throws BIZException;
}
