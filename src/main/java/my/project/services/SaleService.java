package my.project.services;

import my.project.entities.transaction.ProductDetail;
import my.project.entities.transaction.Sale;
import my.project.repository.ProductDetailRepository;
import my.project.repository.SaleRepository;
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

    public ResponseEntity<List<ProductDetail>> listProductDetail(){
        return ResponseEntity.ok(productDetailRepository.findAll());
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
