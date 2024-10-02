package com.swd392.ticket_resell_be.dtos.requests;

import com.swd392.ticket_resell_be.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record RegisterDtoRequest(
        @NotEmpty(message = "USERNAME_EMPTY")
        String username,
        @NotEmpty(message = "PASSWORD_EMPTY")
        @Length(min = 8, message = "PASSWORD_LENGTH")
        String password,
        @NotEmpty(message = "PASSWORD_EMPTY")
        @Length(min = 8, message = "PASSWORD_LENGTH")
        String confirmPassword,
        @Email(message = "EMAIL_INVALID")
        @NotEmpty(message = "EMAIL_EMPTY")
        String email
) implements Serializable {
}