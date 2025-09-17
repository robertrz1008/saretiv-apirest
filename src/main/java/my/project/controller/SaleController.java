package my.project.controller;

import my.project.dto.supportCustomDTO.ProductDetailResponse;
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

    @PutMapping("/proDetail/{id}")
    public ResponseEntity<ProductDetail> updateProductDetail(@PathVariable int id, @RequestBody ProductDetail productDetail){
        return saleservice.updateProductDetail(id, productDetail);
    }
    @GetMapping("/proDetail/{id}")
    public ResponseEntity<ProductDetail> findProDetailById(@PathVariable int id){
        return saleservice.findById(id);
    }

    @DeleteMapping("/proDetail/{id}")
    public ResponseEntity<?> deleteProductDetail(@PathVariable int id){
        return saleservice.deleteProduct(id);
    }

    @DeleteMapping("/proDetail/support/{id}")
    public ResponseEntity<?> deleteProductDetial(@PathVariable int id){
        return saleservice.deleteProDetailBySupportId(id);
    }

    @GetMapping("/proDetail/support/{id}")
    public ResponseEntity<List<ProductDetailResponse>> findBySupport(@PathVariable int id){
        List<ProductDetailResponse> product = productDetailRepository.findBySupport(id);
        return ResponseEntity.ok(product);
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
