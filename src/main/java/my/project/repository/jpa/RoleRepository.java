package my.project.repository;

import my.project.entities.abm.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findRoleEntityByNameIn(List<String> roleNames);
}
