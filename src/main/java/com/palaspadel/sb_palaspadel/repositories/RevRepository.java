package com.palaspadel.sb_palaspadel.repositories;

import com.palaspadel.sb_palaspadel.entities.Rev;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description: Funcionalidades de la entidad Rev
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
public interface RevRepository extends JpaRepository<Rev, Integer> {
    List<Rev> findByRevUsu (String usuariorev);

}
