package my.project.services;

import my.project.dto.supportCustomDTO.SupportDTO;
import my.project.dto.supportCustomDTO.SupportRequestDTO;
import my.project.entities.abm.Customer;
import my.project.entities.abm.UserEntity;
import my.project.entities.transaction.Support;
import my.project.repository.CustomerRepository;
import my.project.repository.SupportRepository;
import my.project.repository.UserRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportService{

    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

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

    public ResponseEntity<List<SupportDTO>> findAllCustomized() {
        return ResponseEntity.ok(supportRepository.findAllCustomized());
    }

    public ResponseEntity<Support> update(Integer id, Support entity) {
        Support support = supportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" device not found"));
        return ResponseEntity.ok(supportRepository.save(support));
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
