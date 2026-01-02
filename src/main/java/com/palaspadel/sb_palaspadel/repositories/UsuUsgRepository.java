package com.palaspadel.sb_palaspadel.repositories;

import com.palaspadel.sb_palaspadel.entities.Usg;
import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.entities.UsuUsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Description: Funcionalidades de la entidad UsuUsg (Relación Usuario-Rol)
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
public interface UsuUsgRepository extends JpaRepository<UsuUsg, Integer> {

    // Buscar todos los roles de un usuario
    @Query("select uu from UsuUsg uu where uu.usuario = :usuario")
    List<UsuUsg> findByUsuario(@Param("usuario") Usu usuario);

    // Comprobar si un usuario tiene algún rol asignado
    @Query("select (count(uu) > 0) from UsuUsg uu where uu.usuario = :usuario")
    boolean existsByUsuario(@Param("usuario") Usu usuario);

    // Buscar todos los usuarios con un rol determinado
    @Query("select uu from UsuUsg uu where uu.permiso = :permiso")
    List<UsuUsg> findByPermiso(@Param("permiso") Usg permiso);

    // Comprobar si algún usuario tiene un rol determinado
    @Query("select (count(uu) > 0) from UsuUsg uu where uu.permiso = :permiso")
    boolean existsByPermiso(@Param("permiso") Usg permiso);

    // Eliminar relación usuario-rol
    @Modifying
    @Transactional
    @Query("delete from UsuUsg uu where uu.usuario = :usuario and uu.permiso = :permiso")
    void deleteByUsuarioAndPermiso(@Param("usuario") Usu usuario, @Param("permiso") Usg permiso);


}
