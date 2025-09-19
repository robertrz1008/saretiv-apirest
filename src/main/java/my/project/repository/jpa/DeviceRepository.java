package my.project.repository.jpa;

import jakarta.transaction.Transactional;
import my.project.entities.transaction.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    @Query(value = """
    SELECT * 
    FROM devices
    WHERE support_id = :supId
    """, nativeQuery = true)
    Optional<Device> findBySupportId(@Param("supId") int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM devices WHERE id = :id", nativeQuery = true)
    int deleteCustom(@Param("id") int id);
}
