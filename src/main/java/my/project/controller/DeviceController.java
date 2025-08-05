package my.project.controller;

import my.project.entities.abm.CategoryDevice;
import my.project.entities.transaction.Device;
import my.project.services.DeviceService;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
public class DeviceController implements InAbmService<Device, Integer> {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<Device> create(@RequestBody Device entity) {
        return deviceService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<Device>> findAll() {
        return deviceService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> update(@PathVariable Integer id, @RequestBody Device entity) {
        return deviceService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(Integer id) {
        return deviceService.delete(id);
    }
}
