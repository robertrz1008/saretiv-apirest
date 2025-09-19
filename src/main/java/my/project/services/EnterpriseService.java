package my.project.services;

import my.project.entities.abm.Enterprise;
import my.project.repository.jpa.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseService {
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    public ResponseEntity<Enterprise> save(Enterprise enterprise){
        try {
            Enterprise en = enterpriseRepository.save(enterprise);
            return new ResponseEntity<>(en, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<Enterprise> save(int id, Enterprise enterprise){
        Enterprise enterpriseFound = enterpriseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        enterpriseFound.setName(enterprise.getName());
        enterpriseFound.setDirection(enterprise.getDirection());
        enterpriseFound.setTelephone(enterprise.getTelephone());

        return new ResponseEntity<>(enterpriseRepository.save(enterprise), HttpStatus.OK);
    }
}
