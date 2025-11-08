package my.project.controller;

import my.project.dto.params.CustomerParamsDTO;
import my.project.entities.abm.Customer;
import my.project.entities.abm.UserEntity;
import my.project.security.AuthController;
import my.project.services.CustomerService;
import my.project.services.Interface.InAbmService;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

//    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO')")
//    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer entity) {
        return customerService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        return customerService.findAll();
    }

    @PutMapping("/{doc}")
    public ResponseEntity<Customer> update(@PathVariable("doc") String doc, @RequestBody Customer entity) {
        return customerService.update(doc, entity);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Customer> update(@PathVariable("id") int doc, @RequestBody Customer cus) {
        return customerService.updateStatus(doc, cus.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return customerService.delete(id);
    }

    @GetMapping("/filter/{filter}")
    public ResponseEntity<List<Customer>> findByFilter(@PathVariable String filter){
        return customerService.findByFilter(filter);
    }

    @PostMapping("/params")
    public ResponseEntity<List<Customer>> findByParams(@RequestBody CustomerParamsDTO customerParams){
        return customerService.findyParams(customerParams);
    }

    @PostMapping("/report")
    public ResponseEntity<byte[]> report(@RequestBody List<Customer> list) {
        try {
            byte[] report = customerService.report(list);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=report.pdf");

            return new ResponseEntity<>(report, headers, HttpStatus.OK);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
