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
 * Description: DTO para crear una nueva pala
 * <p>
 * Created by Andres on 2026
 *
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PalaCrearDto {
    @NotBlank (message = "Marca es obligatoria")
    @Size(max = 50)
    private String marca;

    @NotBlank (message = "Modelo es obligatorio")
    @Size(max = 75)
    private String modelo;

    private Integer peso;
    private Pal.FormaPala forma;
    private Pal.DurezaPala dureza;
    private Pal.BalancePala balance;

    @NotNull(message = "Precio es obligatorio")
    @DecimalMin("0.0")
    private BigDecimal precio;

    private String urlCompra;


//     Imagen de la pala lo harmemos mediante el endpoint de subida de imagenes
//    private String imagen;
}
