package com.bufalari.building.controller;


import com.bufalari.building.requestDTO.WallDTO;
import com.bufalari.building.converts.ProjectConverter;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.service.WallCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/walls")
public class WallController {

    @Autowired
    private WallCalculationService wallCalculationService;

    @Autowired
    private ProjectConverter projectConverter;

    @PostMapping("/calculate")
    public ResponseEntity<WallEntity> calculateWall(@RequestBody WallDTO wallDTO) {
        // Obter o floorNumber do WallDTO (você precisa adicionar esse campo ao DTO)
        int floorNumber = wallDTO.getFloorNumber(); // Supondo que você tenha adicionado o campo floorNumber ao WallDTO

        // Converter o DTO para entidade, passando o floorNumber
        WallEntity wallEntity = projectConverter.convertWall(wallDTO, floorNumber);

        // Calcular as medidas da parede
        WallEntity calculatedWall = wallCalculationService.calculateWall(wallEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(calculatedWall);
    }
}
