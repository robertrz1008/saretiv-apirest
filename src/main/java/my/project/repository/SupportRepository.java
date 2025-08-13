package my.project.repository;

import my.project.dto.supportCustomDTO.SupportDTO;
import my.project.entities.transaction.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupportRepository extends JpaRepository<Support, Integer> {

    Optional<Support> findById(Integer id);

    @Query(value = "SELECT \n" +
            "\tsup.id as \"id\", \n" +
            "\tusers.username as \"user\", users.id as \"userId\", \n" +
            "\tdev.description, dev.observation,dev.id AS \"categoryDevId\", dev.id as \"devId\", cat.name AS \"categoryDev\", \n" +
            "\tcus.name || ' ' || cus.lastname AS \"customer\", cus.id AS \"customerId\", cus.document AS \"cedula\",\n" +
            "\tsup.start_date AS \"startDate\", sup.total AS \"total\", sup.status AS \"status\"\n" +
            "FROM supports  AS sup JOIN devices AS dev ON sup.id = dev.support_id\n" +
            "JOIN users ON sup.user_id = users.id\n" +
            "JOIN categories_device AS cat ON dev.catdevice_id = cat.id\n" +
            "JOIN customers AS cus ON sup.customer_id = cus.id",
            nativeQuery = true)
    List<SupportDTO> findAllCustomized();

}
