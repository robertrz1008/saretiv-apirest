package my.project.controller;

import my.project.dto.params.ProductParamsDTO;
import my.project.entities.abm.Product;
import my.project.repository.jpa.ProductRepository;
import my.project.services.Interface.InAbmService;
import my.project.services.ProductService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController implements InAbmService<Product, Integer> {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product entity) {
        return productService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return productService.findAll();
    }


    @GetMapping("/filter/{str}")
    public ResponseEntity<List<Product>> findByFIlter(@PathVariable("str") String filter) {
        return productService.findByFilter(filter);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Product> findByFIlter(@PathVariable("id") int id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id,@RequestBody Product entity) {
        return productService.update(id, entity);
    }
    @PutMapping("/stock/{id}/{stock}")
    public ResponseEntity<Product> updateStock(@PathVariable("id") Integer id,@PathVariable("stock") Integer stock) {
        return productService.updateStock(id, stock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return productService.delete(id);
    }

    @PostMapping("/params")
    public ResponseEntity<List<Product>> findByParams(@RequestBody ProductParamsDTO proParams){
        return productService.findByParams(proParams);
    }
    @PostMapping("/report")
    public ResponseEntity<byte[]> report(@RequestBody List<Product> list){
        try {
            byte[] report = productService.report(list);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=report.pdf");

            return new ResponseEntity<>(report, headers, HttpStatus.OK);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

}
