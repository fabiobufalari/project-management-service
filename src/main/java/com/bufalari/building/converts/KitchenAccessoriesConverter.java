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
        KitchenAccessoriesEntity entity = new KitchenAccessoriesEntity();
        entity.setSink(convertSink(dto.getSink()));
        entity.setCountertop(convertCountertop(dto.getCountertop()));
        entity.setCabinets(convertCabinets(dto.getCabinets()));
        return entity;
    }

    private CountertopEntity convertCountertop(CountertopDTO dto) {
        CountertopEntity entity = new CountertopEntity();
        entity.setMaterial(dto.getMaterial());
        entity.setLengthFoot(dto.getLengthFoot());
        entity.setWidthFoot(dto.getWidthFoot());
        return entity;
    }

    private CabinetsEntity convertCabinets(CabinetsDTO dto) {
        CabinetsEntity entity = new CabinetsEntity();
        entity.setMaterial(dto.getMaterial());
        entity.setColor(dto.getColor());
        return entity;
    }

    private SinkEntity convertSink(SinkDTO dto) {
        SinkEntity entity = new SinkEntity();
        entity.setMaterial(dto.getMaterial());
        entity.setFaucetMaterial(dto.getFaucetMaterial());
        return entity;
    }

}
