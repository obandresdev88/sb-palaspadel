package com.palaspadel.sb_palaspadel.dto;

import lombok.Data;

@Data
public class RegistroRequestDto {
    private String usunom;
    private String usuema;
    private String usupas;
    private String usuniv; // enviar nombre del enum: PRINCIPIANTE, INTERMEDIO, ...
    private boolean permaneceLogged;
}
