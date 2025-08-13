package my.project.services;

import my.project.controller.CategoryDevController;
import my.project.dto.abm.DeviceRequest;
import my.project.entities.abm.CategoryDevice;
import my.project.entities.abm.Customer;
import my.project.entities.abm.Supplier;
import my.project.entities.abm.UserEntity;
import my.project.entities.transaction.Device;
import my.project.entities.transaction.Support;
import my.project.repository.CategoryDevRepository;
import my.project.repository.DeviceRepository;
import my.project.repository.SupplierRepository;
import my.project.repository.SupportRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private CategoryDevRepository categoryDevRepository;
    @Autowired
    private SupportRepository supportRepository;

    public ResponseEntity<Device> create(DeviceRequest request) {
        CategoryDevice category = categoryDevRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("category not found"));

        Support support = supportRepository.findById(request.supportId())
                .orElseThrow(() -> new RuntimeException("Support not found"));

        try {
            Device device = new Device();
            device.setDescription(request.description());
            device.setObservation(request.observation());
            device.setCategory(category);
            device.setSupport(support);

            return ResponseEntity.ok(deviceRepository.save(device));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<Device>> findAll() {
        return ResponseEntity.ok(deviceRepository.findAll());
    }

    public ResponseEntity<Device> update(Integer id, Device entity) {
        Device deviceFound = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" device not found"));

        deviceFound.setDescription(entity.getDescription());
        deviceFound.setObservation(entity.getObservation());

        return ResponseEntity.ok(deviceRepository.save(deviceFound));
    }

    public ResponseEntity<String> delete(Integer id) {
        Device deviceFound = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" device not found"));

        try {
            deviceRepository.delete(deviceFound);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
