package my.project.dto.supportCustomDTO;

import java.time.LocalDate;

public interface SupportDTO {
        int getId();
        String getDescription();
        String getObservation();
        String getCategoryDev();
        Integer getCategoryDevId();
        String getCustomer();
        Integer getCustomerId();
        Integer getDevId();
        String getDocument();
        LocalDate getStarDate();
        long getTotal();
        String getStatus(); // cambia si es necesario
        String getUser();
        Integer getUserId();
}
