package com.palaspadel.sb_palaspadel.services;

import com.palaspadel.sb_palaspadel.entities.Usg;
import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.entities.UsuUsg;
import com.palaspadel.sb_palaspadel.entities.UsuUsgId;
import com.palaspadel.sb_palaspadel.repositories.UsgRepository;
import com.palaspadel.sb_palaspadel.repositories.UsuRepository;
import com.palaspadel.sb_palaspadel.repositories.UsuUsgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Description: Logica de negocio implementada de la entidad Usu
 *
 * Created by Andres on 2025
 * @version 1.0
 */


@Service
@RequiredArgsConstructor // Lombok genera un constructor para
public class UsuService {

    private final UsuRepository usuRepository;
    private final UsuUsgRepository usuUsgRepository;
    private final UsgRepository usgRepository;

    public List<Usu> findAll() {
        return usuRepository.findAll();
    }

    public Optional<Usu>findByEmail(String email) {
        return usuRepository.findByUsuema(email);
    }

    public Usu save(Usu usu) {
        // Guardamos el usuario primero
        Usu savedUsu = usuRepository.save(usu);

        // Buscamos el rol de "USER", por defecto todos deben tener este rol
        // A los administradores se les asigna el rol "ADMIN" manualmente por DB
        // Tal vez en el futuro implementemos un endpoint para asignar roles

        Optional<Usg> rolUser = usgRepository.findByUsgnom("USER");

        // Si el rol "USER" existe, lo asignamos al usuario
        rolUser.ifPresent(usg -> {
            // Crear la clave primaria compuesta
            UsuUsgId usuUsgId = new UsuUsgId();
            usuUsgId.setUsuusuid(savedUsu.getId());
            usuUsgId.setUsuusgid(usg.getId());

            // Creamos la entidad de relación con la clave compuesta
            UsuUsg usuarioRol = new UsuUsg();
            usuarioRol.setId(usuUsgId);
            usuarioRol.setUsuario(savedUsu);
            usuarioRol.setPermiso(usg);

            // Guardar la relación en la tabla intermedia
            usuUsgRepository.save(usuarioRol);
        });

        return savedUsu;
    }




    public Usu findById(Integer id) {
        return usuRepository.findById(id).orElse(null);
    }

}
