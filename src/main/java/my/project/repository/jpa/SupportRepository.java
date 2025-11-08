package my.project.repository.jpa;

import my.project.dto.supportCustomDTO.DevicesAmountByCategory;
import my.project.dto.supportCustomDTO.SupportDTO;
import my.project.entities.transaction.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupportRepository extends JpaRepository<Support, Integer> {

    Optional<Support> findById(Integer id);

    @Query(value = "SELECT \n" +
            "\tsup.id as \"id\", \n" +
            "\tusers.name || ' ' || users.lastname as \"user\", users.id as \"userId\", \n" +
            "\tdev.description, dev.observation,dev.id AS \"categoryDevId\", dev.id as \"devId\", cat.name AS \"categoryDev\", \n" +
            "\tcus.name || ' ' || cus.lastname AS \"customer\", cus.id AS \"customerId\", cus.document AS \"cedula\",\n" +
            "\tsup.start_date AS \"startDate\", sup.end_date AS \"endDate\",sup.total AS \"total\", sup.status AS \"status\"\n" +
            "FROM supports  AS sup JOIN devices AS dev ON sup.id = dev.support_id\n" +
            "JOIN users ON sup.user_id = users.id\n" +
            "JOIN categories_device AS cat ON dev.catdevice_id = cat.id\n" +
            "JOIN customers AS cus ON sup.customer_id = cus.id " +
            "ORDER BY id DESC",
            nativeQuery = true)
    List<SupportDTO> findAllCustomized();

    @Query(value = "SELECT \n" +
            "\tsup.id as \"id\", \n" +
            "\tusers.name || ' ' || users.lastname as \"user\", users.id as \"userId\", \n" +
            "\tdev.description, dev.observation,dev.id AS \"categoryDevId\", dev.id as \"devId\", cat.name AS \"categoryDev\", \n" +
            "\tcus.name || ' ' || cus.lastname AS \"customer\", cus.id AS \"customerId\", cus.document AS \"cedula\",\n" +
            "\tsup.start_date AS \"startDate\", sup.end_date AS \"endDate\",sup.total AS \"total\", sup.status AS \"status\"\n" +
            "FROM supports  AS sup JOIN devices AS dev ON sup.id = dev.support_id\n" +
            "JOIN users ON sup.user_id = users.id\n" +
            "JOIN categories_device AS cat ON dev.catdevice_id = cat.id\n " +
            "JOIN customers AS cus ON sup.customer_id = cus.id " +
            "WHERE cus.lastname ILIKE CONCAT('%', :filter, '%') OR dev.description ILIKE CONCAT('%', :filter, '%')"+
            "ORDER BY id DESC",
            nativeQuery = true)
    List<SupportDTO> findAllCustomizedByFilter(@Param("filter") String filter);
    @Query(value = "SELECT \n" +
            "\tsup.id as \"id\", \n" +
            "\tusers.username as \"user\", users.id as \"userId\", \n" +
            "\tdev.description, dev.observation,dev.id AS \"categoryDevId\", dev.id as \"devId\", cat.name AS \"categoryDev\", \n" +
            "\tcus.name || ' ' || cus.lastname AS \"customer\", cus.id AS \"customerId\", cus.document AS \"cedula\",\n" +
            "\tsup.start_date AS \"startDate\", sup.end_date AS \"endDate\", sup.total AS \"total\", sup.status AS \"status\"\n" +
            "FROM supports  AS sup JOIN devices AS dev ON sup.id = dev.support_id\n" +
            "JOIN users ON sup.user_id = users.id\n" +
            "JOIN categories_device AS cat ON dev.catdevice_id = cat.id\n" +
            "JOIN customers AS cus ON sup.customer_id = cus.id " +
            "WHERE sup.id = :supportId " +
            "ORDER BY id DESC",
            nativeQuery = true)
    SupportDTO findAllCustomizedById(@Param("supportId") int id);

    @Query(value = "SELECT  cat.name as \"category\", count(dev.id) as \"amount\" \n" +
            "\tFROM categories_device as cat JOIN devices as dev ON cat.id = catdevice_id  JOIN supports as sup on dev.support_id = sup.id\n" +
            "WHERE sup.status = 'FINALIZADO' GROUP BY category"
    , nativeQuery = true)
    public List<DevicesAmountByCategory> deviceAmount();

}
