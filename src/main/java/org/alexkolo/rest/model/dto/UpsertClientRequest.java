package org.alexkolo.rest.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertClientRequest {

    @NotBlank(message = "Name can't be empty!")
    @Size(min = 3, max = 30, message = "Name can't be less then {min} grater then {max}!")
    private String name;

}
