package my.project.services;

import my.project.dto.params.TypeSupportParamsDTO;
import my.project.entities.abm.Enterprise;
import my.project.entities.report.SupportTypeReport;
import my.project.entities.transaction.TypeSupport;
import my.project.repository.jpa.EnterpriseRepository;
import my.project.repository.jpa.TypeSupportRepository;
import my.project.services.Interface.InAbmService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TypeSupportService implements InAbmService<TypeSupport, Integer> {

    @Autowired
    private TypeSupportRepository typeSupportRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EnterpriseRepository enterpriseRepository;


    public ResponseEntity<TypeSupport> create(TypeSupport entity) {
        TypeSupport typeSupport = typeSupportRepository.save(entity);
        return ResponseEntity.ok(typeSupport);
    }

    public ResponseEntity<List<TypeSupport>> findAll() {
        List<TypeSupport> typeSupports = typeSupportRepository.findAll().stream()
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(typeSupports);
    }

    public ResponseEntity<List<TypeSupport>> findByFIlter(String filter){
        try {
            List<TypeSupport> suppTypes = typeSupportRepository.findByFilter(filter);
            return ResponseEntity.ok(suppTypes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<TypeSupport> update(Integer id, TypeSupport entity) {
        TypeSupport typeSupportFound = typeSupportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("typesuppor not found"));

        typeSupportFound.setAmount(entity.getAmount());
        typeSupportFound.setDescription(entity.getDescription());
        typeSupportFound.setCategory(entity.getCategory());

        return ResponseEntity.ok(typeSupportRepository.save(typeSupportFound));
    }

    public ResponseEntity<String> delete(Integer id) {
        TypeSupport typeSupportFound = typeSupportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("typesuppor not found"));

        try {
            typeSupportRepository.delete(typeSupportFound);
            return ResponseEntity.ok("deleted");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<TypeSupport>> findByParams(TypeSupportParamsDTO params){
        String query= "SELECT ts.id, ts.description, ts.amount, cat.id AS \"catId\", cat.name AS \"catName\"" +
                "FROM types_support AS ts JOIN categories_device AS cat ON ts.catdevice = cat.id\n" +
                "WHERE ts.description ILIKE '%%'";

        if(params.category() != null){
            query +=" AND cat.name = '"+params.category()+"'";
        }
        if(params.amountMin() > 0 && params.amountMax() > 0){
            query +=" AND ts.amount BETWEEN " + params.amountMin()+" AND "+params.amountMax();
        }
        if(params.property() != null){
            query += " ORDER BY "+params.property();

            if(params.order() != null){
                query += " "+params.order();
            }
        }

        List<TypeSupport> typeSupportList = jdbcTemplate.query(query, (rs, row) ->
                new TypeSupport.Builder()
                        .id(rs.getInt("id"))
                        .description(rs.getString("description"))
                        .amount(rs.getDouble("amount"))
                        .categoryDevice(
                                rs.getInt("catId"),
                                rs.getString("catName")
                        )
                        .build()
        );

        return ResponseEntity.ok(typeSupportList);
    }

    public byte[] report(List<TypeSupport> supports) throws JRException {
        List<SupportTypeReport> newList = supports.stream().map(ts ->
            new SupportTypeReport(
                    ts.getId(),
                    ts.getDescription(),
                    ts.getAmount(),
                    ts.getCategory().getName()
            )
        ).collect(Collectors.toList());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(newList);
        InputStream reportStream = this.getClass().getResourceAsStream("/reports/supportTypes_report.jasper");

        List<Enterprise> enterprise = enterpriseRepository.findAll();

        if(enterprise.isEmpty()) {
            throw new RuntimeException("enterprise void");
        }
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
