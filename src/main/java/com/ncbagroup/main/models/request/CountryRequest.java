package com.ncbagroup.main.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CountryRequest {

    @NotBlank(message = "Country name must not be blank")
    @Size(min = 2, message = "Country name must be more than 2 letters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Country name must contain only letters and spaces")
    private String name;
}