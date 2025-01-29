package ru.practicum.event.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    @NotNull
    @DecimalMin(value = "-90.0", message = "Широта должна быть в диапазоне -90 и 90.")
    @DecimalMax(value = "90.0", message = "Широта должна быть в диапазоне -90 и 90.")
    private Float lat;

    @NotNull
    @DecimalMin(value = "-180.0", message = "Долгота должна быть в диапазоне -180 и 180.")
    @DecimalMax(value = "180.0", message = "Долгота должна быть в диапазоне -180 и 180.")
    private Float lon;
}
