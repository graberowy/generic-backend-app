package app.address.resources;

import app.address.client.Address;
import app.address.model.UserAddressTotal;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
/**
 * This interface is for map userAddressTotal to userAddressTotalDTO and reverse
 */
@Mapper(componentModel = "spring")
public interface UserAddressTotalMapper {
    /**
     * This method is for map userAddressTotal to userAddressTotalDTO
     * @param userAddressTotal object
     * @return userAddressTotalDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")
    @BeanMapping(ignoreByDefault = true)
    UserAddressTotalDTO mapToUserAddressTotalDTO(UserAddressTotal userAddressTotal);

    /**
     * This method is for map address to addressDTO
     * @param address object
     * @return addressDTO
     */
    @Mapping(source = "code", target = "code")
    @Mapping(source = "street", target = "street")
    @BeanMapping(ignoreByDefault = true)
    AddressDTO mapToAddressDTO(Address address);

    /**
     * This method work conversely to mapToUserAddressTotalDTO
     * @param userAddressTotalDTO object
     * @return userAddressTotal
     */
    @InheritInverseConfiguration(name = "mapToUserAddressTotalDTO")
    UserAddressTotal mapToUserAddressTotal(UserAddressTotalDTO userAddressTotalDTO);

    /**
     * This method work conversely to mapToAddressDTO
     * @param addressDTO object
     * @return address
     */
    @InheritInverseConfiguration(name = "mapToAddressDTO")
    Address mapToAddress(AddressDTO addressDTO);
}
