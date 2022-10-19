package com.alnicode.contacts.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserRequest(@NotBlank String name, @NotBlank String phone, @NotBlank String username, @NotBlank @Email String email,
                          @NotBlank String password, @NotBlank String repeatPassword) {

    public boolean passwordsMatch() {
        return repeatPassword.equals(password);
    }

}
