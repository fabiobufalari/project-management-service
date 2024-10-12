package com.bufalari.building.controller;

import com.bufalari.building.converts.ProjectConverter;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.repository.WallRepository;
import com.bufalari.building.requestDTO.WallDTO;
import com.bufalari.building.service.WallCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/walls")
public class WallController {

    @Autowired
    private WallCalculationService wallCalculationService;

    @Autowired
    private ProjectConverter projectConverter;

    @Autowired
    private WallRepository wallRepository;


    @PostMapping("/calculate")
    public ResponseEntity<List<WallEntity>> calculateWall(@RequestBody List<WallDTO> wallDTOs) {
        List<WallEntity> calculatedWalls = wallDTOs.stream()
                .map(wallDTO -> {
                    int floorNumber = wallDTO.getFloorNumber();
                    WallEntity wallEntity = projectConverter.convertWall(wallDTO, floorNumber);
                    return wallCalculationService.calculateWall(wallEntity);
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(calculatedWalls);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<WallEntity> getWallByUuid(@PathVariable UUID uuid) {
        Optional<WallEntity> wallEntity = wallRepository.findByUuid(uuid);

        if (wallEntity.isPresent()) {
            return ResponseEntity.ok(wallEntity.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}