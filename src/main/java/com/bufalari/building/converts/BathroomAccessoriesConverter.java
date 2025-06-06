package com.bufalari.building.converts;

import com.bufalari.building.entity.BathroomAccessoriesEntity;
import com.bufalari.building.entity.ShowerEntity;
import com.bufalari.building.entity.SinkEntity;
import com.bufalari.building.entity.ToiletEntity;
import com.bufalari.building.requestDTO.BathroomAccessoriesDTO;
import com.bufalari.building.requestDTO.ShowerDTO;
import com.bufalari.building.requestDTO.SinkDTO;
import com.bufalari.building.requestDTO.ToiletDTO;
import org.springframework.stereotype.Component;

@Component
public class BathroomAccessoriesConverter {

    public BathroomAccessoriesEntity toEntity(BathroomAccessoriesDTO dto) {
        if (dto == null) return null;
        BathroomAccessoriesEntity entity = new BathroomAccessoriesEntity();
        if (dto.getShower() != null) entity.setShower(convertShowerDtoToEntity(dto.getShower()));
        if (dto.getSink() != null) entity.setSink(convertSinkDtoToEntity(dto.getSink()));
        if (dto.getToilet() != null) entity.setToilet(convertToiletDtoToEntity(dto.getToilet()));
        return entity;
    }

    public BathroomAccessoriesDTO toDto(BathroomAccessoriesEntity entity) {
        if (entity == null) return null;
        BathroomAccessoriesDTO dto = new BathroomAccessoriesDTO();
        if (entity.getShower() != null) dto.setShower(convertShowerEntityToDto(entity.getShower()));
        if (entity.getSink() != null) dto.setSink(convertSinkEntityToDto(entity.getSink()));
        if (entity.getToilet() != null) dto.setToilet(convertToiletEntityToDto(entity.getToilet()));
        return dto;
    }

    // --- Métodos auxiliares de conversão ---
    private ShowerEntity convertShowerDtoToEntity(ShowerDTO dto) {
        if (dto == null) return null;
        return ShowerEntity.builder().type(dto.getType()).material(dto.getMaterial()).heightFoot(dto.getHeightFoot()).build();
    }
    private ShowerDTO convertShowerEntityToDto(ShowerEntity entity) {
        if (entity == null) return null;
        return new ShowerDTO(entity.getType(), entity.getMaterial(), entity.getHeightFoot());
    }

    private SinkEntity convertSinkDtoToEntity(SinkDTO dto) {
        if (dto == null) return null;
        return SinkEntity.builder().material(dto.getMaterial()).faucetMaterial(dto.getFaucetMaterial()).build();
    }
    private SinkDTO convertSinkEntityToDto(SinkEntity entity) {
        if (entity == null) return null;
        return new SinkDTO(entity.getMaterial(), entity.getFaucetMaterial());
    }

    private ToiletEntity convertToiletDtoToEntity(ToiletDTO dto) {
        if (dto == null) return null;
        return ToiletEntity.builder().type(dto.getType()).material(dto.getMaterial()).build();
    }
    private ToiletDTO convertToiletEntityToDto(ToiletEntity entity) {
        if (entity == null) return null;
        return new ToiletDTO(entity.getType(), entity.getMaterial());
    }
}