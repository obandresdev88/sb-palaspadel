package com.palaspadel.sb_palaspadel.services;

import com.palaspadel.sb_palaspadel.dto.AutenticacionResponseDto;
import com.palaspadel.sb_palaspadel.dto.RegistroRequestDto;
import com.palaspadel.sb_palaspadel.entities.Usu;

import java.util.List;
import java.util.Optional;

public interface UsuService {
    AutenticacionResponseDto registrar(RegistroRequestDto request);
    List<Usu> findAll();


    Optional<Usu> obtenerUsuarioPorEmail(String usuema);
}
