package com.palaspadel.sb_palaspadel.repositories;

import com.palaspadel.sb_palaspadel.entities.Pal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PalRepository extends JpaRepository<Pal, Integer> {

    List<Pal> findByPalpesBetween(Integer minPeso, Integer maxPeso);
    List<Pal> findByPalpreLessThanEqual(java.math.BigDecimal precio);
    List<Pal> findByPalactTrue();
    List<Pal> findByPalfor(Pal.FormaPala forma);

    /**
     * Buscar pala por marca y modelo
     * se utiliza para evitar duplicados
     *
     * @param palmar
     * @param palmod
     * @return
     */
    Optional<Pal> findByPalmarAndPalmod(String palmar, String palmod);



    /**
     * Listar palas por su estado activo/inactivo
     * se utiliza para mostrar palas activas en el catálogo
     *
     * @param palact
     * @return
     */
    List<Pal> findPalByPalactIs(Boolean palact);
}
