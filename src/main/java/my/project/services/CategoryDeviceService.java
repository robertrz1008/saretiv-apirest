package my.project.services;

import my.project.entities.abm.CategoryDevice;
import my.project.repository.CategoryDevRepository;
import my.project.services.Interface.InAbmService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryDeviceService implements InAbmService<CategoryDevice, Integer> {

    @Autowired
    private CategoryDevRepository categoryDevRepository;

    public ResponseEntity<CategoryDevice> create(CategoryDevice entity) {
        return ResponseEntity.ok(categoryDevRepository.save(entity));
    }

    public ResponseEntity<List<CategoryDevice>> findAll() {
        return ResponseEntity.ok(categoryDevRepository.findAll());
    }

    public ResponseEntity<CategoryDevice> update(Integer id, CategoryDevice entity) {
        CategoryDevice categoryDeviceFound = categoryDevRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        categoryDeviceFound.setName(entity.getName());

        return ResponseEntity.ok(categoryDevRepository.save(categoryDeviceFound));
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        CategoryDevice categoryDeviceFound = categoryDevRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        categoryDevRepository.delete(categoryDeviceFound);
        return ResponseEntity.ok("ok");
    }
}
