package my.project.repository.jpa;

import my.project.entities.abm.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByDocument(String document);

    @Query(value = """
    SELECT * 
    FROM customers 
    WHERE name ILIKE CONCAT('%', :filter, '%')
       OR document ILIKE CONCAT('%', :filter, '%')
    """, nativeQuery = true)
    List<Customer> findByFilter(@Param("filter") String filter);
}
