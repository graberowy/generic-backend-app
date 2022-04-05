package app.generic.service;


import app.data.Data;

import java.util.List;
import java.util.Optional;

/**
 * This interface is for cover generic operations on specific type
 * @param <T> entity type
 * @param <ID> id type
 * @author pawel.jankowski
 */
public interface GenericService<T extends Data<ID>, ID> {
    /**
     * This method search record using provided id
     * @param id Specific id for identify record
     * @return record, if id exists
     */
    Optional<T> findById(ID id);

    /**
     * This method add new record of specific entity
     * @param t new record with specific parameters
     * @return save new record
     */
    T save(T t);

    /**
     * This method is for update data for existing record
     * @param t updated data of specific parameters of specific type
     * @return specific type record
     */
    Optional<T> update(T t);

    /**
     * This method is for search all records in specific type
     * @return list of existing records
     */
    List<T> findAll();

    /**
     * This method is for remove existing record
     * @param id specific id in specific format
     */
    void delete(ID id);
}
