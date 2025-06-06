package com.bufalari.building.service;

import com.bufalari.building.converts.FloorConverter;
import com.bufalari.building.entity.FloorEntity;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.exception.ResourceNotFoundException;
import com.bufalari.building.repository.FloorRepository;
import com.bufalari.building.requestDTO.CalculationStructureDTO;
import com.bufalari.building.requestDTO.WallDTO;
import com.bufalari.building.responseDTO.FloorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FloorServiceImpl implements FloorService {

    private static final Logger log = LoggerFactory.getLogger(FloorServiceImpl.class);

    private final FloorRepository floorRepository;
    private final FloorConverter floorConverter;
    private final WallService wallService; // Injete o WallService para criar paredes

    @Override
    public FloorEntity createFloorFromCalcStructure(CalculationStructureDTO calcStructureDTO, ProjectEntity project) {
        log.info("Criando andar número {} para o projeto {}", calcStructureDTO.getFloorNumber(), project.getId());
        FloorEntity floorEntity = floorConverter.requestDtoToEntity(calcStructureDTO, project); // Conversor agora só mapeia

        // Salva a entidade Floor primeiro para ter um ID
        FloorEntity savedFloor = floorRepository.save(floorEntity);
        log.debug("Andar base salvo com ID: {}", savedFloor.getId());

        // Agora, cria as paredes associadas a este andar
        if (calcStructureDTO.getWalls() != null && !calcStructureDTO.getWalls().isEmpty()) {
            List<WallEntity> walls = new ArrayList<>();
            for (WallDTO wallDTO : calcStructureDTO.getWalls()) {
                // O WallService cuidará da conversão, cálculo e persistência da WallEntity
                // e da criação dos WallRoomMappings.
                WallEntity createdWall = wallService.createWallFromDTO(wallDTO, savedFloor, project);
                walls.add(createdWall);
            }
            // A associação de WallEntity com FloorEntity é geralmente feita com WallEntity tendo um campo floor
            // ou FloorEntity tendo uma lista de Walls, dependendo do mapeamento.
            // Se FloorEntity tem List<WallEntity> walls (mappedBy="floor"), o save já lida.
            // Se a relação é gerenciada por Room e WallRoomMapping, a lógica de WallService cuida.
            // Para o modelo atual, WallEntity tem floorNumber e Project, e se relaciona com Rooms.
            // FloorEntity tem uma lista de Rooms.
        }
        return savedFloor; // Retorna o andar salvo (pode ser recarregado se necessário para obter relações)
    }

    @Override
    @Transactional(readOnly = true)
    public FloorResponseDTO getFloorResponseById(UUID floorId) {
        return floorRepository.findById(floorId)
                .map(floorConverter::entityToDetailResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Floor not found with ID: " + floorId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FloorResponseDTO> getFloorsByProjectId(UUID projectId) {
        return floorRepository.findByProject_Id(projectId).stream()
                .map(floorConverter::entityToDetailResponseDto)
                .collect(Collectors.toList());
    }
}