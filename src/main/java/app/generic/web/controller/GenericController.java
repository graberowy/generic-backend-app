package app.generic.web.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * This interface is for define CRUD methods on objects
 *
 * @param <DTO> object DTO
 * @param <ID>  object id
 * @author pawel.jankowski
 */
public interface GenericController<DTO, ID> {
    /**
     * This method is for get all existing objects records
     *
     * @return list of existing objects
     */
    ResponseEntity<List<DTO>> getAll();

    /**
     * This method is for get specific object
     *
     * @param id identity of object
     * @return object with existing id or error if not found
     */
    ResponseEntity<DTO> getById(ID id);

    /**
     * This method is for create new object
     *
     * @param dto result of mapping object
     * @return new object
     */
    ResponseEntity<DTO> saveNew(DTO dto);

    /**
     * This method is for update data of existing object
     *
     * @param id  identity of object
     * @param dto result of mapping object
     * @return updated object or error if not found
     */
    ResponseEntity<DTO> updateRecord(ID id, DTO dto);

    /**
     * This method is for remove existing object
     *
     * @param id identity of object
     * @return success status if object exist or error if not
     */
    ResponseEntity<Void> deleteRecord(ID id);
}
