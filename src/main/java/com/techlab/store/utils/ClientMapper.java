package com.techlab.store.utils;

import com.techlab.store.dto.ClientDTO;
import com.techlab.store.dto.OrderDTO;
import com.techlab.store.dto.OrderDetailDTO;
import com.techlab.store.entity.Client;
import com.techlab.store.entity.Order;
import com.techlab.store.entity.OrderDetail;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ClientMapper {

    // --- SALIDA (GET):
    ClientDTO toDto(Client client);

    @Mapping(target = "clientId", source = "client.id")
    OrderDTO toOrderDto(Order order);

    List<ClientDTO> toDtoList(List<Client> clients);

    // --- ENTRADA (POST/PUT)

    // Se ignora orders para no romper relacion de entidad client->orders

    // Para crear un cliente nuevo
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Client toEntity(ClientDTO dto);

    @Mapping(target = "product.id", source = "product.id")
    @Mapping(target = "product.name", source = "product.name")
    @Mapping(target = "product.price", source = "product.price")
    OrderDetailDTO toDetailDto(OrderDetail detail);

    // Para editar un cliente existente
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateClientFromDto(ClientDTO dto, @MappingTarget Client entity);

}
