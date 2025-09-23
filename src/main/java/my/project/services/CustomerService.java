package my.project.services;

import my.project.dto.params.CustomerParamsDTO;
import my.project.dto.params.UserParamsDTO;
import my.project.entities.abm.Customer;
import my.project.repository.jpa.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService{

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseEntity<Customer> create(Customer entity) {
        return new ResponseEntity<>(customerRepository.save(entity), HttpStatus.CREATED);
    }

    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customers = customerRepository.findAll().stream()
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    public ResponseEntity<Customer> update(String doc, Customer customer) {
        Customer customerFound = customerRepository.findByDocument(doc)
                .orElseThrow(() -> new RuntimeException("not found"));
        customerFound.setName(customer.getName());
        customerFound.setLastname(customer.getLastname());
        customerFound.setTelephone(customer.getTelephone());
        customerFound.setDocument(customer.getDocument());
        customerFound.setAddress(customer.getAddress());
        customerFound.setStatus(customer.getStatus());

        return new ResponseEntity<>(customerRepository.save(customerFound), HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Integer id) {
        try {
            customerRepository.deleteById(id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<Customer>> findByFilter(String filter){
        List<Customer> list = customerRepository.findByFilter(filter);

        return ResponseEntity.ok(list);
    }

    public ResponseEntity<List<Customer>> findyParams(CustomerParamsDTO customerParams){
        String query = "SELECT * FROM customers WHERE name ILIKE '%%'";
        query +=" AND status = "+customerParams.isActive();

        if(customerParams.property() != null){
            query += " ORDER BY "+customerParams.property();

            if(customerParams.order() != null){
                query += " "+customerParams.order();
            }
        }

        List<Customer> userResponse = jdbcTemplate.query(query, (rs, row) -> {
            Customer customer= new Customer();
            customer.setId(rs.getInt("id"));
            customer.setName(rs.getString("name"));
            customer.setLastname(rs.getString("lastname"));
            customer.setDocument(rs.getString("document"));
            customer.setTelephone(rs.getString("telephone"));
            customer.setAddress(rs.getString("address"));
            customer.setStatus(rs.getBoolean("status"));
            return customer;
        });
        return ResponseEntity.ok(userResponse);
    }
}
