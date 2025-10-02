package my.project.dto.sales;

import java.util.Date;

public interface SaleResponse {
    String getDescription();
    String getCategory();
    Integer getAmount();
    Integer getPrice();
    Date getDate();
    Integer getSubtotal();
}
