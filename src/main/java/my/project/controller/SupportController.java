package my.project.controller;

import my.project.dto.params.SupportParamsDTO;
import my.project.dto.supportCustomDTO.SupportByParamsResponseDTO;
import my.project.dto.supportCustomDTO.SupportDTO;
import my.project.dto.supportCustomDTO.SupportRequestDTO;
import my.project.entities.abm.Product;
import my.project.entities.transaction.Support;
import my.project.security.AuthController;
import my.project.services.Interface.InAbmService;
import my.project.services.SupportService;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/support")
public class SupportController{

    @Autowired
    private SupportService supportService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping
    public ResponseEntity<Support> create(@RequestBody SupportRequestDTO entity) {
        logger.info(entity.toString());
        return supportService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<Support>> findAll() {
        return supportService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Support>> findById(@PathVariable Integer id) {
        return supportService.findById(id);
    }
    @GetMapping("/custom")
    public ResponseEntity<List<SupportDTO>> findAllCustomized() {
        return supportService.findAllCustomized();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Support> update(@PathVariable Integer id, @RequestBody SupportRequestDTO entity) {
        return supportService.update(id, entity);
    }

    @PostMapping("/params")
    public ResponseEntity<List<SupportByParamsResponseDTO>> findByParams(@RequestBody SupportParamsDTO params){
        return supportService.findByParams(params);
    }

    @GetMapping("/custom/{id}")
    public ResponseEntity<SupportDTO> findCustomizedById(@PathVariable int id){
        return supportService.findCustomizedById(id);
    }
    @PutMapping("/status/{status}/{id}")
    public ResponseEntity<Support> updateStatus(@PathVariable("id") Integer id, @PathVariable("status") String entity) {
        return supportService.updateStatus(id, entity);
    }

    @PutMapping("/total/{total}/{id}")
    public ResponseEntity<Support> updateTotal(@PathVariable("total") Long total, @PathVariable("id") Integer id) {
        return supportService.updateTotal(total, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return supportService.delete(id);
    }

    @PostMapping("/report")
    public ResponseEntity<byte[]> report(@RequestBody List<SupportByParamsResponseDTO> list){
        try {
            byte[] report = supportService.report(list);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=report.pdf");

            return new ResponseEntity<>(report, headers, HttpStatus.OK);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

}
