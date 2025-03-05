package com.palaspadel.sb_palaspadel.controllers;

import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.services.UsuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/usuarios")
public class UsuController {

    @Autowired
    private UsuService usuService;

    @GetMapping
    public List<Usu> listarUsuarios() {
        return usuService.findAll();
    }
}
