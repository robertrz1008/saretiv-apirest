package my.project.services;

import my.project.entities.transaction.TypeSupport;
import my.project.repository.jpa.TypeSupportRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeSupportService implements InAbmService<TypeSupport, Integer> {

    @Autowired
    private TypeSupportRepository typeSupportRepository;

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
}
