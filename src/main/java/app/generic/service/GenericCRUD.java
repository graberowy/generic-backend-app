package app.generic.service;

import app.data.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is for cover generic operations on specific type
 *
 * @param <T>  entity type
 * @param <ID> id type
 * @author pawel.jankowski
 */
public abstract class GenericCRUD<T extends Data<ID>, ID> implements GenericService<T, ID> {
    private final JpaRepository<T, ID> repository;

    /**
     * This is constructor for use generic operations on specific type
     *
     * @param repository specified type interface
     */
    public GenericCRUD(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    /**
     * This method search record using provided id
     *
     * @param id Specific id for identify record
     * @return record, if id exists
     */
    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    /**
     * This method add new record of specific entity
     *
     * @param t new record with specific parameters
     * @return save new record
     */
    @Override
    public T save(T t) {
        t.setId(null);
        return repository.save(t);
    }

    /**
     * This method is for update data for existing record
     *
     * @param t updated data of specific parameters of specific type
     * @return specific type record
     */
    @Override
    public Optional<T> update(T t) {
        return repository.findById(t.getId())
                .map(org -> repository.save(t));
    }

    /**
     * This method is for search all records in specific type
     *
     * @return list of existing records
     */
    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * This method is for remove existing record
     *
     * @param id specific id in specific format
     */
    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
}
