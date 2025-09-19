package my.project.services;

import my.project.entities.transaction.ProductDetail;
import my.project.entities.transaction.Sale;
import my.project.repository.jpa.ProductDetailRepository;
import my.project.repository.jpa.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;

    public ResponseEntity<List<ProductDetail>> createProductDetail(List<ProductDetail> productDetails) {
        try {
            List<ProductDetail> productDetailsSaved = productDetailRepository.saveAll(productDetails);

            return ResponseEntity.ok(productDetailsSaved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<String> deleteProDetailBySupportId(int id){
        try {
            productDetailRepository.deleteBySupportId(id);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<ProductDetail>> listProductDetail(){
        return ResponseEntity.ok(productDetailRepository.findAll());
    }

    public ResponseEntity<ProductDetail> findById(int id){
        ProductDetail productDetailFound = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product detail not found"));

        return ResponseEntity.ok(productDetailFound);
    }

    public ResponseEntity<ProductDetail> updateProductDetail(Integer id, ProductDetail entity){
        ProductDetail productDetailFound = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product detail not found"));

        productDetailFound.setProductAmount(entity.getProductAmount());
        productDetailFound.setSubtotal(entity.getSubtotal());

        try {
            ProductDetail pro = productDetailRepository.save(productDetailFound);
            return ResponseEntity.ok(pro);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> deleteProduct(int id){
        ProductDetail productDetailFound = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product detail not found"));
        try {
            productDetailRepository.delete(productDetailFound);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Sale> createSale(Sale sale){
        try {
            return ResponseEntity.ok(saleRepository.save(sale));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Sale> updateSale(int id, Sale sale){
        Sale saleFound = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cat not found"));

        saleFound.setTotal(sale.getTotal());

        saleRepository.save(saleFound);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
