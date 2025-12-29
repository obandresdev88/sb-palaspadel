package com.palaspadel.sb_palaspadel.controllers;

import com.palaspadel.sb_palaspadel.dto.AutenticacionResponseDto;
import com.palaspadel.sb_palaspadel.dto.RegistroRequestDto;
import com.palaspadel.sb_palaspadel.services.UsuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionController {

    private final UsuService usuService;

    @PostMapping("/registro")
    public ResponseEntity<AutenticacionResponseDto> registrar(@RequestBody RegistroRequestDto request) {
        AutenticacionResponseDto response = usuService.registrar(request);
        return ResponseEntity.ok(response);
    }
}
