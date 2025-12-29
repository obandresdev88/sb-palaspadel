package com.palaspadel.sb_palaspadel.controllers;

import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.repositories.UsuRepository;
import com.palaspadel.sb_palaspadel.services.UsuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: Clase controladora de la entidad Usu.
 * Se encarga de recibir las peticiones a través de mapping y llamar a la capa de negocio (servicio) correspondiente
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

    /**
     * Metodo para listar todos los usuarios llamando al servicio o capa de negocio (impl)
     * @return
     */
    @GetMapping
    public List<Usu> listarUsuarios() {
        return usuService.findAll();
    }


}
