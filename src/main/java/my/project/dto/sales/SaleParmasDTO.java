package my.project.dto.sales;

public record SaleParmasDTO(
        String property,
        String category,
        String order,
        Integer subtotalMin,
        Integer subtotalMax,
        String dateFrom,
        String dateTo
) {
}
