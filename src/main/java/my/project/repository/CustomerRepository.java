package my.project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import my.project.dao.auth.entityManager.UserRole;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerRepository {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    public List<UserRole> list(){
        String query = "SELECT ur FROM user_role ur";
        List<UserRole> ur = entityManager.createQuery(query).getResultList();
        return ur;
    }
}
