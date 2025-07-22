package my.project.services;

import my.project.entities.abm.CategoryProduct;
import my.project.entities.abm.Supplier;
import my.project.repository.SupplierRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService implements InAbmService<Supplier, Integer> {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public ResponseEntity<Supplier> create(Supplier entity) {
        return ResponseEntity.ok(supplierRepository.save(entity));
    }

    @Override
    public ResponseEntity<List<Supplier>> findAll() {
        return ResponseEntity.ok(supplierRepository.findAll());
    }

    @Override
    public ResponseEntity<Supplier> update(Integer id, Supplier entity) {
        Supplier supplierFound = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        try {
            supplierFound.setName(entity.getName());
            supplierFound.setTelephone(entity.getTelephone());
            supplierFound.setRuc(entity.getRuc());

            return ResponseEntity.ok(supplierRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        Supplier supplierFound = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        supplierRepository.delete(supplierFound);

        return ResponseEntity.ok("deleted");
    }

}
