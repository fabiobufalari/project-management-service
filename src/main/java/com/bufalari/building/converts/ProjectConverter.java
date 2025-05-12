package com.bufalari.building.converts;

import com.bufalari.building.entity.LocationEntity;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.enums.ProjectStatus;
import com.bufalari.building.requestDTO.LocationDTO;
import com.bufalari.building.requestDTO.ProjectInfoDTO;
import com.bufalari.building.responseDTO.FloorResponseDTO; // Import correto
import com.bufalari.building.responseDTO.ProjectDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectConverter {

    private static final Logger log = LoggerFactory.getLogger(ProjectConverter.class);

    private final LocationConverter locationConverter;
    private final FloorConverter floorConverter;

    public ProjectEntity requestDtoToEntity(ProjectInfoDTO dto) {
        if (dto == null) return null;
        log.debug("Convertendo ProjectInfoDTO para ProjectEntity: {}", dto.getProjectName());

        ProjectEntity entity = new ProjectEntity();
        entity.setId(dto.getProjectId());
        entity.setProjectIdLegacy(dto.getProjectIdLegacy());
        entity.setProjectName(dto.getProjectName());
        try {
            entity.setDateTime(dto.getDateTime() != null ? LocalDateTime.parse(dto.getDateTime()) : LocalDateTime.now());
        } catch (DateTimeParseException e) {
            log.warn("Falha ao parsear dateTime '{}'. Usando data atual.", dto.getDateTime());
            entity.setDateTime(LocalDateTime.now());
        }
        entity.setBuildingType(dto.getBuildingType());
        entity.setNumberOfFloors(dto.getNumberOfFloors());
        entity.setHasBasement(dto.isHasBasement());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : ProjectStatus.PLANNING);
        entity.setBudgetAmount(dto.getBudgetAmount());
        entity.setCurrency(dto.getCurrency());
        entity.setStartDatePlanned(dto.getStartDatePlanned());
        entity.setEndDatePlanned(dto.getEndDatePlanned());
        entity.setClientId(dto.getClientId());
        entity.setCompanyBranchId(dto.getCompanyBranchId());

        if (dto.getLocation() != null) {
            entity.setLocationEntity(locationConverter.requestDtoToEntity(dto.getLocation()));
        }

        entity.setFloors(new ArrayList<>()); // Floors são adicionados no serviço

        return entity;
    }

    public ProjectDetailResponseDTO entityToDetailResponseDto(ProjectEntity entity) {
        if (entity == null) return null;

        ProjectDetailResponseDTO dto = new ProjectDetailResponseDTO();
        dto.setProjectId(entity.getId());
        dto.setProjectIdLegacy(entity.getProjectIdLegacy());
        dto.setProjectName(entity.getProjectName());
        dto.setDateTime(entity.getDateTime() != null ? entity.getDateTime().toString() : null);
        dto.setBuildingType(entity.getBuildingType());
        dto.setNumberOfFloors(entity.getNumberOfFloors());
        dto.setHasBasement(entity.isHasBasement());
        dto.setStatus(entity.getStatus());
        dto.setBudgetAmount(entity.getBudgetAmount());
        dto.setCurrency(entity.getCurrency());
        dto.setStartDatePlanned(entity.getStartDatePlanned());
        dto.setEndDatePlanned(entity.getEndDatePlanned());
        dto.setStartDateActual(entity.getStartDateActual());
        dto.setEndDateActual(entity.getEndDateActual());
        dto.setClientId(entity.getClientId());
        dto.setCompanyBranchId(entity.getCompanyBranchId());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        dto.setLastModifiedAt(entity.getLastModifiedAt());

        if (entity.getLocationEntity() != null) {
            dto.setLocation(locationConverter.entityToDto(entity.getLocationEntity()));
        }

        if (entity.getFloors() != null) {
            // A conversão aqui deve estar correta agora que ProjectDetailResponseDTO.floors
            // espera List<com.bufalari.building.responseDTO.FloorResponseDTO>
            // e floorConverter.entityToDetailResponseDto retorna com.bufalari.building.responseDTO.FloorResponseDTO
            dto.setFloors(entity.getFloors().stream()
                .map(floorConverter::entityToDetailResponseDto)
                .collect(Collectors.toList()));
        } else {
            dto.setFloors(Collections.emptyList());
        }
        return dto;
    }

    // Novo método para converter Entity para ProjectInfoDTO (para a resposta do POST /create)
    public ProjectInfoDTO entityToInfoResponseDto(ProjectEntity entity) {
        if (entity == null) return null;
        ProjectInfoDTO dto = new ProjectInfoDTO();
        dto.setProjectId(entity.getId()); // ID UUID
        dto.setProjectIdLegacy(entity.getProjectIdLegacy());
        dto.setProjectName(entity.getProjectName());
        dto.setDateTime(entity.getDateTime() != null ? entity.getDateTime().toString() : null);

        if (entity.getLocationEntity() != null) {
            dto.setLocation(locationConverter.entityToDto(entity.getLocationEntity()));
        }
        dto.setBuildingType(entity.getBuildingType());
        dto.setNumberOfFloors(entity.getNumberOfFloors());
        dto.setHasBasement(entity.isHasBasement());
        dto.setStatus(entity.getStatus());
        dto.setBudgetAmount(entity.getBudgetAmount());
        dto.setCurrency(entity.getCurrency());
        dto.setStartDatePlanned(entity.getStartDatePlanned());
        dto.setEndDatePlanned(entity.getEndDatePlanned());
        dto.setClientId(entity.getClientId());
        dto.setCompanyBranchId(entity.getCompanyBranchId());

        // ProjectInfoDTO não inclui a lista detalhada de andares (CalculationStructureDTO) na resposta do POST create.
        // Se precisar, seria necessário mapear FloorEntity para CalculationStructureDTO.
        // Por ora, omitindo essa conversão complexa para a resposta do POST.
        dto.setCalculationStructure(Collections.emptyList()); // Ou nulo, dependendo da preferência

        return dto;
    }
}