package com.bufalari.building.DTO;

import lombok.Data;

@Data
public class DoorDimensionsDTO {

    //@NotNull(message = "Width cannot be null")
    //@DecimalMin(value = "0.0", inclusive = false, message = "Width must be greater than zero")
    private Double doorWidthFoot;
    private String doorWidthInches;

    //@NotNull(message = "Height cannot be null")
    //@DecimalMin(value = "0.0", inclusive = false, message = "Height must be greater than zero")
    private Double doorHeightFoot;
    private String doorHeightInches;

    //@NotNull(message = "Height cannot be null")
    //@DecimalMin(value = "0.0", inclusive = false, message = "Height must be greater than zero")
    private Double doorThickness;
    
}