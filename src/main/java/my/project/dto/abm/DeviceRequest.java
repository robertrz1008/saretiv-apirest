package my.project.dto.abm;

public record DeviceRequest(
        String observation,
        String description,
        Integer categoryId,
        Integer supportId
) {
}
