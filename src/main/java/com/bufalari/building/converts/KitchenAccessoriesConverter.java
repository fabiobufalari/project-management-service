package com.bufalari.building.converts;

import com.bufalari.building.entity.CabinetsEntity;
import com.bufalari.building.entity.CountertopEntity;
import com.bufalari.building.entity.KitchenAccessoriesEntity;
import com.bufalari.building.entity.SinkEntity;
import com.bufalari.building.requestDTO.CabinetsDTO;
import com.bufalari.building.requestDTO.CountertopDTO;
import com.bufalari.building.requestDTO.KitchenAccessoriesDTO;
import com.bufalari.building.requestDTO.SinkDTO;
import org.springframework.stereotype.Component;

@Component
public class KitchenAccessoriesConverter {

    public KitchenAccessoriesEntity toEntity(KitchenAccessoriesDTO dto) {
        if (dto == null) return null;
        KitchenAccessoriesEntity entity = new KitchenAccessoriesEntity();
        if (dto.getSink() != null) entity.setSink(convertSinkDtoToEntity(dto.getSink()));
        if (dto.getCountertop() != null) entity.setCountertop(convertCountertopDtoToEntity(dto.getCountertop()));
        if (dto.getCabinets() != null) entity.setCabinets(convertCabinetsDtoToEntity(dto.getCabinets()));
        return entity;
    }

    public KitchenAccessoriesDTO toDto(KitchenAccessoriesEntity entity) {
        if (entity == null) return null;
        KitchenAccessoriesDTO dto = new KitchenAccessoriesDTO();
        if (entity.getSink() != null) dto.setSink(convertSinkEntityToDto(entity.getSink()));
        if (entity.getCountertop() != null) dto.setCountertop(convertCountertopEntityToDto(entity.getCountertop()));
        if (entity.getCabinets() != null) dto.setCabinets(convertCabinetsEntityToDto(entity.getCabinets()));
        return dto;
    }

    // --- Métodos auxiliares ---
    private SinkEntity convertSinkDtoToEntity(SinkDTO dto) {
        if (dto == null) return null;
        return SinkEntity.builder().material(dto.getMaterial()).faucetMaterial(dto.getFaucetMaterial()).build();
    }
    private SinkDTO convertSinkEntityToDto(SinkEntity entity) {
        if (entity == null) return null;
        return new SinkDTO(entity.getMaterial(), entity.getFaucetMaterial());
    }

    private CountertopEntity convertCountertopDtoToEntity(CountertopDTO dto) {
        if (dto == null) return null;
        return CountertopEntity.builder().material(dto.getMaterial()).lengthFoot(dto.getLengthFoot()).widthFoot(dto.getWidthFoot()).build();
    }
    private CountertopDTO convertCountertopEntityToDto(CountertopEntity entity) {
        if (entity == null) return null;
        return new CountertopDTO(entity.getMaterial(), entity.getLengthFoot(), entity.getWidthFoot());
    }

    private CabinetsEntity convertCabinetsDtoToEntity(CabinetsDTO dto) {
        if (dto == null) return null;
        return CabinetsEntity.builder().material(dto.getMaterial()).color(dto.getColor()).build();
    }
    private CabinetsDTO convertCabinetsEntityToDto(CabinetsEntity entity) {
        if (entity == null) return null;
        return new CabinetsDTO(entity.getMaterial(), entity.getColor());
    }
}