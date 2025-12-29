package com.palaspadel.sb_palaspadel.exceptions;

public class RecursoYaExisteException extends RuntimeException {
    public RecursoYaExisteException(String recurso, String campo, String valor) {
        super(String.format("El %s con %s '%s' ya está registrado", recurso, campo, valor));
    }
}
