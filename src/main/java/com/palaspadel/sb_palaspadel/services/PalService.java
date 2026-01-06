package com.palaspadel.sb_palaspadel.services;

import com.palaspadel.sb_palaspadel.dto.PalaActualizarDto;
import com.palaspadel.sb_palaspadel.dto.PalaCrearDto;
import com.palaspadel.sb_palaspadel.dto.PalaResponseDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Description: Interfaz para la lógica de negocio de palas
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
public interface PalService {

    List<PalaResponseDto> listarPalasActivas();

    PalaResponseDto obtenerPalaPorId(Integer id);

    @Transactional
    PalaResponseDto crearPala(PalaCrearDto dto, MultipartFile imagen);

    @Transactional
    PalaResponseDto actualizarPala(Integer id, PalaActualizarDto dto, MultipartFile imagen);

    @Transactional
    void eliminarPala(Integer id);

    @Transactional
    int importarPalasDesdeExcel(MultipartFile file) throws Exception;
}