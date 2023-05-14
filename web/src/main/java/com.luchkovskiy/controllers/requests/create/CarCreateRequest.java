package com.luchkovskiy.controllers.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@Schema(description = "Car information to save in database")
public class CarCreateRequest {

    @NotNull
    @Size(min = 2, max = 30)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Audi", type = "string", description = "Brand of the car")
    private String brand;

    @NotNull
    @Size(min = 2, max = 30)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "RS7", type = "string", description = "Model of the car")
    private String model;

    @NotNull
    @Max(220)
    @Min(150)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "210.5", type = "number", description = "Maximum car's speed")
    private Float maxSpeed;

    @NotNull
    @Size(min = 3, max = 20)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Blue", type = "string", description = "Color of the car")
    private String color;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2018", type = "integer", description = "Year when the car was released")
    @Min(2010)
    @Max(2023)
    private Integer releaseYear;

    @NotNull
    @Size(min = 3, max = 30)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Mechanical", type = "string", description = "Gearbox type of the car")
    private String gearboxType;

    @NotNull
    @Min(2)
    @Max(7)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "5", type = "integer", description = "Amount of sits in the car")
    private Integer sitsAmount;

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "Id of the class level in database")
    private Long classId;

    @NotNull
    @Min(3)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "7.5", type = "number", description = "Fuel consumption per 100 km in average")
    private Float gasConsumption;

    @Pattern(regexp = "^[1-9]{4} [АВЕКМНОРСТУХABEIKMHOPCTYX]{2}-[1-7]")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1234AX-3", type = "string", description = "License plate number of the car")
    private String licensePlateNumber;

}
