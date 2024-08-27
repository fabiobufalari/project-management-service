package com.bufalari.building.controller;

import com.bufalari.building.DTO.WallDimensionsDTO;
import com.bufalari.building.convertTo.ConvertWallDimensionsTo;
import com.bufalari.building.model.WallMeasurementEntity;
import com.bufalari.building.service.StudsMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/walls")
public class WallMeasurementController {

    @Autowired
    private StudsMeasurementService service;

    @Autowired
    private ConvertWallDimensionsTo convertWallDimensionsTo;

    @PostMapping
    public ResponseEntity<WallDimensionsDTO> createMeasurement(@RequestBody WallDimensionsDTO wallDimensionsDTO) {

        // Convert Wall Dimensions DTO to Wall Measurement Entity
        WallMeasurementEntity entity = convertWallDimensionsTo.convertToEntity(wallDimensionsDTO);

        // Saves the entity and calculates the area
        WallMeasurementEntity savedEntity = service.calculateAndSaveMeasurement(entity);

        // Convert Wall Measurement Entity back to Wall Dimensions DTO
        WallDimensionsDTO resultDTO = convertWallDimensionsTo.convertToDTO(savedEntity);

        return new ResponseEntity<>(resultDTO, HttpStatus.CREATED);
    }
}