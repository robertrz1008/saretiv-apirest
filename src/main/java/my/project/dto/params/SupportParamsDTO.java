package my.project.dto.params;

public record SupportParamsDTO(
        Integer customerId,
        Integer technicalId,
        Integer categoryId,
        String property,
        String order,
        Integer totalMin,
        Integer totalMax,
        String dateFrom,
        String dateTo
) {
}
