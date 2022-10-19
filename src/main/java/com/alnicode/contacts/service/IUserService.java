package com.alnicode.contacts.service;

import com.alnicode.contacts.dto.user.UserRequest;
import com.alnicode.contacts.dto.user.UserResponse;

import java.util.Optional;

public interface IUserService {
    UserResponse create(UserRequest request) throws Exception;
    Optional<UserResponse> get(long id);
    Optional<UserResponse> update(long id, UserRequest request);
    boolean delete(long id);
}
