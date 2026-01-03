package com.palaspadel.sb_palaspadel.controllers;

import com.palaspadel.sb_palaspadel.dto.PalaCrearDto;
import com.palaspadel.sb_palaspadel.dto.PalaActualizarDto;
import com.palaspadel.sb_palaspadel.dto.PalaResponseDto;
import com.palaspadel.sb_palaspadel.services.PalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Description: Controlador REST para gestionar palas de pádel
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api/palas")
public class PalControler {

    @Autowired
    private PalService palService;

    // ====================== ENDPOINTS PÚBLICOS ======================

    /**
     * Listar todas las palas activas
     */
    @GetMapping
    public ResponseEntity<List<PalaResponseDto>> listarPalas() {
        List<PalaResponseDto> palas = palService.listarPalasActivas();
        return ResponseEntity.ok(palas);
    }

    /**
     * Obtener una pala por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PalaResponseDto> obtenerPalaPorId(@PathVariable Integer id) {
        PalaResponseDto pala = palService.obtenerPalaPorId(id);
        return ResponseEntity.ok(pala);
    }

    // ====================== ENDPOINTS PROTEGIDOS - REQUIEREN ROLE ADMIN ======================

    /**
     * Crear una nueva pala con imagen opcional
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<PalaResponseDto> crearPala(
            @Valid @ModelAttribute PalaCrearDto dto,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        PalaResponseDto palaCreada = palService.crearPala(dto, imagen);
        return ResponseEntity.status(HttpStatus.CREATED).body(palaCreada);
    }

    /**
     * Actualizar una pala existente con imagen opcional
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<PalaResponseDto> actualizarPala(
            @PathVariable Integer id,
            @Valid @ModelAttribute PalaActualizarDto dto,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        PalaResponseDto palaActualizada = palService.actualizarPala(id, dto, imagen);
        return ResponseEntity.ok(palaActualizada);
    }

    /**
     * Eliminar una pala (eliminación lógica)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPala(@PathVariable Integer id) {
        palService.eliminarPala(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Importar palas desde Excel
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/importar-excel")
    public ResponseEntity<String> importarPalas(@RequestParam("archivo") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor, sube un archivo válido.");
        }
        try {
            int palasImportadas = palService.importarPalasDesdeExcel(file);
            return ResponseEntity.ok("Número de palas importadas: " + palasImportadas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al importar el archivo: " + e.getMessage());
        }
    }
}
