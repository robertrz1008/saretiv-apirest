package my.project.services;

import my.project.dto.supportCustomDTO.SupportDTO;
import my.project.dto.supportCustomDTO.SupportRequestDTO;
import my.project.entities.abm.Customer;
import my.project.entities.abm.UserEntity;
import my.project.entities.transaction.Support;
import my.project.repository.CustomerRepository;
import my.project.repository.SupportRepository;
import my.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ResponseEntity<Optional<Support>> findById(Integer id) {
        return ResponseEntity.ok(supportRepository.findById(id));
    }

    public ResponseEntity<List<SupportDTO>> findAllCustomized() {
        List<SupportDTO> supportList = supportRepository.findAllCustomized().stream()
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(supportList);
    }

    public ResponseEntity<Support> update(Integer id, SupportRequestDTO entity) {
        Support supportFound = supportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" device not found"));
        UserEntity userFound = userRepository.findById(entity.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        supportFound.setStatus(entity.status());
        supportFound.setTotal(entity.total());
        supportFound.setUser(userFound);

        return ResponseEntity.ok(supportRepository.save(supportFound));
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
