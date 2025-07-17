package my.project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import my.project.dao.auth.entityManager.UserRole;
import my.project.entities.abm.Customer;
import my.project.entities.abm.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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
