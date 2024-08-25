package com.bufalari.building.DTO;

import lombok.Data;

@Data
public class WindowDimensionsDTO {

    //@NotNull(message = "Width cannot be null")
    //@DecimalMin(value = "0.0", inclusive = false, message = "Width must be greater than zero")
    private Double windowsWidth;

    //@NotNull(message = "Height cannot be null")
    // @DecimalMin(value = "0.0", inclusive = false, message = "Height must be greater than zero")
    private Double windowsHeight;

    //@NotNull(message = "Height cannot be null")
    // @DecimalMin(value = "0.0", inclusive = false, message = "Height must be greater than zero")
    private Double windowsThickness;

}
