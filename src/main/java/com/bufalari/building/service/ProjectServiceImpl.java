package com.bufalari.building.service;

import com.bufalari.building.converts.LocationConverter;
import com.bufalari.building.converts.ProjectConverter;
import com.bufalari.building.entity.FloorEntity;
import com.bufalari.building.entity.LocationEntity;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.exception.ResourceNotFoundException;
import com.bufalari.building.repository.LocationRepository;
import com.bufalari.building.repository.ProjectRepository;
// import com.bufalari.building.requestDTO.CalculationStructureDTO; // Não usado diretamente para criar Floors aqui
import com.bufalari.building.requestDTO.ProjectInfoDTO;
import com.bufalari.building.responseDTO.CalculationResponseDTO;
import com.bufalari.building.responseDTO.ProjectDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
// import java.util.ArrayList; // Não mais necessário aqui para floors
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("projectServiceImpl")
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;
    private final ProjectConverter projectConverter;
    private final FloorService floorService;
    private final LocationRepository locationRepository;

    @Override
    public ProjectDetailResponseDTO createProject(ProjectInfoDTO projectInfoDTO) { // Retorna ProjectDetailResponseDTO
        log.info("Service: Creating new project: {}", projectInfoDTO.getProjectName());

        ProjectEntity projectEntity = projectConverter.requestDtoToEntity(projectInfoDTO);

        if (projectEntity.getLocationEntity() != null && projectEntity.getLocationEntity().getId() == null) {
            LocationEntity savedLocation = locationRepository.save(projectEntity.getLocationEntity());
            projectEntity.setLocationEntity(savedLocation);
            log.debug("New LocationEntity saved with ID: {}", savedLocation.getId());
        }
        
        ProjectEntity savedProject = projectRepository.save(projectEntity);
        log.debug("ProjectEntity base saved with ID: {}", savedProject.getId());

        if (projectInfoDTO.getCalculationStructure() != null && !projectInfoDTO.getCalculationStructure().isEmpty()) {
            final ProjectEntity finalSavedProject = savedProject;
            List<FloorEntity> createdFloors = projectInfoDTO.getCalculationStructure().stream()
                .map(calcStructureDTO -> floorService.createFloorFromCalcStructure(calcStructureDTO, finalSavedProject))
                .collect(Collectors.toList());
            // A associação é feita via floor.setProject(this) dentro do FloorService e pela persistência dos Floors.
            // Se a relação Project->Floors for bidirecional e o lado do Projeto for o "owner"
            // (sem mappedBy em ProjectEntity.floors), então:
            // finalSavedProject.setFloors(createdFloors);
            // projectRepository.save(finalSavedProject); // Salvar novamente para atualizar a lista de floors
            // No entanto, com @OneToMany(mappedBy = "project") em ProjectEntity.floors,
            // o lado FloorEntity é o "owner" da FK, então salvar os floors já estabelece a relação.
            // Para garantir que o DTO de resposta tenha os andares, podemos recarregar o projeto.
        }

        log.info("Project '{}' created successfully with ID: {}", savedProject.getProjectName(), savedProject.getId());
        // Recarregar para obter todas as associações (incluindo andares e suas paredes)
        ProjectEntity reloadedProject = projectRepository.findById(savedProject.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Newly created project not found: " + savedProject.getId()));
        return projectConverter.entityToDetailResponseDto(reloadedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public CalculationResponseDTO calculateMaterials(ProjectInfoDTO projectInfoDTO) {
        log.info("Service: Calculating materials for the project: {}", projectInfoDTO.getProjectName());
        // TODO: Implementar lógica detalhada de cálculo de materiais
        CalculationResponseDTO response = new CalculationResponseDTO();
        // Simular alguns dados para teste
        // response.setCalculationExternal(List.of(new WallCalculationDTO(UUID.randomUUID(), "ExtWall-01", ...)));
        log.warn("Material calculation logic is a STUB for project: {}", projectInfoDTO.getProjectName());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDetailResponseDTO getProjectDetailById(UUID id) {
        log.debug("Service: Fetching project details by ID: {}", id);
        return projectRepository.findById(id)
                .map(projectConverter::entityToDetailResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDetailResponseDTO> getAllProjects() {
        log.debug("Service: Fetching all projects");
        return projectRepository.findAll().stream()
                .map(projectConverter::entityToDetailResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDetailResponseDTO updateProject(UUID id, ProjectInfoDTO projectInfoDTO) {
        log.info("Service: Updating project with ID: {}", id);
        ProjectEntity existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id + " for update."));

        // Atualizar campos simples
        existingProject.setProjectName(projectInfoDTO.getProjectName());
        if (projectInfoDTO.getDateTime() != null) {
             try {
                 existingProject.setDateTime(LocalDateTime.parse(projectInfoDTO.getDateTime()));
             } catch (DateTimeParseException e) {
                 log.warn("Failed to parse dateTime '{}' during project update '{}'. Keeping existing value.", projectInfoDTO.getDateTime(), id);
             }
        }
        // ... (outros campos como no create) ...
        existingProject.setBuildingType(projectInfoDTO.getBuildingType());
        existingProject.setNumberOfFloors(projectInfoDTO.getNumberOfFloors());
        existingProject.setHasBasement(projectInfoDTO.isHasBasement());
        if (projectInfoDTO.getStatus() != null) existingProject.setStatus(projectInfoDTO.getStatus());
        existingProject.setBudgetAmount(projectInfoDTO.getBudgetAmount());
        existingProject.setCurrency(projectInfoDTO.getCurrency());
        existingProject.setStartDatePlanned(projectInfoDTO.getStartDatePlanned());
        existingProject.setEndDatePlanned(projectInfoDTO.getEndDatePlanned());
        existingProject.setClientId(projectInfoDTO.getClientId());
        existingProject.setCompanyBranchId(projectInfoDTO.getCompanyBranchId());


        if (projectInfoDTO.getLocation() != null) {
            LocationConverter locationConverter = new LocationConverter();

            LocationEntity locFromDto = locationConverter.requestDtoToEntity(projectInfoDTO.getLocation());
            if (existingProject.getLocationEntity() != null && locFromDto != null) {
                // Atualizar campos da localização existente
                LocationEntity existingLoc = existingProject.getLocationEntity();
                existingLoc.setAddress(locFromDto.getAddress());
                existingLoc.setCity(locFromDto.getCity());
                existingLoc.setProvince(locFromDto.getProvince());
                existingLoc.setPostalCode(locFromDto.getPostalCode());
                // LocationRepository.save(existingLoc) é gerenciado pelo cascade do Project ou pela transação
            } else if (locFromDto != null) { // Nova localização
                existingProject.setLocationEntity(locationRepository.save(locFromDto));
            } else { // Remover localização
                if(existingProject.getLocationEntity() != null) {
                    LocationEntity locToRemove = existingProject.getLocationEntity();
                    existingProject.setLocationEntity(null);
                    locationRepository.delete(locToRemove); // Se não houver cascade orphanRemoval
                }
            }
        }
        // TODO: Lógica de atualização de Floors/Walls (complexa, omitida para brevidade)
        log.warn("Detailed update of floors and walls in ProjectService is not fully implemented.");

        ProjectEntity updatedProject = projectRepository.save(existingProject);
        log.info("Project with ID {} updated successfully.", id);
        return projectConverter.entityToDetailResponseDto(updatedProject);
    }

    @Override
    public void deleteProject(UUID id) {
        log.info("Service: Deleting project with ID: {}", id);
        ProjectEntity projectToDelete = projectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id + " for deletion."));
        
        // A deleção em cascata de Floors, Rooms, Walls, WallRoomMappings etc. deve ser
        // configurada nas entidades com CascadeType.ALL e orphanRemoval=true.
        projectRepository.delete(projectToDelete);
        log.info("Project with ID {} deleted successfully.", id);
    }
}