package my.project.dto.supportCustomDTO;

import java.util.Date;

public record SupportRequestDTO(
        Integer customerId,
        Date startDate,
        String status,
        double total,
        Integer userId
) {
}
