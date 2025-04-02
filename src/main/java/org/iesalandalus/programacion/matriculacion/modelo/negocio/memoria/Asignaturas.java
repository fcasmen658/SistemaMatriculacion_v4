package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Asignaturas implements IAsignaturas {
    private final ArrayList<Asignatura> coleccionAsignaturas;

    public Asignaturas() {
        this.coleccionAsignaturas = new ArrayList<>();
        comenzar();
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }

    @Override
    public ArrayList<Asignatura> get() {
        return copiaProfundaAsignaturas();
    }

    private ArrayList<Asignatura> copiaProfundaAsignaturas() {
        ArrayList<Asignatura> copiaAsignaturas = new ArrayList<>();
        for (Asignatura coleccionAsignatura : coleccionAsignaturas) {
            copiaAsignaturas.add(new Asignatura(coleccionAsignatura));
        }
        return copiaAsignaturas;
    }

    @Override
    public int getTamano() {
        return this.coleccionAsignaturas.size();
    }

    @Override
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }
        int indice = this.coleccionAsignaturas.indexOf(asignatura);
        if (indice == -1) {
            coleccionAsignaturas.add(new Asignatura(asignatura));
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
    }


    @Override
    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede buscar una asignatura nula.");
        }
        int indice = this.coleccionAsignaturas.indexOf(asignatura);
        if (indice == -1) {
            return null;
        } else {
            return new Asignatura(this.coleccionAsignaturas.get(indice));
        }
    }

    @Override
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        } else {
            int indice = this.coleccionAsignaturas.indexOf(asignatura);
            if (indice == -1) {
                throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
            }
            this.coleccionAsignaturas.remove(indice);
        }
    }
}