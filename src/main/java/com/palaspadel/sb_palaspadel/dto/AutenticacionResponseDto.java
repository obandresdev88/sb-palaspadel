package com.palaspadel.sb_palaspadel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AutenticacionResponseDto {
    private final String token;
    private final String usuema;
    private final String usunom;
    private final Integer usuid;
    private List<String> roles;
}
