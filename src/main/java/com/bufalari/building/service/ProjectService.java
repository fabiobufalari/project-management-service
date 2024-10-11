package com.bufalari.building.service;


import com.bufalari.building.converts.ProjectConverter;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.repository.ProjectRepository;
import com.bufalari.building.requestDTO.ProjectInfoDTO;
import com.bufalari.building.responseDTO.CalculationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectConverter projectConverter;


    public ProjectEntity createProject(ProjectEntity projectEntity) {
        return projectRepository.save(projectEntity);
    }

    public ProjectEntity createProject(ProjectInfoDTO projectInfoDTO) {
        ProjectEntity entity = projectConverter.toEntity(projectInfoDTO);
        return projectRepository.save(entity);
    }

    public CalculationResponseDTO calculateMaterials(ProjectInfoDTO projectInfoDTO) {
        // Perform all the calculations here for walls, floors, etc.
        // Return the response DTO with calculated values
        CalculationResponseDTO response = new CalculationResponseDTO();
        // Fill with calculations...
        return response;
    }
}