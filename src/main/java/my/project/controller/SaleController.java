package my.project.controller;

import my.project.entities.transaction.ProductDetail;
import my.project.entities.transaction.Sale;
import my.project.repository.ProductDetailRepository;
import my.project.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sale")
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'VENDEDOR')")
public class SaleController {

    @Autowired
    private SaleService saleservice;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @PostMapping("/proDetail")
    public ResponseEntity<List<ProductDetail>> createProD(@RequestBody List<ProductDetail> productDetails){
        return saleservice.createProductDetail(productDetails);
    }
    @GetMapping("/proDetail")
    public ResponseEntity<List<ProductDetail>> findProDetial(){
        List<ProductDetail> productDetails = productDetailRepository.findAll().stream()
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDetails);
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale){
        return saleservice.createSale(sale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@RequestBody Sale sale, @PathVariable int id){
        return saleservice.updateSale(id, sale);
    }

}
