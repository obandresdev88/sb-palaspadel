package com.palaspadel.sb_palaspadel.services;

import com.palaspadel.sb_palaspadel.entities.Usg;
import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.entities.UsuUsg;
import com.palaspadel.sb_palaspadel.repositories.UsuUsgRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuUsgService {

    private final UsuUsgRepository usuUsgRepository;

    public UsuUsgService(UsuUsgRepository usuUsgRepository) {
        this.usuUsgRepository = usuUsgRepository;
    }

    public List<UsuUsg> obtenerRolesPorUsuario(Usu usuario) {
        return usuUsgRepository.findByUsuario(usuario);
    }

    public boolean tieneRol(Usu usuario) {
        return usuUsgRepository.existsByUsuario(usuario);
    }

    public List<UsuUsg> obtenerUsuariosPorRol(Usg permiso) {
        return usuUsgRepository.findByPermiso(permiso);
    }

    public boolean tienePermiso(Usg permiso) {
        return usuUsgRepository.existsByPermiso(permiso);
    }

    public void asignarRol(Usu usuario, Usg permiso) {
        UsuUsg usuUsg = new UsuUsg();
        usuUsg.setUsuario(usuario);
        usuUsg.setPermiso(permiso);
        usuUsgRepository.save(usuUsg);
    }

    public void eliminarRol(Usu usuario, Usg permiso) {
        usuUsgRepository.deleteByUsuarioAndPermiso(usuario, permiso);
    }
}
