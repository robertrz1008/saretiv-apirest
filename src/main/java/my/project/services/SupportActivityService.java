package my.project.services;

import my.project.entities.transaction.SupportActivity;
import my.project.repository.jpa.SupportActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportActivityService {

    @Autowired
    public SupportActivityRepository activityRepository;

    public ResponseEntity<List<SupportActivity>> create(List<SupportActivity> activities) {
        try {
            List<SupportActivity> activitiesSaves = activityRepository.saveAll(activities);

            return ResponseEntity.ok(activitiesSaves);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<SupportActivity>> findAll() {
        return ResponseEntity.ok(activityRepository.findAll());
    }

    public ResponseEntity<SupportActivity> update(Long id, SupportActivity entity) {
        return null;
    }

    public ResponseEntity<List<SupportActivity>> findBySupport(Integer id){
        List<SupportActivity> activities = activityRepository.findBySupport(id);

        return ResponseEntity.ok(activities);
    }

    public ResponseEntity<String> delete(Long id) {
        SupportActivity activityFound = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("activity not found"));

        try {
            activityRepository.delete(activityFound);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<String> deleteBySupportId(int id){
        try {
            activityRepository.deleteBySupportId(id);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
