package com.bufalari.building.service;

import com.bufalari.building.entity.FloorEntity;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.requestDTO.WallDTO;
import com.bufalari.building.responseDTO.WallCalculationDTO;

import java.util.UUID;

public interface WallService {
    WallEntity createWallFromDTO(WallDTO wallDTO, FloorEntity floor, ProjectEntity project);
    WallCalculationDTO getWallCalculationDtoByUuid(UUID wallUuid);
    WallDTO getWallDtoByUuid(UUID wallUuid); // Para consistência com WallController
    // Outros métodos CRUD para Wall, se necessário
}