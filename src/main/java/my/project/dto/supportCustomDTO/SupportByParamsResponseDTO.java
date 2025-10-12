package my.project.dto.supportCustomDTO;

import java.time.LocalDate;
import java.util.Date;

public record SupportByParamsResponseDTO(
        Integer id,
        String description,
        String categoryDev,
        String customer,
        String document,
        Date startDate,
        Date endDate,
        Long total,
        String status,
        String user
) {
}
