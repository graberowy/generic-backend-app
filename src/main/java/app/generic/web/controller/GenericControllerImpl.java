package app.generic.web.controller;

import app.data.Data;
import app.generic.web.resources.DataDTO;
import app.generic.service.GenericCRUD;
import app.generic.web.resources.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is for generic cover CRUD operations on entity's
 *
 * @param <DTO> object DTO
 * @param <T>   object entity
 * @param <ID>  id type
 * @author pawel.jankowski
 */
@RequiredArgsConstructor
public abstract class GenericControllerImpl<DTO extends DataDTO<ID>, T extends Data<ID>, ID> implements GenericController<DTO, ID> {

    private final GenericCRUD<T, ID> genericCRUD;
    private final GenericMapper<DTO, T> genericMapper;

    /**
     * This method is for get all existing objects records
     *
     * @return list of existing objects
     */
    @Override
    @GetMapping
    public ResponseEntity<List<DTO>> getAll() {
        return ResponseEntity.ok(genericCRUD.findAll().stream()
                .map(genericMapper::getDTO)
                .collect(Collectors.toList()));
    }

    /**
     * This method is for get specific object record
     *
     * @param id identity of object record
     * @return object record with existing id or error if not found
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable ID id) {
        return genericCRUD.findById(id)
                .map(genericMapper::getDTO)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * This method is for create new object record
     *
     * @param dto result of mapping object
     * @return new object record
     */
    @Override
    @PostMapping
    public ResponseEntity<DTO> saveNew(@RequestBody DTO dto) {
        T t = genericMapper.getModel(dto);
        return ResponseEntity.ok(genericMapper.getDTO(genericCRUD.save(t)));
    }

    /**
     * This method is for update data of existing object record
     *
     * @param id  identity of object record
     * @param dto result of mapping object
     * @return updated object record or error if not found
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<DTO> updateRecord(@PathVariable ID id, @RequestBody DTO dto) {
        dto.setId(id);
        T t = genericMapper.getModel(dto);
        return genericCRUD.update(t)
                .map(genericMapper::getDTO)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * This method is for remove existing object record
     *
     * @param id identity of object record
     * @return success status if object exist or error if not
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable ID id) {
        if (genericCRUD.findById(id).isPresent()) {
            genericCRUD.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
