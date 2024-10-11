package com.bufalari.building.controller;


import com.bufalari.building.DTO.WallDTO;
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
@RequestMapping("/walls")
public class WallController {

    @Autowired
    private WallCalculationService wallCalculationService;

    @Autowired
    private ProjectConverter projectConverter;

    @PostMapping("/calculate")
    public ResponseEntity<WallEntity> calculateWall(@RequestBody WallDTO wallDTO) {

        WallEntity wallEntity = projectConverter.toEntity(wallDTO); // Converter o DTO para a entidade

        WallEntity calculatedWall = wallCalculationService.calculateWall(wallEntity); // Calcular as medidas
        return ResponseEntity.status(HttpStatus.CREATED).body(calculatedWall);
    }
}
