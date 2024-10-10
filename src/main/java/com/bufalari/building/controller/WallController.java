package com.bufalari.building.controller;

import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.service.WallCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/calculate")
    public ResponseEntity<WallEntity> calculateWall(@RequestBody WallEntity wall) {
        WallEntity calculatedWall = wallCalculationService.performWallCalculations(wall);
        return ResponseEntity.ok(calculatedWall);
    }
}
