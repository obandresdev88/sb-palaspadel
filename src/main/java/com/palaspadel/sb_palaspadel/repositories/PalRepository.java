package com.palaspadel.sb_palaspadel.repositories;

import com.palaspadel.sb_palaspadel.entities.Pal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description: Funcionalidades de la entidad Pal
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
public interface PalRepository extends JpaRepository<Pal, Integer> {
    List<Pal> findByPalpes (String palpes);
    List<Pal> findByPalfor(String palfor);
    List<Pal> findByPaldur(String paldur);
    List<Pal> findByPalbal (String palbal);
    List<Pal> findByPalpre (String palpre);
}
