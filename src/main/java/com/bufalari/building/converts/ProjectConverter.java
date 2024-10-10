package com.bufalari.building.converts;

import com.bufalari.building.entity.FloorEntity;
import com.bufalari.building.entity.LocationEntity;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.requestDTO.CalculationStructureDTO;
import com.bufalari.building.requestDTO.LocationDTO;
import com.bufalari.building.requestDTO.ProjectInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectConverter {

    private final WallConverter wallConverter;
    private final CeilingConverter ceilingConverter;
    private final BaseboardConverter baseboardConverter;
    private final PaintingConverter paintingConverter;
    private final BalconyConverter balconyConverter;
    private final BathroomAccessoriesConverter bathroomAccessoriesConverter;
    private final KitchenAccessoriesConverter kitchenAccessoriesConverter;
    private final RoofConverter roofConverter;

    @Autowired
    public ProjectConverter(WallConverter wallConverter,
                            CeilingConverter ceilingConverter,
                            BaseboardConverter baseboardConverter,
                            PaintingConverter paintingConverter,
                            BalconyConverter balconyConverter,
                            BathroomAccessoriesConverter bathroomAccessoriesConverter,
                            KitchenAccessoriesConverter kitchenAccessoriesConverter,
                            RoofConverter roofConverter) {
        this.wallConverter = wallConverter;
        this.ceilingConverter = ceilingConverter;
        this.baseboardConverter = baseboardConverter;
        this.paintingConverter = paintingConverter;
        this.balconyConverter = balconyConverter;
        this.bathroomAccessoriesConverter = bathroomAccessoriesConverter;
        this.kitchenAccessoriesConverter = kitchenAccessoriesConverter;
        this.roofConverter = roofConverter;
    }

    public ProjectEntity toEntity(ProjectInfoDTO dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectName(dto.getProjectName());
        entity.setDateTime(dto.getDateTime());
        entity.setBuildingType(dto.getBuildingType());
        entity.setNumberOfFloors(dto.getNumberOfFloors());
        entity.setHasBasement(dto.isHasBasement());

        // Converte a localização
        entity.setLocationEntity(convertLocationEntity(dto.getLocation()));

        // Converte os andares (floors)
        List<FloorEntity> floors = dto.getCalculationStructure().stream()
                .map(this::convertFloor)
                .collect(Collectors.toList());
        entity.setFloors(floors);

        return entity;
    }

    private FloorEntity convertFloor(CalculationStructureDTO dto) {
        FloorEntity entity = new FloorEntity();
        entity.setFloorId(dto.getFloorId());
        entity.setFloorNumber(dto.getFloorNumber());
        entity.setAreaSquareFeet(dto.getAreaSquareFeet());
        entity.setHeated(dto.isHeated());
        entity.setMaterial(dto.getMaterial());

        List<WallEntity> walls = dto.getWalls().stream()
                .map(wallConverter::toEntity)
                .collect(Collectors.toList());
        entity.setWalls(walls);

        entity.setCeiling(ceilingConverter.toEntity(dto.getCeiling()));
        entity.setBaseboards(baseboardConverter.toEntity(dto.getBaseboards()));
        entity.setPainting(paintingConverter.toEntity(dto.getPainting()));
        entity.setBalcony(balconyConverter.toEntity(dto.getBalcony()));
        entity.setBathroomAccessories(bathroomAccessoriesConverter.toEntity(dto.getBathroomAccessories()));
        entity.setKitchenAccessories(kitchenAccessoriesConverter.toEntity(dto.getKitchenAccessories()));
        entity.setRoof(roofConverter.toEntity(dto.getRoof()));

        return entity;
    }

    private LocationEntity convertLocationEntity(LocationDTO dto) {
        return new LocationEntity(
                dto.getAddress(),
                dto.getCity(),
                dto.getProvince(),
                dto.getPostalCode()
        );
    }
}
