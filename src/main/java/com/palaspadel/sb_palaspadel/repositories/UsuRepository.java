package com.palaspadel.sb_palaspadel.repositories;

import com.palaspadel.sb_palaspadel.entities.Usu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Description: Funcionalidades de la entidad Usu
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
public interface UsuRepository extends JpaRepository<Usu, Integer> {

    boolean existsByUsuema(String usuema);
}
