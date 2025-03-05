package com.palaspadel.sb_palaspadel.controllers;

import com.palaspadel.sb_palaspadel.entities.Pal;
import com.palaspadel.sb_palaspadel.services.PalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/palas")
public class PalControler {

    @Autowired
    private PalService palService;

    @GetMapping
    public List<Pal> listarPalas() {
        return palService.findAll();
    }

    @PostMapping
    public Pal agregarPala(@RequestBody Pal pala) {
        return palService.agregarPala(pala);
    }

}
