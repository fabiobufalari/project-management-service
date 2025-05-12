package com.bufalari.building.converts;

import com.bufalari.building.entity.LocationEntity;
import com.bufalari.building.requestDTO.LocationDTO;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {

    public LocationEntity requestDtoToEntity(LocationDTO dto) {
        if (dto == null) return null;
        LocationEntity entity = new LocationEntity();
        // O ID da entidade será nulo aqui; será gerado ao salvar
        // ou definido pelo serviço se o DTO tiver um locationId para atualização
        entity.setId(dto.getLocationId()); // <<< Permite que o DTO passe um ID para atualização
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setProvince(dto.getProvince());
        entity.setPostalCode(dto.getPostalCode());
        return entity;
    }

    public LocationDTO entityToDto(LocationEntity entity) {
        if (entity == null) return null;
        LocationDTO dto = new LocationDTO();
        dto.setLocationId(entity.getId()); // <<< Mapeia o ID da entidade para o DTO
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setProvince(entity.getProvince());
        dto.setPostalCode(entity.getPostalCode());
        return dto;
    }
}