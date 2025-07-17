package my.project.services.Interface;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InAbmService <T, ID>{

    ResponseEntity<T> create(T entity);

    ResponseEntity<List<T>>findAll();

    ResponseEntity<T> update(ID id, T entity);

    ResponseEntity<String> delete(ID id);

}
