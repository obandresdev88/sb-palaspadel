package com.palaspadel.sb_palaspadel.services.impl;

import com.palaspadel.sb_palaspadel.dto.RecomendadorDto;
import com.palaspadel.sb_palaspadel.dto.PalaResponseDto;
import com.palaspadel.sb_palaspadel.entities.Pal;
import com.palaspadel.sb_palaspadel.repositories.PalRepository;
import com.palaspadel.sb_palaspadel.services.RecomendadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecomendadorServiceImpl implements RecomendadorService {

    @Autowired
    private PalRepository palRepository;

    /**
     * Logica de negocio para recomendar palas según las respuestas del usuario
     * @param request
     * @return
     */
    @Override
    public List<PalaResponseDto> recomendar(RecomendadorDto request) {
        // Obtener palas activas
        List<Pal> palas = palRepository.findByPalactTrue();

        // Aplicar filtros
        palas = filtrarPorPresupuesto(palas, request.getPresupuesto());
        palas = filtrarPorForma(palas, request.getFormaPreferida());

        // Calcular scoring, creamos un mapa de pala y su puntuación
        Map<Pal, Integer> scoring = new HashMap<>();
        for (Pal pal : palas) {
            int score = calcularScore(pal, request);
            if (score > 0) {
                scoring.put(pal, score);
            }
        }

        // Ordenar por score y limitar a 3
        return scoring.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(3)
                .map(entry -> mapToResponseDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Logica de negocio para calcular el score de una pala según las respuestas del usuario
     *
     * @param pal
     * @param request
     * @return
     */
    private int calcularScore(Pal pal, RecomendadorDto request) {
        int score = 0;

        // Scoring por nivel
        if ("PRINCIPIANTE".equals(request.getNivel())) {
            if (pal.getPalpes() <= 360) score += 20;
            if (pal.getPalpes() >= 370) score -= 20; //restamos puntos si el peso es alto para principiantes
            if (pal.getPaldur() == Pal.DurezaPala.BLANDA) score += 15;
        } else if ("INTERMEDIO".equals(request.getNivel())) {
            if (pal.getPalpes() >= 360 && pal.getPalpes() < 375) score += 20;
            if (pal.getPaldur() == Pal.DurezaPala.MEDIA) score += 15;
        } else if ("AVANZADO".equals(request.getNivel())) {
            if (pal.getPalpes() >= 360 && pal.getPalpes() <= 385) score += 20;
            if (pal.getPaldur() == Pal.DurezaPala.DURA || pal.getPaldur() == Pal.DurezaPala.MEDIA_DURA) score += 15;
        }

        // Scoring por estilo
        if ("DEFENSIVO".equals(request.getEstilo())) {
            if (pal.getPalfor() == Pal.FormaPala.REDONDA) score += 15;
            if (pal.getPalbal() == Pal.BalancePala.BAJO) score += 10;
        } else if ("EQUILIBRADO".equals(request.getEstilo())) {
            if (pal.getPalbal() == Pal.BalancePala.MEDIO) score += 15;
            if (pal.getPalfor() == Pal.FormaPala.LAGRIMA) score += 10;
        } else if ("OFENSIVO".equals(request.getEstilo())) {
            if (pal.getPalfor() == Pal.FormaPala.LAGRIMA || pal.getPalfor() == Pal.FormaPala.DIAMANTE) score += 10;
            if (pal.getPalbal() == Pal.BalancePala.ALTO) score += 15;
        }

        // Scoring por lesiones (tronco superior) -> preferencia por palas blandas y con balance bajo
        if (request.isLesionesPadel()) {
            if (pal.getPaldur() == Pal.DurezaPala.BLANDA) score += 20;
            if (pal.getPalbal() == Pal.BalancePala.BAJO) score += 15;
        }

        return score;
    }

    private List<Pal> filtrarPorPresupuesto(List<Pal> palas, Double presupuesto) {
        if (presupuesto == null || presupuesto <= 0) {
            return palas;
        }
        return palas.stream()
                .filter(p -> p.getPalpre().doubleValue() <= presupuesto)
                .collect(Collectors.toList());
    }

    private List<Pal> filtrarPorForma(List<Pal> palas, String forma) {
        if (forma == null || forma.isEmpty()) {
            return palas;
        }
        try {
            Pal.FormaPala formaPala = Pal.FormaPala.valueOf(forma.toUpperCase());
            return palas.stream()
                    .filter(p -> formaPala == p.getPalfor())
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return palas;
        }
    }

    private PalaResponseDto mapToResponseDto(Pal pal, Integer score) {
        PalaResponseDto dto = new PalaResponseDto();
        dto.setId(pal.getId());
        dto.setMarca(pal.getPalmar());
        dto.setModelo(pal.getPalmod());
        dto.setPeso(pal.getPalpes());
        dto.setForma(pal.getPalfor());
        dto.setDureza(pal.getPaldur());
        dto.setBalance(pal.getPalbal());
        dto.setPrecio(pal.getPalpre());
        dto.setUrlCompra(pal.getPalurl());
        // Construir URL pública de la imagen
        String img = pal.getPalimg();
        if (img == null || img.trim().isEmpty()) {
            dto.setImagen("/images/default.webp");
        } else if (img.startsWith("http") || img.startsWith("/images/")) {
            dto.setImagen(img);
        } else {
            dto.setImagen("/images/" + img);
        }
        dto.setActivo(pal.getPalact());
        dto.setPuntuacion(score);
        return dto;
    }
}
