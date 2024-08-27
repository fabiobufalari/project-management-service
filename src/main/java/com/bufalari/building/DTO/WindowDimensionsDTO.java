package com.bufalari.building.DTO;

import lombok.Data;

@Data
public class WindowDimensionsDTO {

    //@NotNull(message = "Width cannot be null")
    //@DecimalMin(value = "0.0", inclusive = false, message = "Width must be greater than zero")
    private Double windowsWidthFoot;
    private Double windowsWidthInches;

    //@NotNull(message = "Height cannot be null")
    // @DecimalMin(value = "0.0", inclusive = false, message = "Height must be greater than zero")
    private Double windowsHeightFoot;
    private Double windowsHeightInches;

    //@NotNull(message = "Height cannot be null")
    // @DecimalMin(value = "0.0", inclusive = false, message = "Height must be greater than zero")
    private Double windowsThickness;

}
