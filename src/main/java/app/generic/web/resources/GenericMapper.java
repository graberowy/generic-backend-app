package app.generic.web.resources;

import app.data.Data;


/**
 * This interface is to map object to DTO and reverse
 *
 * @param <DTO> object DTO
 * @param <T>   object type
 * @author pawel.jankowski
 */
public interface GenericMapper<DTO, T extends Data> {
    /**
     * This method is to map object to object DTO
     *
     * @param t mapping object
     * @return object DTO
     */
    DTO getDTO(T t);

    /**
     * This method is to map object DTO to object model
     *
     * @param dto object DTO
     * @return object model
     */
    T getModel(DTO dto);


}
