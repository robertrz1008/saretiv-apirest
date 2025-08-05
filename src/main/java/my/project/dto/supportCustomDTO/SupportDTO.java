package my.project.dto.supportCustomDTO;

import java.time.LocalDate;

public interface SupportDTO {
        int getId();
        String getDescription();
        String getObservation();
        String getCategoryDev();
        String getCustomer();
        String getDocument();
        LocalDate getStarDate();
        long getTotal();
        String getStatus(); // cambia si es necesario
}
