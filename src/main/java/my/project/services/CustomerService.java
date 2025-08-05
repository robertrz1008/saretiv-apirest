package my.project.services;

import my.project.entities.abm.Customer;
import my.project.entities.abm.UserEntity;
import my.project.repository.CustomerRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService{

    @Autowired
    private CustomerRepository customerRepository;


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

    public ResponseEntity<List<Customer>> findByFilter(@PathVariable String filter){
        List<Customer> list = customerRepository.findByFilter(filter);

        return ResponseEntity.ok(list);
    }
}
