package com.alnicode.contacts.service;

import com.alnicode.contacts.dto.UserRequest;
import com.alnicode.contacts.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserResponse create(UserRequest request) throws Exception;
    Optional<UserResponse> get(long id);
    Optional<UserResponse> update(long id, UserRequest request);
    boolean delete(long id);
    void addContact(long userId, long contactId) throws Exception;
    void removeContact(long userId, long contactId) throws Exception;
    Optional<List<UserResponse>> getContacts(long userId);
}
