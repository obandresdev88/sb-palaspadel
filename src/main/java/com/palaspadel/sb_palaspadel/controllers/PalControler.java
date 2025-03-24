package com.palaspadel.sb_palaspadel.controllers;

import com.palaspadel.sb_palaspadel.entities.Pal;
import com.palaspadel.sb_palaspadel.services.PalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/importar")
    public ResponseEntity<String> importarPalas(@RequestParam("archivoExcel") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor, sube un archivo válido.");
        }

        try {
            palService.importarPalasDesdeExcel(file);
            return ResponseEntity.ok("Número de palas importadas: " + palService.palasImportadas.size());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al importar el archivo: " + e.getMessage());
        }
    }
}
