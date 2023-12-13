package org.alexkolo.rest.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertOrderRequest {

    @NotBlank(message = "Name of product can't be empty!")
    @Size(min = 5, max = 50, message = "Name of product can't be less then {min} grater then {max}!")
    private String product;

    @NotNull(message = "Put cost of product!")
    @Positive(message = "Cost of product mast be positive!")
    private BigDecimal cost;

    @NotNull(message = "Put client ID!")
    @Positive(message = "Client ID mast be positive!")
    private Long clientId;

}
