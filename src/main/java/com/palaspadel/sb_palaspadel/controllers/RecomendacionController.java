package com.palaspadel.sb_palaspadel.controllers;

import com.palaspadel.sb_palaspadel.entities.Pal;
import com.palaspadel.sb_palaspadel.services.RecomendacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/recomendacion")
public class RecomendacionController {

    @Autowired
    private RecomendacionService recomendacionService;

    // Obtener palas recomendadas según el estilo del jugador
    @GetMapping
    public List<Pal> recomendarPalas(@RequestParam String balance,
                                     @RequestParam String forma,
                                     @RequestParam String dureza,
                                     @RequestParam BigDecimal precio) {
        return recomendacionService.recomendarPalasPorEstilo(balance, forma, dureza, precio);
    }
}
