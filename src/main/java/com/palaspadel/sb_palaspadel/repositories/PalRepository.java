package com.palaspadel.sb_palaspadel.repositories;

import com.palaspadel.sb_palaspadel.entities.Pal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PalRepository extends JpaRepository<Pal, Integer> {

    @Query("SELECT p FROM Pal p WHERE p.palbal = :palbal AND p.palfor = :palfor AND p.paldur = :paldur AND p.palpre <= :palpre")
    List<Pal> findMatchingPals(@Param("palbal") Pal.BalancePala palbal,
                               @Param("palfor") Pal.FormaPala palfor,
                               @Param("paldur") Pal.DurezaPala paldur,
                               @Param("palpre") BigDecimal palpre);
}
