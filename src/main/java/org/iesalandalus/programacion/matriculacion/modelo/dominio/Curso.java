package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import java.util.Objects;

public enum Curso {
    PRIMERO("Primero"),
    SEGUNDO("Segundo");

    private final String cadenaAMostrar;

    Curso(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir() {
        int digito = 0;
        if (Objects.equals(cadenaAMostrar, PRIMERO.cadenaAMostrar)) {
        } else {
            digito = 1;
        }
        return digito + ".-" + cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "Curso seleccionado:" + cadenaAMostrar;
    }

}