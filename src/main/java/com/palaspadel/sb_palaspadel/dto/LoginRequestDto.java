package com.palaspadel.sb_palaspadel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String usuema;          // email
    private String usupas;          // password en texto plano
    private boolean permaneceLogged; // mismo criterio que en registro
}