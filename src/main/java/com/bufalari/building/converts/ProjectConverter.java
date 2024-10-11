package com.bufalari.building.converts;

import com.bufalari.building.entity.*;
import com.bufalari.building.repository.RoomRepository;
import com.bufalari.building.repository.WallRepository;
import com.bufalari.building.repository.WallRoomMappingRepository;
import com.bufalari.building.requestDTO.*;
import com.bufalari.building.service.WallCalculationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProjectConverter {

    @Autowired
    private WallCalculationService wallCalculationService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private WallRoomMappingRepository wallRoomMappingRepository;

    @Autowired
    private WallRepository wallRepository;

    @Autowired
    private WindowConverter windowConverter;

    @Autowired
    private DoorConverter doorConverter;

    @Transactional
    public WallEntity convertWall(WallDTO dto, int floorNumber) {
        WallEntity entity = new WallEntity();
        entity.setWallId(dto.getWallId());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setLengthFoot(dto.getLengthFoot());
        entity.setLengthInches(dto.getLengthInches());
        entity.setHeightFoot(dto.getHeightFoot());
        entity.setHeightInches(dto.getHeightInches());
        entity.setWallThicknessInch(dto.getWallThicknessInch());
        entity.setFloorNumber(floorNumber);

        // Gerar um novo UUID
        UUID newUuid = UUID.randomUUID();
        entity.setUuid(newUuid); // Definir o UUID diretamente

        List<WindowEntity> windowEntities = dto.getWindows().stream()
                .map(windowConverter::toEntity) // Chamar o método de conversão
                .collect(Collectors.toList());
        entity.setWindows(windowEntities); // Definir a lista na WallEntity

        // Converter as portas
        List<DoorEntity> doorEntities = dto.getDoors().stream()
                .map(doorConverter::toEntity) // Chamar o método de conversão
                .collect(Collectors.toList());
        entity.setDoors(doorEntities); // Definir a lista na WallEntity

        // Calcula as medidas da parede
        entity.setLinearFootage(wallCalculationService.calculateLinearFootage(entity.getTotalLengthInFeet()));
        entity.setSquareFootage(wallCalculationService.calculateSquareFootage(entity.getTotalLengthInFeet(), entity.getTotalHeightInFeet()));

        // Persistir a WallEntity primeiro
        entity = wallRepository.save(entity);

        // Relacionar a parede com os ambientes (usando WallRoomMapping)
        for (RoomSideDTO roomSideDTO : dto.getRoomSides()) {
            RoomEntity roomEntity = roomRepository.findByRoomTypeAndFloorNumber(
                    roomSideDTO.getRoomType(), floorNumber).orElse(null);

            if (roomEntity != null) {
                System.out.println("RoomEntity encontrada: " + roomEntity.getRoomType());

                WallRoomMapping mapping = new WallRoomMapping();
                mapping.setWall(entity);
                mapping.setRoom(roomEntity);
                mapping.setSide(roomSideDTO.getSideOfWall());

                wallRoomMappingRepository.save(mapping);
                System.out.println("WallRoomMapping salvo com ID: " + mapping.getId());

                // Adicionar a parede à lista de paredes do ambiente (bidirecional)
                roomEntity.getWalls().add(entity);
                entity.getRooms().add(roomEntity);

                // ===>>> REMOVER A DEFINIÇÃO DE materialType AQUI <<<===
                // ===>>> (ela será definida após o loop) <<<===
            } else {
                System.out.println("RoomEntity não encontrada para roomType: " + roomSideDTO.getRoomType() + " e floorNumber: " + floorNumber);
            }
        }

        // ===>>> DETERMINAR O MATERIALTYPE APÓS O LOOP: <<<===
        String materialType = determineWallMaterialType(entity); // Novo método para determinar o material
        entity.setMaterialType(materialType);

        // Atualizar a WallEntity com o materialType definido
        entity = wallRepository.save(entity);

        // Forçar o carregamento da lista rooms
        entity.getRooms().size();

        return entity;
    }

    // ===>>> NOVO MÉTODO PARA DETERMINAR O MATERIAL DA PAREDE: <<<===
    private String determineWallMaterialType(WallEntity wall) {
        if (wall.isExternal()) {
            return "Concrete"; // Parede externa sempre de concreto
        }

        // Verificar se algum dos ambientes é área molhada
        boolean hasWetArea = wall.getRooms().stream().anyMatch(RoomEntity::isWetArea);

        if (hasWetArea) {
            return "Moisture Resistant Drywall"; // Usar material resistente à umidade
        } else {
            return "Drywall"; // Usar drywall comum
        }
    }


    public ProjectEntity toEntity(ProjectInfoDTO dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectName(dto.getProjectName());
        entity.setDateTime(LocalDateTime.parse(dto.getDateTime()));
        entity.setBuildingType(dto.getBuildingType());
        entity.setNumberOfFloors(dto.getNumberOfFloors());
        entity.setHasBasement(dto.isHasBasement());

        // Converter a localização usando o novo método convertLocationEntity
        entity.setLocationEntity(convertLocationEntity(dto.getLocation()));

        // Converter e definir os andares do projeto usando o novo método convertFloor
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
        entity.setWetArea(dto.isWetArea());

        // Converter as paredes (walls)
        List<WallEntity> walls = dto.getWalls().stream()
                .map(wallDTO -> convertWall(wallDTO, dto.getFloorNumber())) // Passar floorNumber para convertWall
                .collect(Collectors.toList());
        entity.setWalls(walls);

        // ... (Converter outros elementos: ceiling, baseboards, etc. - LEMBRE-SE de implementar esses métodos!) ...

        return entity;
    }

    // Método para converter LocationDTO em LocationEntity
    private LocationEntity convertLocationEntity(LocationDTO dto) {
        return new LocationEntity(
                dto.getAddress(),
                dto.getCity(),
                dto.getProvince(),
                dto.getPostalCode()
        );
    }

}