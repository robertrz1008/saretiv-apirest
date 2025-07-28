package my.project.services;

import my.project.entities.abm.CategoryProduct;
import my.project.entities.abm.Product;
import my.project.entities.abm.Supplier;
import my.project.repository.ProductRepository;
import my.project.repository.SupplierRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return ResponseEntity.ok(supplierRepository.findAll());
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
