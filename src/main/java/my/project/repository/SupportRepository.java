package my.project.repository;

import my.project.dto.supportCustomDTO.SupportDTO;
import my.project.entities.transaction.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportRepository extends JpaRepository<Support, Integer> {

    @Query(value = "SELECT \n" +
            "\tsup.id as \"id\", \n" +
            "\tdev.description, dev.observation, cat.name AS \"categoryDev\", \n" +
            "\tcus.name AS \"customer\", cus.document AS \"cedula\",\n" +
            "\tsup.start_date AS \"StartDate\", sup.total AS \"total\", sup.status AS \"status\"\n" +
            "FROM supports  AS sup JOIN devices AS dev ON sup.id = dev.support_id\n" +
            "JOIN categories_device AS cat ON dev.catdevice_id = cat.id\n" +
            "JOIN customers AS cus ON sup.customer_id = cus.id",
            nativeQuery = true)
    List<SupportDTO> findAllCustomized();
}
