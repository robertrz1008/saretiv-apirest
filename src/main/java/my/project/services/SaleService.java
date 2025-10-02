package my.project.services;

import my.project.dto.sales.SaleByParamsDTO;
import my.project.dto.sales.SaleParmasDTO;
import my.project.dto.sales.SaleResponse;
import my.project.dto.sales.SalesDatesDTO;
import my.project.entities.transaction.ProductDetail;
import my.project.entities.transaction.Sale;
import my.project.repository.jpa.ProductDetailRepository;
import my.project.repository.jpa.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    public ResponseEntity<List<SaleResponse>> findByDate(SalesDatesDTO datesDTO){
        try {
            List<SaleResponse> sales = saleRepository.findByDate(datesDTO.date1(), datesDTO.date2());
            return ResponseEntity.ok(sales);
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

    public ResponseEntity<List<SaleByParamsDTO>> findByParams(SaleParmasDTO params){
        String query = "SELECT pro.description, cat.name AS \"category\", pro.entry_sale as \"price\", pd.product_amount as \"amount\", pd.subtotal as \"subtotal\", sa.create_at AS \"date\"\n" +
                "FROM sales AS sa \n" +
                "\tJOIN products_detail AS pd ON sa.id = pd.sale_id\n" +
                "\tJOIN products AS pro ON pd.product_id = pro.id\n" +
                "\tJOIN categories_product AS cat ON pro.category_id = cat.id\n" +
                " WHERE pro.description ILIKE '%%'";

        if(params.category() != null){
            query +=" AND cat.name = '"+params.category()+"'";
        }
        if(params.subtotalMin() > 0 && params.subtotalMax() > 0){
            query +=" AND pro.entry_price BETWEEN " + params.subtotalMin()+" AND "+params.subtotalMax();
        }
        if(params.dateFrom() != null && params.dateTo() != null){
            query +=" AND sa.create_at BETWEEN '"+ params.dateFrom()+" 00:00:00' AND '"+params.dateTo()+" 23:59:59'";
        }
        if(params.property() != null){
            query += " ORDER BY "+params.property();

            if(params.order() != null){
                query += " "+params.order();
            }
        }

        List<SaleByParamsDTO> list =  jdbcTemplate.query(query, (rs ,rw)->
                new SaleByParamsDTO(
                        rs.getString("description"),
                        rs.getInt("amount"),
                        rs.getString("category"),
                        rs.getInt("price"),
                        rs.getDate("date"),
                        rs.getInt("subtotal")
                )
        );
        return ResponseEntity.ok(list);
    }

}
