package com.bufalari.building.service;

import com.bufalari.building.requestDTO.ProjectInfoDTO;
import com.bufalari.building.responseDTO.CalculationResponseDTO;
import com.bufalari.building.responseDTO.ProjectDetailResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ProjectDetailResponseDTO createProject(ProjectInfoDTO projectInfoDTO);
    ProjectDetailResponseDTO getProjectDetailById(UUID id);
    List<ProjectDetailResponseDTO> getAllProjects();
    ProjectDetailResponseDTO updateProject(UUID id, ProjectInfoDTO projectInfoDTO);
    void deleteProject(UUID id);
    CalculationResponseDTO calculateMaterials(ProjectInfoDTO projectInfoDTO); // Este pode ser complexo
}