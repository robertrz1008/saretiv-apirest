package my.project.services;

import my.project.entities.transaction.Device;
import my.project.repository.DeviceRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService implements InAbmService<Device, Integer> {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public ResponseEntity<Device> create(Device entity) {
        try {
            return ResponseEntity.ok(deviceRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<List<Device>> findAll() {
        return ResponseEntity.ok(deviceRepository.findAll());
    }

    @Override
    public ResponseEntity<Device> update(Integer id, Device entity) {
        Device deviceFound = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" device not found"));
        return ResponseEntity.ok(deviceRepository.save(deviceFound));
    }

    @Override
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
