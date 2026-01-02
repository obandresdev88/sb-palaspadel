// java
package com.palaspadel.sb_palaspadel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String usunom;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String usuema;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String usupas;

    @NotBlank(message = "El nivel es obligatorio")
    private String usuniv;

    private boolean permaneceLogged;
}
