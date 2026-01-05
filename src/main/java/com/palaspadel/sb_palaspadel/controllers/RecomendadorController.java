package com.palaspadel.sb_palaspadel.controllers;

import com.palaspadel.sb_palaspadel.dto.PalaResponseDto;
import com.palaspadel.sb_palaspadel.dto.RecomendadorDto;
import com.palaspadel.sb_palaspadel.services.RecomendadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: Controlador REST para el recomendador de palas
 * <p>
 * Created by Andres on 2026
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api/recomendador")
public class RecomendadorController {
    @Autowired
    private RecomendadorService recomendadorService;

    @PostMapping
    public ResponseEntity<List<PalaResponseDto>> obtenerRecomendaciones(
            @RequestBody RecomendadorDto request) {

            List<PalaResponseDto> recomendaciones = recomendadorService.recomendar(request);
            return ResponseEntity.ok(recomendaciones);

    }
}
