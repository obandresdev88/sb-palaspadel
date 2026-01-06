package com.palaspadel.sb_palaspadel.repositories;

import com.palaspadel.sb_palaspadel.entities.Usg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Description: Funcionalidades de la entidad Usg (Roles/Permisos)
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
public interface UsgRepository extends JpaRepository<Usg, Integer> {

    // Buscar rol por nombre
    Optional<Usg> findByUsgnom(String usgnom);

    // Comprobar si existe un rol por nombre
    boolean existsByUsgnom(String usgnom);

}
