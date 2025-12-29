package com.palaspadel.sb_palaspadel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AutenticacionResponseDto {
    private final String token;
    private final String usuema;
    private final Integer usuid;
}
