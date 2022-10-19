package com.alnicode.contacts.mapper;

import com.alnicode.contacts.dto.user.UserRequest;
import com.alnicode.contacts.dto.user.UserResponse;
import com.alnicode.contacts.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    User toEntity(UserRequest request);

}
