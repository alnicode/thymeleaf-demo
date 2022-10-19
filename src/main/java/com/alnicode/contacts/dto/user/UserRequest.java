package com.alnicode.contacts.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserRequest(@NotBlank String name, @NotBlank @Email String email, @NotBlank String phone, @NotBlank String username,
                          @NotBlank String password, @NotBlank String repeatPassword) {

    public boolean passwordsMatch() {
        return password.equals(repeatPassword);
    }

}
