package my.project.dto.sales;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder({"date", "description", "amount", "category", "price", "date", "subtotal"})
public record SaleByParamsDTO(
        Integer id,
        String description,
        Integer amount,
        String category,
        Integer price,
        Date date,
        Integer subtotal
) { }
