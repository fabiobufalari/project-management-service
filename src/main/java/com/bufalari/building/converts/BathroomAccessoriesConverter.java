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
        BathroomAccessoriesEntity entity = new BathroomAccessoriesEntity();
        entity.setShower(convertShower(dto.getShower()));
        entity.setSink(convertSink(dto.getSink()));
        entity.setToilet(convertToilet(dto.getToilet()));
        return entity;
    }

    private ShowerEntity convertShower(ShowerDTO dto) {
        ShowerEntity entity = new ShowerEntity();
        entity.setType(dto.getType());
        entity.setMaterial(dto.getMaterial());
        entity.setHeightFoot(dto.getHeightFoot());
        return entity;
    }

    private SinkEntity convertSink(SinkDTO dto) {
        SinkEntity entity = new SinkEntity();
        entity.setMaterial(dto.getMaterial());
        entity.setFaucetMaterial(dto.getFaucetMaterial());
        return entity;
    }

    private ToiletEntity convertToilet(ToiletDTO dto) {
        ToiletEntity entity = new ToiletEntity();
        entity.setType(dto.getType());
        entity.setMaterial(dto.getMaterial());
        return entity;
    }
}
