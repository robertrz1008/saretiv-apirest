package my.project.dto.auth.entityManager;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"user_id", "role_id"})
public record UserRole(
        int userId,
        int roleId
) {
}
