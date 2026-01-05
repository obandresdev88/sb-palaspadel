package com.palaspadel.sb_palaspadel.services.impl;

import com.palaspadel.sb_palaspadel.dto.PalaActualizarDto;
import com.palaspadel.sb_palaspadel.dto.PalaCrearDto;
import com.palaspadel.sb_palaspadel.dto.PalaResponseDto;
import com.palaspadel.sb_palaspadel.entities.Pal;
import com.palaspadel.sb_palaspadel.repositories.PalRepository;
import com.palaspadel.sb_palaspadel.services.PalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Description: Implementación de la lógica de negocio para palas
 * <p>
 * Created by Andres on 2026
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PalServiceImpl implements PalService {

    private final PalRepository palRepository;

    @Value("${app.upload.dir:src/main/resources/images}")
    private String uploadDir;

    private static final List<String> EXTENSIONES_PERMITIDAS = Arrays.asList("jpg", "jpeg", "png", "webp");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @Override
    public List<PalaResponseDto> listarPalasActivas() {
        return palRepository.findPalByPalactIs(true).stream()
                .map(this::convertirAResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PalaResponseDto obtenerPalaPorId(Integer id) {
        Pal pala = palRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pala no encontrada con ID: " + id));
        return convertirAResponseDto(pala);
    }

    @Override
    public PalaResponseDto crearPala(PalaCrearDto dto, MultipartFile imagen) {
        // Verificar si ya existe pala con misma marca y modelo
        Optional<Pal> existente = palRepository.findByPalmarAndPalmod(dto.getMarca(), dto.getModelo());
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe una pala con marca " + dto.getMarca() + " y modelo " + dto.getModelo());
        }

        Pal pala = new Pal();
        pala.setPalmar(dto.getMarca());
        pala.setPalmod(dto.getModelo());
        pala.setPalpes(dto.getPeso());
        pala.setPalfor(dto.getForma());
        pala.setPaldur(dto.getDureza());
        pala.setPalbal(dto.getBalance());
        pala.setPalpre(dto.getPrecio());
        pala.setPalurl(dto.getUrlCompra());
        pala.setPalact(true);
        pala.setPalimg("/images/default.webp"); // si no viene pondremos una imagen por defecto


        // Guardar primero para obtener el ID
        Pal palaGuardada = palRepository.save(pala);

        // Guardar imagen si se proporciona
        if (imagen != null && !imagen.isEmpty()) {
            validarImagen(imagen);
            String nombreImagen = guardarImagen(imagen, palaGuardada.getId(), palaGuardada.getPalmar(), palaGuardada.getPalmod());
            palaGuardada.setPalimg(nombreImagen);
            palaGuardada = palRepository.save(palaGuardada);
        }

        log.info("Pala creada: {} {}", palaGuardada.getPalmar(), palaGuardada.getPalmod());
        return convertirAResponseDto(palaGuardada);
    }

    @Override
    public PalaResponseDto actualizarPala(Integer id, PalaActualizarDto dto, MultipartFile imagen) {
        Pal pala = palRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pala no encontrada con ID: " + id));

        // Verificar si marca/modelo ya existen en otra pala
        Optional<Pal> existente = palRepository.findByPalmarAndPalmod(dto.getMarca(), dto.getModelo());
        if (existente.isPresent() && !existente.get().getId().equals(id)) {
            throw new RuntimeException("Ya existe otra pala con marca " + dto.getMarca() + " y modelo " + dto.getModelo());
        }

        pala.setPalmar(dto.getMarca());
        pala.setPalmod(dto.getModelo());
        pala.setPalpes(dto.getPeso());
        pala.setPalfor(dto.getForma());
        pala.setPaldur(dto.getDureza());
        pala.setPalbal(dto.getBalance());
        pala.setPalpre(dto.getPrecio());
        pala.setPalurl(dto.getUrlCompra());
//        pala.setPalact(dto.getActivo());

        // Actualizar imagen si se proporciona
        if (imagen != null && !imagen.isEmpty()) {
            validarImagen(imagen);
            // Eliminar imagen anterior si existe
            if (pala.getPalimg() != null && !pala.getPalimg().isEmpty()) {
                eliminarImagen(pala.getPalimg());
            }
            String nombreImagen = guardarImagen(imagen, pala.getId(), pala.getPalmar(), pala.getPalmod());
            pala.setPalimg(nombreImagen);
        }

        Pal palaActualizada = palRepository.save(pala);
        log.info("Pala actualizada: {} {}", palaActualizada.getPalmar(), palaActualizada.getPalmod());
        return convertirAResponseDto(palaActualizada);
    }

    @Override
    public void eliminarPala(Integer id) {
        Pal pala = palRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pala no encontrada con ID: " + id));

        pala.setPalact(false);
        palRepository.save(pala);
        log.info("Pala eliminada (lógico): {} {}", pala.getPalmar(), pala.getPalmod());
    }

    @Override
    public int importarPalasDesdeExcel(MultipartFile file) throws Exception {
        List<Pal> palasImportadas = new java.util.ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                if (row.getCell(0) == null || row.getCell(0).getStringCellValue().trim().isEmpty()) continue;

                String marca = row.getCell(0).getStringCellValue().trim();
                String modelo = row.getCell(1).getStringCellValue().trim();

                Optional<Pal> existente = palRepository.findByPalmarAndPalmod(marca, modelo);
                if (existente.isPresent()) {
                    log.warn("Pala ya existe: {} {}. Se omite.", marca, modelo);
                    continue;
                }

                Pal pala = new Pal();
                pala.setPalmar(marca);
                pala.setPalmod(modelo);
                pala.setPalpes((int) row.getCell(2).getNumericCellValue());
                pala.setPalfor(validarEnum(row.getCell(3), Pal.FormaPala.class, Pal.FormaPala.REDONDA));
                pala.setPaldur(validarEnum(row.getCell(4), Pal.DurezaPala.class, Pal.DurezaPala.MEDIA));
                pala.setPalbal(validarEnum(row.getCell(5), Pal.BalancePala.class, Pal.BalancePala.MEDIO));

                if (row.getCell(6) != null && row.getCell(6).getCellType() == CellType.NUMERIC) {
                    pala.setPalpre(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
                } else {
                    pala.setPalpre(BigDecimal.ZERO);
                }

                pala.setPalurl(row.getCell(7) != null ? row.getCell(7).getStringCellValue().trim() : "");
                pala.setPalimg(row.getCell(8) != null ? row.getCell(8).getStringCellValue() : "");
                pala.setPalact(true);

                palasImportadas.add(pala);
            }
        }

        palRepository.saveAll(palasImportadas);
        log.info("Palas importadas: {}", palasImportadas.size());
        return palasImportadas.size();
    }

    /**
     * Validar imagen antes de guardar
     */
    private void validarImagen(MultipartFile archivo) {
        if (archivo.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }

        if (archivo.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("El archivo excede el tamaño máximo permitido (5MB)");
        }

        String nombreOriginal = archivo.getOriginalFilename();
        if (nombreOriginal == null || !nombreOriginal.contains(".")) {
            throw new RuntimeException("El archivo no tiene una extensión válida");
        }

        String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf(".") + 1).toLowerCase();
        if (!EXTENSIONES_PERMITIDAS.contains(extension)) {
            throw new RuntimeException("Formato de imagen no permitido. Use: " + String.join(", ", EXTENSIONES_PERMITIDAS));
        }
    }

    /**
     * Guardar imagen en src/main/resources/images con formato id_marca_modelo.extension
     */
    private String guardarImagen(MultipartFile archivo, Integer id, String marca, String modelo) {
        try {
            Path dirPath = Paths.get(uploadDir);
            Files.createDirectories(dirPath);

            String nombreOriginal = archivo.getOriginalFilename();
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf(".")).toLowerCase();

            String marcaLimpia = marca.replaceAll("[^a-zA-Z0-9]", "_");
            String modeloLimpio = modelo.replaceAll("[^a-zA-Z0-9]", "_");
            String nombreImagen = id + "_" + marcaLimpia + "_" + modeloLimpio + extension;

            Path rutaArchivo = dirPath.resolve(nombreImagen);
            Files.write(rutaArchivo, archivo.getBytes());

            log.info("Imagen guardada: {}", nombreImagen);
            return nombreImagen; // guardar solo el nombre en la entidad/BD
        } catch (IOException e) {
            log.error("Error al guardar imagen: {}", e.getMessage(), e);
            throw new RuntimeException("Error al guardar imagen: " + e.getMessage(), e);
        }
    }

    /**
     * Eliminar imagen del sistema de archivos
     */
    private void eliminarImagen(String nombreImagen) {
        try {
            if (nombreImagen == null || nombreImagen.isEmpty()) return;
            // Si viene con prefijo /images/ lo eliminamos
            String nombre = nombreImagen;
            if (nombre.startsWith("/images/")) {
                nombre = nombre.substring("/images/".length());
            } else if (nombre.startsWith("images/")) {
                nombre = nombre.substring("images/".length());
            }
            Path rutaArchivo = Paths.get(uploadDir).resolve(nombre);
            Files.deleteIfExists(rutaArchivo);
            log.info("Imagen eliminada: {}", nombre);
        } catch (IOException e) {
            log.warn("Error al eliminar imagen {}: {}", nombreImagen, e.getMessage());
        }
    }


    private <E extends Enum<E>> E validarEnum(Cell cell, Class<E> enumType, E defaultValue) {
        if (cell == null || cell.getCellType() != CellType.STRING || cell.getStringCellValue().trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Enum.valueOf(enumType, cell.getStringCellValue().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Valor inválido para {}: {}", enumType.getSimpleName(), cell.getStringCellValue());
            return defaultValue;
        }
    }

    private PalaResponseDto convertirAResponseDto(Pal pala) {
        PalaResponseDto dto = new PalaResponseDto();
        dto.setId(pala.getId());
        dto.setMarca(pala.getPalmar());
        dto.setModelo(pala.getPalmod());
        dto.setPeso(pala.getPalpes());
        dto.setForma(pala.getPalfor());
        dto.setDureza(pala.getPaldur());
        dto.setBalance(pala.getPalbal());
        dto.setPrecio(pala.getPalpre());
        dto.setUrlCompra(pala.getPalurl());
        // Construir URL pública de la imagen
        String img = pala.getPalimg();
        if (img == null || img.trim().isEmpty()) {
            dto.setImagen("/images/default.webp");
        } else if (img.startsWith("http") || img.startsWith("/images/")) {
            dto.setImagen(img);
        } else {
            dto.setImagen("/images/" + img);
        }
        dto.setActivo(pala.getPalact());
        // dto.setCreatedAt(pala.getCreatedAt()); // Descomentado cuando agregues el campo al DTO
        return dto;
    }
}
