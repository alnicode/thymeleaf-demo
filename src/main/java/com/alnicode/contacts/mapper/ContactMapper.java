package com.alnicode.contacts.mapper;

import com.alnicode.contacts.dto.contact.ContactRequest;
import com.alnicode.contacts.dto.contact.ContactResponse;
import com.alnicode.contacts.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactResponse toResponse(Contact entity);

    List<ContactResponse> toResponses(List<Contact> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Contact toEntity(ContactRequest request);

}
