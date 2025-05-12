package com.bufalari.building.converts;

import com.bufalari.building.entity.FloorEntity;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.entity.RoomEntity;
import com.bufalari.building.requestDTO.RoomDTO;
// import com.bufalari.building.requestDTO.WallDTO; // Se for mapear paredes no DTO de Room
// import org.springframework.beans.factory.annotation.Autowired; // Se injetar WallConverter
import org.springframework.stereotype.Component;
// import java.util.stream.Collectors; // Se for mapear paredes

@Component
public class RoomConverter {

    // Se for mapear paredes de RoomEntity para List<WallDTO> em RoomDTO:
    // private final WallConverter wallConverter;
    //
    // @Autowired
    // public RoomConverter(WallConverter wallConverter) {
    //     this.wallConverter = wallConverter;
    // }
    // public RoomConverter() {} // Construtor padrão se WallConverter não for injetado


    public RoomEntity toEntity(RoomDTO dto, FloorEntity floor, ProjectEntity project) {
        if (dto == null) {
            return null;
        }
        RoomEntity.RoomEntityBuilder builder = RoomEntity.builder()
                .id(dto.getId()) // Mantém ID para atualizações, será nulo para criações
                .roomType(dto.getRoomType())
                .isWetArea(dto.isWetArea());

        if (floor != null) {
            builder.floor(floor);
        } else if (dto.getFloorId() != null) {
            // Lançar exceção ou logar se floorId for fornecido mas a entidade floor não
            // throw new IllegalArgumentException("Floor entity must be provided if floorId is present in DTO");
        }

        if (project != null) {
            builder.project(project);
        } else if (dto.getProjectId() != null) {
            // throw new IllegalArgumentException("Project entity must be provided if projectId is present in DTO");
        }
        // Mapear paredes do DTO para a entidade aqui geralmente não é feito no conversor para criação,
        // pois as WallEntities precisariam ser buscadas ou criadas primeiro.
        // A associação de paredes é melhor feita no serviço.
        return builder.build();
    }

    public RoomDTO toDto(RoomEntity entity) {
        if (entity == null) {
            return null;
        }
        RoomDTO.RoomDTOBuilder builder = RoomDTO.builder()
                .id(entity.getId())
                .roomType(entity.getRoomType())
                .isWetArea(entity.isWetArea());

        if (entity.getFloor() != null) {
            builder.floorId(entity.getFloor().getId());
            builder.floorNumberInfo(entity.getFloor().getFloorNumber());
        }
        if (entity.getProject() != null) {
            builder.projectId(entity.getProject().getId());
            builder.projectName(entity.getProject().getProjectName());
        }

        // Exemplo de como mapear paredes, se o DTO de Room contiver uma lista de WallDTOs
        // e se WallConverter e WallDTO estiverem definidos.
        // if (entity.getWallMappings() != null && wallConverter != null) {
        //    builder.walls(entity.getWallMappings().stream()
        //            .map(mapping -> wallConverter.toDto(mapping.getWall())) // Assume que wallConverter.toDto existe
        //            .collect(Collectors.toList()));
        // }
        return builder.build();
    }
}