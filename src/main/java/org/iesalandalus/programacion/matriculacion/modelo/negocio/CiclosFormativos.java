package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import java.util.ArrayList;
import javax.naming.OperationNotSupportedException;

public class CiclosFormativos {
    private final ArrayList<CicloFormativo> coleccionCiclosFormativos;

    public CiclosFormativos() {
        coleccionCiclosFormativos = new ArrayList<>();
    }

    public ArrayList<CicloFormativo> get() {
        return copiaProfundaCiclosFormativos();
    }

    private ArrayList<CicloFormativo> copiaProfundaCiclosFormativos() {
        ArrayList<CicloFormativo> copiaCiclosFormativos = new ArrayList<>();
        for (CicloFormativo coleccionCiclosFormativo : this.coleccionCiclosFormativos) {
            copiaCiclosFormativos.add(new CicloFormativo(coleccionCiclosFormativo));
        }
        return copiaCiclosFormativos;
    }


    public int getTamano() {
        return this.coleccionCiclosFormativos.size();
    }


    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }

        int indice = this.coleccionCiclosFormativos.indexOf(cicloFormativo);
        if (indice == -1) {
            coleccionCiclosFormativos.add(new CicloFormativo(cicloFormativo));
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }
    }

    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede buscar un ciclo formativo nulo.");
        }

        int indice = this.coleccionCiclosFormativos.indexOf(cicloFormativo);
        if (indice == -1) {
            return null;
        } else {
            return new CicloFormativo(this.coleccionCiclosFormativos.get(indice));
        }
    }

    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }
        int indice = this.coleccionCiclosFormativos.indexOf(cicloFormativo);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");
        }
        this.coleccionCiclosFormativos.remove(indice);
    }
}