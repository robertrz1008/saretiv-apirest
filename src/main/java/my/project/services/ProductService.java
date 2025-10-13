package my.project.services;

import my.project.dto.params.ProductParamsDTO;
import my.project.entities.abm.Enterprise;
import my.project.entities.abm.Product;
import my.project.entities.report.ProductReport;
import my.project.repository.jdbc.ExampleRepository;
import my.project.repository.jpa.EnterpriseRepository;
import my.project.repository.jpa.ProductRepository;
import my.project.security.AuthController;
import my.project.services.Interface.InAbmService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService implements InAbmService<Product, Integer> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private ExampleRepository exampleRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);



    @Override
    public ResponseEntity<Product> create(Product entity) {
        return ResponseEntity.ok(productRepository.save(entity));
    }

    @Override
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productRepository.findAll().stream()
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<Product> update(Integer id, Product entity) {
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        productFound.setDescription(entity.getDescription());
        productFound.setCategory(entity.getCategory());
        productFound.setSupplier(entity.getSupplier());
        productFound.setStock(entity.getStock());
        productFound.setDescription(entity.getDescription());
        productFound.setDescription(entity.getDescription());
        productFound.setEntryPrice(entity.getEntryPrice());
        productFound.setSalePrice(entity.getSalePrice());
        productFound.setBarcode(entity.getBarcode());

        return ResponseEntity.ok(productRepository.save(productFound));
    }

    public ResponseEntity<Product> updateStock(Integer id, int stock) {
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));

        productFound.setStock(stock);

        return ResponseEntity.ok(productRepository.save(productFound));
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        productRepository.delete(productFound);
        return ResponseEntity.ok("deleted");
    }

    public ResponseEntity<List<Product>> findByFilter(@PathVariable String filter){
        List<Product> list = productRepository.findByFilter(filter);

        return ResponseEntity.ok(list);
    }

    public ResponseEntity<Product> findById(int id){
        Product pro = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));
        return ResponseEntity.ok(pro);
    }

    public ResponseEntity<List<Product>> findByParams(ProductParamsDTO proParams){

        String query = "SELECT \n" +
                "\tpro.id, pro.description, pro.barcode, pro.stock, pro.entry_price, pro.entry_sale, pro.stock,\n" +
                "\tcat.id AS \"catId\", cat.name AS \"catName\", \n" +
                "\tsup.id AS \"supId\", sup.name AS \"supName\", sup.address AS \"supAddress\", sup.ruc AS \"supRuc\", sup.telephone AS \"supTele\"\n" +
                "FROM products AS pro \n" +
                "\tJOIN categories_product as cat ON pro.category_id = cat.id\n" +
                "\tJOIN suppliers AS sup ON pro.supplier_id = sup.id WHERE pro.description ILIKE '%%'";

        if(proParams.isStock() !=  null){
            if(proParams.isStock().equals("y") ){
                query +=" AND pro.stock > 0 ";
            } else if (proParams.isStock().equals("n")) {
                query +=" AND pro.stock = 0 ";
            }
        }
        if(proParams.category() != null){
            query +=" AND cat.name = '"+proParams.category()+"'";
        }
        if(proParams.supplier() != null){
            query +=" AND sup.name = '" + proParams.supplier()+"'";
        }
        if(proParams.buyMin() > 0 && proParams.buyMax() > 0){
            query +=" AND pro.entry_price BETWEEN " + proParams.buyMin()+" AND "+proParams.buyMax();
        }
        if(proParams.saleMin() > 0 && proParams.saleMax() > 0){
            query +=" AND pro.entry_price BETWEEN " + proParams.saleMin()+" AND "+proParams.saleMax();
        }
        if(proParams.property() != null){
            query += " ORDER BY "+proParams.property();

            if(proParams.order() != null){
                query += " "+proParams.order();
            }
        }

        List<Product> productResponse = jdbcTemplate.query(query, (rs, row) ->
             new Product.Builder()
                    .id(rs.getInt("id"))
                    .description(rs.getString("description"))
                    .stock(rs.getInt("stock"))
                    .barcode(rs.getString("barcode"))
                    .entryPrice(rs.getInt("entry_price"))
                    .salePrice(rs.getInt("entry_sale"))
                    .categoryProduct(
                            rs.getInt("catId"),
                            rs.getString("catName")
                    )
                    .supplier(
                            rs.getInt("supId"),
                            rs.getString("supName"),
                            rs.getString("supAddress"),
                            rs.getString("supRuc"),
                            rs.getString("supTele")
                    )
                    .build()
        );
        return ResponseEntity.ok(productResponse);
    }

    public byte[] report(List<Product> products) throws JRException {
        List<ProductReport> productsToReport = products.stream().map( product ->
            new ProductReport(
                    product.getId(),
                    product.getBarcode(),
                    product.getDescription(),
                    product.getCategory().getName(),
                    product.getSupplier().getName(),
                    product.getStock(),
                    product.getEntryPrice(),
                    product.getSalePrice()
            )
        ).collect(Collectors.toList());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productsToReport);
        InputStream reportStream = this.getClass().getResourceAsStream("/reports/products_report.jasper");

        List<Enterprise> enterprise = enterpriseRepository.findAll();

        if(enterprise.isEmpty()) {
            throw new RuntimeException("enterprise void");
        }
        //getting today date
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", enterprise.get(0).getName());
        params.put("telephone", enterprise.get(0).getTelephone());
        params.put("address", enterprise.get(0).getDirection());
        params.put("today", formatter.format(localDateTime));

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, params, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

}
