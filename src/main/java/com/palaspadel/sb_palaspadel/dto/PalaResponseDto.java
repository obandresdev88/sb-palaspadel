package com.palaspadel.sb_palaspadel.dto;

import com.palaspadel.sb_palaspadel.entities.Pal;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Description: DTO para transferir datos de palas
 * <p>
 * Created by Andres on 2026
 *
 * @version 1.0
 */
@Getter
@Setter
public class PalaResponseDto {
    private Integer id;
    private String marca;
    private String modelo;
    private Integer peso;
    private Pal.FormaPala forma;
    private Pal.DurezaPala dureza;
    private Pal.BalancePala balance;
    private BigDecimal precio;
    private String urlCompra;
    private String imagen;
    private Boolean activo;
//    private Instant createdAt;
}
