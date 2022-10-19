package com.alnicode.contacts.dto.contact;

import com.alnicode.contacts.dto.user.UserResponse;

public record ContactRequest(String name, String phone, String email) {

    public ContactRequest(String name, UserResponse user) {
        this(name, user.phone(), user.email());
    }

}
