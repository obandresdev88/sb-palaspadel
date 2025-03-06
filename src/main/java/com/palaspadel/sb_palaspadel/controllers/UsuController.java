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

    /**
     * Metodo para crear un usuario llamando al servicio o capa de negocio (impl)
     *
     * @param usu
     * @return
     */
    @CrossOrigin(origins = "http://127.0.0.1:5500") // Para permitir peticiones desde cualquier origen
    @PostMapping("/registro")
    public ResponseEntity<Usu> crearUsuario( @RequestBody Usu usu) { // Se pone body porque no vendrá por path variable, se rellena en el formulario
        Usu usuSaved  = usuService.save(usu);
        if (usuSaved == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuSaved);
    }
}
