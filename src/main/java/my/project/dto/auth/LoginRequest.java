package my.project.dto.auth;

import org.springframework.lang.NonNull;

public record LoginRequest(
        @NonNull String username,
        @NonNull String password
) {
}
