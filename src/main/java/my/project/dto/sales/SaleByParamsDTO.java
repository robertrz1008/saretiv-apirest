package my.project.dto.sales;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder({"description", "amount", "category", "price", "date", "subtotal"})
public record SaleByParamsDTO(
    String description,
    Integer amount,
    String category,
    Integer price,
    Date date,
    Integer subtotal
) {
}
