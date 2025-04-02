package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class CiclosFormativos implements ICiclosFormativos {
    private final ArrayList<CicloFormativo> coleccionCiclosFormativos;

    public CiclosFormativos() {
        coleccionCiclosFormativos = new ArrayList<>();
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }

    @Override
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


    @Override
    public int getTamano() {
        return this.coleccionCiclosFormativos.size();
    }


    @Override
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

    @Override
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

    @Override
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