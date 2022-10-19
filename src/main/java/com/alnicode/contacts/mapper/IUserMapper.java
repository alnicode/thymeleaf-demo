package com.alnicode.contacts.mapper;

import com.alnicode.contacts.dto.UserRequest;
import com.alnicode.contacts.dto.UserResponse;
import com.alnicode.contacts.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    UserResponse toResponse(User entity);

    List<UserResponse> toResponses(List<User> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    User toEntity(UserRequest request);

}
