package my.project.dto.params;

public record UserParamsDTO(
        String property,
        String role,
        String order,
        Boolean isActive
) {
}
