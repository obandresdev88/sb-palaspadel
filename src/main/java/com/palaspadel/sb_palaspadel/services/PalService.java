package com.palaspadel.sb_palaspadel.services;

import com.palaspadel.sb_palaspadel.entities.Pal;
import com.palaspadel.sb_palaspadel.repositories.PalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Description: Logica de negocio implementada de la entidad Pal
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PalService {
    private final PalRepository palRepository;
    public List<Pal> palasImportadas = new ArrayList<>();

    public List<Pal> findAll() {
        return palRepository.findAll();
    }

    public Pal agregarPala(Pal pala) {
        return palRepository.save(pala);
    }

    // Funcion para procesar el archivo Excel e importar palas
    public void importarPalasDesdeExcel(MultipartFile file) throws Exception {
//        List<Pal> palas = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0); // Tomar la primera hoja

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la primera fila (encabezados)
                if (row.getCell(0) == null || row.getCell(0).getStringCellValue().trim().isEmpty()) continue; // Saltar filas vacías

                String marca = row.getCell(0).getStringCellValue().trim();
                String modelo = row.getCell(1).getStringCellValue().trim();
                Optional<Pal> existente = palRepository.findByPalmarAndPalmod(marca, modelo);
                if (existente.isPresent()) {
                    log.warn("La pala " + marca + " " + modelo + " ya existe. Se omite.");
                    continue;
                }

                Pal pala = new Pal();
                pala.setPalmar(marca);; // Marca
                pala.setPalmod(modelo);; // Modelo
                pala.setPalpes((int) row.getCell(2).getNumericCellValue()); // Peso

                // Validaciones para evitar valores incorrectos
                pala.setPalfor(validarEnum(row.getCell(3), Pal.FormaPala.class, Pal.FormaPala.REDONDA)); // Forma
                pala.setPaldur(validarEnum(row.getCell(4), Pal.DurezaPala.class, Pal.DurezaPala.MEDIA)); // Dureza
                pala.setPalbal(validarEnum(row.getCell(5), Pal.BalancePala.class, Pal.BalancePala.MEDIO)); // Balance

                if (row.getCell(6) != null && row.getCell(6).getCellType() == CellType.NUMERIC) {
                    pala.setPalpre(BigDecimal.valueOf(row.getCell(6).getNumericCellValue())); // Precio
                } else {
                    pala.setPalpre(BigDecimal.ZERO); // Valor por defecto
                }

                pala.setPalurl(row.getCell(7) != null ? row.getCell(7).getStringCellValue().trim() : ""); // Manejar URL vacía

                palasImportadas.add(pala);
            }
        }

        log.info("Palas importadas correctamente: " + palasImportadas.size());
        palRepository.saveAll(palasImportadas);
    }

    /**
     * Funcion para validar y convertir valores de un Enum desde una celda Excel.
     * Si la celda es nula o vacía, retorna el valor por defecto.
     */
    private <E extends Enum<E>> E validarEnum(Cell cell, Class<E> enumType, E defaultValue) {
        if (cell == null || cell.getCellType() != CellType.STRING || cell.getStringCellValue().trim().isEmpty()) {
            return defaultValue;
        }

        try {
            return Enum.valueOf(enumType, cell.getStringCellValue().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Valor inválido para enum " + enumType.getSimpleName() + ": " + cell.getStringCellValue());
            return defaultValue; // Si el valor no es válido, usa el valor por defecto
        }
    }


}
