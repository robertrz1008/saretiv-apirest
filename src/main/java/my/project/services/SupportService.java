package my.project.services;

import my.project.dto.params.SupportParamsDTO;
import my.project.dto.supportCustomDTO.SupportByParamsResponseDTO;
import my.project.dto.supportCustomDTO.SupportDTO;
import my.project.dto.supportCustomDTO.SupportRequestDTO;
import my.project.entities.abm.Customer;
import my.project.entities.abm.UserEntity;
import my.project.entities.transaction.Support;
import my.project.repository.jpa.CustomerRepository;
import my.project.repository.jpa.SupportRepository;
import my.project.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SupportService{

    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseEntity<Support> create(SupportRequestDTO entity) {
        Customer customer = customerRepository.findById(entity.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        UserEntity user = userRepository.findById(entity.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        try {
            Support support = new Support();
            support.setStartDate(entity.startDate());
            support.setStatus(entity.status());
            support.setTotal(entity.total());
            support.setCustomer(customer);
            support.setUser(user);

            return ResponseEntity.ok(supportRepository.save(support));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<Support>> findAll() {
        return null;
    }

    public ResponseEntity<Optional<Support>> findById(Integer id) {
        return ResponseEntity.ok(supportRepository.findById(id));
    }

    public ResponseEntity<List<SupportDTO>> findAllCustomized() {
        List<SupportDTO> supportList = supportRepository.findAllCustomized();

        return ResponseEntity.ok(supportList);
    }

    public ResponseEntity<List<SupportByParamsResponseDTO>> findByParams(SupportParamsDTO params){
        String query = "SELECT \n" +
                "\tsup.id as \"id\", \n" +
                "\tusers.name || '' || users.lastname as \"user\", users.id as \"userId\", \n" +
                "\tdev.description AS \"devDescription\", dev.observation ,dev.id AS \"categoryDevId\", dev.id as \"devId\", cat.name AS \"categoryDev\", \n" +
                "\tcus.name || ' ' || cus.lastname AS \"customer\", cus.id AS \"customerId\", cus.document AS \"cedula\",\n" +
                "\tsup.start_date AS \"startDate\", sup.end_date AS \"endDate\",sup.total AS \"total\", sup.status AS \"status\"\n" +
                "FROM supports  AS sup JOIN devices AS dev ON sup.id = dev.support_id\n" +
                "JOIN users ON sup.user_id = users.id\n" +
                "JOIN categories_device AS cat ON dev.catdevice_id = cat.id\n" +
                "JOIN customers AS cus ON sup.customer_id = cus.id WHERE sup.status = 'FINALIZADO' ";

        if(params.customerId() != null){
            query +=" AND cus.id ="+params.customerId();
        }
        if(params.technicalId() != null){
            query +=" AND users.id = "+params.technicalId();
        }
        if(params.categoryId() != null){
            query +=" AND cat.id = "+params.categoryId();
        }
        if(params.totalMin() > 0 && params.totalMax() > 0){
            query +=" AND sup.total BETWEEN " + params.totalMin()+" AND "+params.totalMax();
        }
        if(params.dateFrom() != null && params.dateTo() != null){
            query +=" AND sup.start_date BETWEEN '"+ params.dateFrom()+" 00:00:00' AND '"+params.dateTo()+" 23:59:59'";
        }
        if(params.property() != null){
            query += " ORDER BY "+params.property();

            if(params.order() != null){
                query += " "+params.order();
            }
        }


        List<SupportByParamsResponseDTO> list = jdbcTemplate.query(query, (rs, row) ->
                new SupportByParamsResponseDTO(
                        rs.getInt("id"),
                        rs.getString("devDescription"),
                        rs.getString("categoryDev"),
                        rs.getString("customer"),
                        rs.getString("cedula"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getLong("total"),
                        rs.getString("status"),
                        rs.getString("user")
                        )
        );
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<SupportDTO> findCustomizedById(int id){
        try {
            SupportDTO supports = supportRepository.findAllCustomizedById(id);
            return ResponseEntity.ok(supports);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Support> update(Integer id, SupportRequestDTO entity) {
        Support supportFound = supportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("device not found"));
        UserEntity userFound = userRepository.findById(entity.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

//        supportFound.setStatus(entity.status());
        supportFound.setStartDate(entity.startDate());
        supportFound.setTotal(entity.total());
        supportFound.setUser(userFound);

        return ResponseEntity.ok(supportRepository.save(supportFound));
    }

    public ResponseEntity<Support> updateStatus(Integer id, String status){
        Support supportFound = supportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "support not found"));

        if(status.equals("FINALIZADO")){
            supportFound.setEndDate(new Date());
        }
        if(status.equals("ACTIVO")){
            supportFound.setEndDate(null);
        }

        supportFound.setStatus(status);


        try {
            return ResponseEntity.ok(supportRepository.save(supportFound));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Support> updateTotal(Long total, Integer id){
        Support supportFound = supportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("device not found"));
        supportFound.setTotal(total);

        try {
            Support sup =supportRepository.save(supportFound);
            return ResponseEntity.ok(sup);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        Support support = supportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" device not found"));
        try {
            supportRepository.delete(support);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
