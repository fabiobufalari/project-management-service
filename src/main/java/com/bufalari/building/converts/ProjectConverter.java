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

        // Calcula as medidas da parede
        entity.setLinearFootage(wallCalculationService.calculateLinearFootage(entity.getTotalLengthInFeet()));
        entity.setSquareFootage(wallCalculationService.calculateSquareFootage(entity.getTotalLengthInFeet(), entity.getTotalHeightInFeet()));

        // Gerar um novo UUID para a WallEntity
        UUID newUuid = UUID.randomUUID();
        entity.setUuid(newUuid);

        // Converter as janelas
        List<WindowEntity> windowEntities = dto.getWindows().stream()
                .map(windowConverter::toEntity)
                .collect(Collectors.toList());
        entity.setWindows(windowEntities);

        // Converter as portas
        List<DoorEntity> doorEntities = dto.getDoors().stream()
                .map(doorConverter::toEntity)
                .collect(Collectors.toList());
        entity.setDoors(doorEntities);

        // Persistir a WallEntity primeiro
        entity = wallRepository.save(entity);

        // Relacionar a parede com os ambientes (usando WallRoomMapping)
        for (RoomSideDTO roomSideDTO : dto.getRoomSides()) {
            String roomType = roomSideDTO.getRoomType();

            // Buscar a RoomEntity pelo roomType e floorNumber
            RoomEntity roomEntity = roomRepository.findByRoomTypeAndFloorNumber(roomType, floorNumber)
                    .orElseGet(() -> {
                        // Se a RoomEntity não existir, criar uma nova com UUID
                        RoomEntity newRoom = new RoomEntity();
                        newRoom.setRoomType(roomType);
                        newRoom.setFloorNumber(floorNumber);
                        newRoom.setWetArea(roomSideDTO.isWetArea());
                        newRoom.setUuid(UUID.randomUUID());
                        return roomRepository.save(newRoom);
                    });

            // ===>>> COPIAR UUID DA ROOMENTITY PARA A WALLENTITY: <<<===
            entity.setRoomUuid(roomEntity.getUuid());

            // ===>>> ATUALIZAR A WALLENTITY ANTES DE CRIAR O MAPEAMENTO: <<<===
            entity = wallRepository.save(entity);

            // Criar WallRoomMapping
            WallRoomMapping mapping = new WallRoomMapping();
            mapping.setWall(entity);
            mapping.setRoom(roomEntity);
            mapping.setSide(roomSideDTO.getSideOfWall());

            // Persistir o WallRoomMapping
            wallRoomMappingRepository.save(mapping);
            System.out.println("WallRoomMapping salvo com ID: " + mapping.getId());

            // ===>>> IMPORTANTE: <<<===
            // Adicionar a parede na lista de paredes da RoomEntity (bidirecional)
            roomEntity.getWalls().add(entity);
            // Não é necessário adicionar roomEntity em entity.getRooms(), pois o mapeamento é feito por "mappedBy"

            // Determinar o tipo de material da parede com base no ambiente
            String materialType = wallCalculationService.determineMaterialType(entity, roomEntity);
            entity.setMaterialType(materialType);
        }

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
        // ... (atribuir valores da FloorEntity a partir do DTO) ...

        // Converter as paredes (walls)
        List<WallEntity> walls = dto.getWalls().stream()
                .map(wallDTO -> convertWall(wallDTO, dto.getFloorNumber()))
                .collect(Collectors.toList());
        entity.setWalls(walls);

        // ===>>> GERAR UUID PARA CADA ROOMENTITY E ASSOCIAR ÀS PAREDES: <<<===
        for (WallDTO wallDTO : dto.getWalls()) { // Iterar pelos WallDTOs
            for (RoomSideDTO roomSideDTO : wallDTO.getRoomSides()) { // Acessar roomSides do DTO
                String roomType = roomSideDTO.getRoomType();
                int floorNumber = dto.getFloorNumber(); // Obter floorNumber do dto

                // Buscar a RoomEntity pelo roomType e floorNumber
                RoomEntity roomEntity = roomRepository.findByRoomTypeAndFloorNumber(roomType, floorNumber)
                        .orElseGet(() -> {
                            // Se a RoomEntity não existir, criar uma nova com UUID
                            RoomEntity newRoom = new RoomEntity();
                            newRoom.setRoomType(roomType);
                            newRoom.setFloorNumber(floorNumber);
                            newRoom.setWetArea(roomSideDTO.isWetArea());
                            newRoom.setUuid(UUID.randomUUID()); // Gerar o UUID do ambiente
                            return roomRepository.save(newRoom);
                        });

                // ===>>> ASSOCIAR A PAREDE À ROOMENTITY: <<<===
                // Buscar a WallEntity correspondente ao wallDTO
                WallEntity wallEntity = wallRepository.findByWallId(wallDTO.getWallId()).orElseThrow(() -> new RuntimeException("Parede não encontrada!"));

                // ===>>> COPIAR UUID DA ROOMENTITY PARA A WALLENTITY: <<<===
                wallEntity.setRoomUuid(roomEntity.getUuid());

                // ===>>> ATUALIZAR A WALLENTITY: <<<===
                wallEntity = wallRepository.save(wallEntity); // Salvar a entidade após a atualização do roomUuid

                // Criar WallRoomMapping
                WallRoomMapping mapping = new WallRoomMapping();
                mapping.setWall(wallEntity);
                mapping.setRoom(roomEntity);
                mapping.setSide(roomSideDTO.getSideOfWall());

                wallRoomMappingRepository.save(mapping);
                System.out.println("WallRoomMapping salvo com ID: " + mapping.getId());
            }
        }

        // ... (Converter outros elementos: ceiling, baseboards, etc.) ...

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