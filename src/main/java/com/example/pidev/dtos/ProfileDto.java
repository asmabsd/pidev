package com.example.pidev.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ProfileDto {
    @NotBlank
    @Pattern(regexp = "^[0-9]{8}$")
    private String phone;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]{9}$")
    private String passport;
}