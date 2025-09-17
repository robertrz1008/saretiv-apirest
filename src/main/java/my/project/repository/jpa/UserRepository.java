package my.project.repository;

import jakarta.transaction.Transactional;
import my.project.entities.abm.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findUserByUsername(String username);

    @Query(value = "SELECT * FROM user_role WHERE user_id = :usuarioId",nativeQuery = true)
    List<Map<String, Object>> obtenerRolesDeUsuario(@Param("usuarioId") int usuarioId);

    @Query(value = """
    SELECT * 
    FROM users 
    WHERE name ILIKE CONCAT('%', :filter, '%') 
       OR document ILIKE CONCAT('%', :filter, '%')
    """, nativeQuery = true)
    List<UserEntity> findByFilter(@Param("filter") String filter);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM user_role WHERE user_id=:userid",
            nativeQuery = true
    )
    int deleteUserRolByUser(@Param("userid") int userId);

    Optional<UserEntity> findByDocument(String document);

    @Query(value = "UPDATE SET users(password) VALUES(:pass) WHERE id = :id", nativeQuery = true)
    Optional<UserEntity> modifyPassword(@Param("id") int id, @Param("pass") String password);
}
