package com.palaspadel.sb_palaspadel.dto;

import com.palaspadel.sb_palaspadel.entities.Pal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Description: DTO para actualizar una pala existente
 * <p>
 * Created by Andres on 2026
 *
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PalaActualizarDto {
    @NotBlank (message = "Marca es obligatoria")
    @Size(max = 50)
    private String marca;

    @NotBlank (message = "Modelo es obligatorio")
    @Size(max = 75)
    private String modelo;

    @NotNull
    private Integer peso;

    @NotNull
    private Pal.FormaPala forma;

    @NotNull
    private Pal.DurezaPala dureza;

    @NotNull
    private Pal.BalancePala balance;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal precio;

    private String urlCompra;


//    Imagen de la pala lo harmemos mediante el endpoint de subida de imagenes
//    private String imagen;

//    @NotNull
//    private Boolean activo;
}
