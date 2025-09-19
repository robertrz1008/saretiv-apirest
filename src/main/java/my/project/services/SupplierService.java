package my.project.services;

import my.project.entities.abm.Product;
import my.project.entities.abm.Supplier;
import my.project.repository.jpa.ProductRepository;
import my.project.repository.jpa.SupplierRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService implements InAbmService<Supplier, Integer> {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Supplier> create(Supplier entity) {
        return ResponseEntity.ok(supplierRepository.save(entity));
    }

    public ResponseEntity<List<Supplier>> findAll() {
        List<Supplier> suppliers = supplierRepository.findAll().stream()
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(suppliers);
    }

    public ResponseEntity<List<Supplier>> findByFilter(String filter) {
        return ResponseEntity.ok(supplierRepository.findByFilter(filter));
    }

    public ResponseEntity<Supplier> update(Integer id, Supplier entity) {
        Supplier supplierFound = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        try {
            supplierFound.setName(entity.getName());
            supplierFound.setTelephone(entity.getTelephone());
            supplierFound.setRuc(entity.getRuc());
            supplierFound.setAddress(entity.getAddress());

            return ResponseEntity.ok(supplierRepository.save(supplierFound));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        Supplier supplierFound = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        List<Product> productList = productRepository.findBySupplier(id);

        if(productList.size() > 0){
            return new ResponseEntity<>("not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        supplierRepository.delete(supplierFound);

        return ResponseEntity.ok("deleted");
    }

}
