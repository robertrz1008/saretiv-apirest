package my.project.dao;

import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthCreateRoleRequest(
        @Size(max = 3, message = "The user cannot have more 3 roles")
        List<String> roleListName
) {
}
