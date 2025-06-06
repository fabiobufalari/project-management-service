package com.bufalari.building.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID; // Importar

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    // Se Location for uma entidade separada que pode ser referenciada/atualizada por ID
    @Schema(description = "Unique identifier (UUID) of the location (if pre-existing or for response)", nullable = true)
    private UUID locationId;

    @NotBlank(message = "{location.address.required}")
    @Size(max = 255)
    @Schema(description = "Street address", example = "123 Main Street", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

    @NotBlank(message = "{location.city.required}")
    @Size(max = 100)
    @Schema(description = "City", example = "Anytown", requiredMode = Schema.RequiredMode.REQUIRED)
    private String city;

    @NotBlank(message = "{location.province.required}")
    @Size(max = 100)
    @Schema(description = "State or Province", example = "CA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String province;

    @NotBlank(message = "{location.postalCode.required}")
    @Size(max = 20)
    @Schema(description = "Postal Code or ZIP Code", example = "90210", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postalCode;

    // Country pode ser adicionado se necessário
    // @NotBlank
    // @Schema(description="Country", example="USA")
    // private String country;
}