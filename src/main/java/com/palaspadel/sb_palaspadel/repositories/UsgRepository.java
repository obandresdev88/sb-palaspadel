package com.palaspadel.sb_palaspadel.repositories;

import com.palaspadel.sb_palaspadel.entities.Usg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Description: Funcionalidades de la entidad Usg
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
public interface UsgRepository extends JpaRepository<Usg, Integer> {

    Optional<Usg> findByUsgnom(String usgnom);

}
