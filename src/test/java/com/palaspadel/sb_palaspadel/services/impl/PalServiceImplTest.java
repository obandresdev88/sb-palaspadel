package com.palaspadel.sb_palaspadel.services.impl;

import com.palaspadel.sb_palaspadel.dto.PalaActualizarDto;
import com.palaspadel.sb_palaspadel.dto.PalaCrearDto;
import com.palaspadel.sb_palaspadel.entities.Pal;
import com.palaspadel.sb_palaspadel.repositories.PalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PalServiceImplTest {

    @Mock
    private PalRepository palRepository;

    @InjectMocks
    private PalServiceImpl palService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // inyectar ruta temporal al campo uploadDir
        palService.getClass()
                .getDeclaredFields();
        try {
            var f = palService.getClass().getDeclaredField("uploadDir");
            f.setAccessible(true);
            f.set(palService, tempDir.toString());
        } catch (Exception ignored) {}
    }

    @Test
    void listarPalasActivas_ok() {
        Pal p = new Pal();
        p.setId(1);
        p.setPalmar("Babolat");
        p.setPalmod("Modelo");
        p.setPalact(true);
        when(palRepository.findPalByPalactIs(true)).thenReturn(List.of(p));

        var res = palService.listarPalasActivas();

        assertThat(res).hasSize(1);
        assertThat(res.get(0).getMarca()).isEqualTo("Babolat");
        verify(palRepository).findPalByPalactIs(true);
    }

//    @Test
//    void crearPala_ok_conImagen() {
//        PalaCrearDto dto = new PalaCrearDto("Babolat","Babolat",360, Pal.FormaPala.REDONDA,
//                Pal.DurezaPala.MEDIA, Pal.BalancePala.MEDIO, BigDecimal.TEN, "http://url");
//        Pal saved = new Pal();
//        saved.setId(10);
//        saved.setPalmar("Babolat");
//        saved.setPalmod("Viper");
//        when(palRepository.findByPalmarAndPalmod("Babolat","Viper")).thenReturn(Optional.empty());
//        when(palRepository.save(any(Pal.class))).thenReturn(saved);
//
//        MockMultipartFile img = new MockMultipartFile("file","foto.jpg","image/jpeg","img".getBytes());
//
//        var res = palService.crearPala(dto, img);
//
//        assertThat(res.getId()).isEqualTo(10);
//        verify(palRepository, times(2)).save(any(Pal.class)); // guarda sin imagen y luego con nombre
//    }
//
//    @Test
//    void crearPala_duplicada_lanzaExcepcion() {
//        PalaCrearDto dto = new PalaCrearDto("Babolat","Viper",360, Pal.FormaPala.REDONDA,
//                Pal.DurezaPala.MEDIA, Pal.BalancePala.MEDIO, BigDecimal.TEN, "http://url");
//        when(palRepository.findByPalmarAndPalmod("Babolat","Viper"))
//                .thenReturn(Optional.of(new Pal()));
//
//        assertThrows(RuntimeException.class, () -> palService.crearPala(dto, null));
//        verify(palRepository, never()).save(any());
//    }

    @Test
    void actualizarPala_ok_actualizaImagen() {
        Pal existente = new Pal();
        existente.setId(5);
        existente.setPalmar("Babolat");
        existente.setPalmod("Viper");
        existente.setPalact(true);
        when(palRepository.findById(5)).thenReturn(Optional.of(existente));
        when(palRepository.findByPalmarAndPalmod("Babolat", "Viper")).thenReturn(Optional.of(existente));
        when(palRepository.save(any(Pal.class))).thenAnswer(inv -> inv.getArgument(0));

        PalaActualizarDto dto = new PalaActualizarDto("Babolat", "Viper", 360, Pal.FormaPala.REDONDA,
                Pal.DurezaPala.MEDIA, Pal.BalancePala.MEDIO, BigDecimal.TEN, "http://url");
        MockMultipartFile img = new MockMultipartFile("file", "foto.png", "image/png", "img".getBytes());

        var res = palService.actualizarPala(5, dto, img);

        assertThat(res.getImagen()).contains("5_Babolat_Viper");
        verify(palRepository, times(1)).save(any(Pal.class));
    }

    @Test
    void eliminarPala_ok() {
        Pal existente = new Pal();
        existente.setId(3);
        existente.setPalact(true);
        when(palRepository.findById(3)).thenReturn(Optional.of(existente));

        palService.eliminarPala(3);

        assertThat(existente.getPalact()).isFalse();
        verify(palRepository).save(existente);
    }
}
