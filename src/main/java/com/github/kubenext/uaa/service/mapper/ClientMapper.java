package com.github.kubenext.uaa.service.mapper;

import com.github.kubenext.uaa.domain.Client;
import com.github.kubenext.uaa.service.dto.ClientDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author shangjin.li
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toClient(ClientDTO clientDTO);

    ClientDTO toClientDTO(Client client);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClient(@MappingTarget Client client, ClientDTO clientDTO);

}
