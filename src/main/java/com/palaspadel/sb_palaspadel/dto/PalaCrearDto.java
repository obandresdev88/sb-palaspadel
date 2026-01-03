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
    @NotBlank (message = "Marca es obligatoria y no puede estar vacía")
    @Size(max = 50)
    private String marca;

    @NotBlank (message = "Modelo es obligatorio y no puede estar vacío")
    @Size(max = 75)
    private String modelo;

    @NotNull (message = "Peso es obligatorio")
    private Integer peso;

    @NotNull (message = "Forma es obligatoria")
    private Pal.FormaPala forma;
    @NotNull (message = "Dureza es obligatoria")
    private Pal.DurezaPala dureza;
    @NotNull(message = "Balance es obligatorio")
    private Pal.BalancePala balance;

    @NotNull(message = "Precio es obligatorio")
    @DecimalMin("0.0")
    private BigDecimal precio;

    @NotNull (message = "URL de compra es obligatoria")
    private String urlCompra;


//     Imagen de la pala lo harmemos mediante el endpoint de subida de imagenes
//    private String imagen;
}
