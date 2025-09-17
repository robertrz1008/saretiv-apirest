package my.project.dto.filter;

public record UserFilerDTO(
        String property,
        String role,
        String order,
        Boolean isActive
) {
}
