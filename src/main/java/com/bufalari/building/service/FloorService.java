package com.bufalari.building.service;

import com.bufalari.building.entity.FloorEntity;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.requestDTO.CalculationStructureDTO; // Usado como DTO de entrada para o andar
import com.bufalari.building.responseDTO.FloorResponseDTO;

import java.util.List;
import java.util.UUID;

public interface FloorService {
    FloorEntity createFloorFromCalcStructure(CalculationStructureDTO calcStructureDTO, ProjectEntity project);
    FloorResponseDTO getFloorResponseById(UUID floorId);
    List<FloorResponseDTO> getFloorsByProjectId(UUID projectId);
    // Outros métodos CRUD para Floor, se necessário
}