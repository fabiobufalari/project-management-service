package com.bufalari.building.controller;

import com.bufalari.building.converts.ProjectConverter;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.requestDTO.ProjectInfoDTO;
import com.bufalari.building.responseDTO.CalculationResponseDTO;
import com.bufalari.building.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectConverter projectConverter;

    @PostMapping("/create")
    public ResponseEntity<ProjectEntity> createProject(@RequestBody ProjectInfoDTO projectInfoDTO) {
        ProjectEntity projectEntity = projectConverter.toEntity(projectInfoDTO);
        ProjectEntity savedProject = projectService.createProject(projectEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponseDTO> calculateMaterials(@RequestBody ProjectInfoDTO projectInfoDTO) {
        CalculationResponseDTO response = projectService.calculateMaterials(projectInfoDTO);
        return ResponseEntity.ok(response);
    }
}
