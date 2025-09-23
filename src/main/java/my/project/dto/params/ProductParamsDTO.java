package my.project.dto.params;

public record ProductParamsDTO(
        String property,
        String category,
        String supplier,
        String order,
        String isStock,
        Integer saleMin,
        Integer saleMax,
        Integer buyMin,
        Integer buyMax
) {
}
