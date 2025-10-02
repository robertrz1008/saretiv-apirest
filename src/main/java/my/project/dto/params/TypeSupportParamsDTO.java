package my.project.dto.params;

public record TypeSupportParamsDTO(
        String property,
        String category,
        String order,
        Integer amountMin,
        Integer amountMax
) { }
