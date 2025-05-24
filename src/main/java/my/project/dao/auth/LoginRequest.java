package my.project.dao.auth;

import org.springframework.lang.NonNull;

public record LoginRequest(
        @NonNull String username,
        @NonNull String password
) {
}
