package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Alumnos implements IAlumnos {


    private final ArrayList<Alumno> coleccionAlumnos;

    public Alumnos() {
        coleccionAlumnos = new ArrayList<>();
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }

    @Override
    public ArrayList<Alumno> get() {
        return copiaProfundaAlumnos();
    }

    private ArrayList<Alumno> copiaProfundaAlumnos() {
        ArrayList<Alumno> copiaAlumnos = new ArrayList<>();
        for (Alumno alumno : coleccionAlumnos) {
            copiaAlumnos.add(new Alumno(alumno));
        }
        return copiaAlumnos;
    }

    @Override
    public int getTamano() {
        return this.coleccionAlumnos.size();
    }

    @Override
    public void insertar(Alumno alumno) throws OperationNotSupportedException {

        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }

        int indice = this.coleccionAlumnos.indexOf(alumno);
        if (indice != -1) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }
        this.coleccionAlumnos.add(new Alumno(alumno));
    }

    @Override
    public Alumno buscar(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede buscar un alumno nulo.");
        }
        int indice = this.coleccionAlumnos.indexOf(alumno);
        if (indice == -1) {
            return null;
        } else {
            return new Alumno(this.coleccionAlumnos.get(indice));
        }
    }

    @Override
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }
        int indice = this.coleccionAlumnos.indexOf(alumno);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        }
        coleccionAlumnos.remove(indice);
    }

}
