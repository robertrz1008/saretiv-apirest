package my.project.repository;

import my.project.entities.transaction.SupportActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportActivityRepository extends JpaRepository<SupportActivity, Long> {

    @Query(value = "SELECT * from support_activities WHERE support_id = :id", nativeQuery = true)
    List<SupportActivity> findBySupport(@Param("id") Integer id);

    @Query(value = "DELETE * FROM support_activities WHERE support_id =: id", nativeQuery = true)
    int deleteBySupportId(@Param("id") Integer id);
}
