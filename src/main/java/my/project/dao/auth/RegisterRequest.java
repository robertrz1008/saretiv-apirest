package my.project.dao.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import my.project.dao.AuthCreateRoleRequest;

import java.util.Date;

public record RegisterRequest(
        @NotBlank String name,
        @NotBlank String lastname,
        @NotBlank String telephone,
        @NotBlank String document,
        @NotBlank String username,
        @NotBlank String password,
        Date entryDate,
        boolean status,
        @Valid AuthCreateRoleRequest roleRequest
        ) {
}
