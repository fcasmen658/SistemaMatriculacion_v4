package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

public class Modelo {

    private Alumnos alumnos;
    private Matriculas matriculas;
    private Asignaturas asignaturas;
    private CiclosFormativos ciclosFormativos;

    public void comenzar() {
        this.alumnos = new Alumnos();
        this.asignaturas = new Asignaturas();
        this.ciclosFormativos = new CiclosFormativos();
        this.matriculas = new Matriculas();
        try {
            Alumno a1 = new Alumno("Pepe", "12345678Z", "a@a.com", "666666666", LocalDate.of(2000, 1, 1));
            insertar(a1);
            CicloFormativo cf1 = new CicloFormativo(1234, "DAW", new GradoD("DAW", 2, Modalidad.PRESENCIAL), "Desarrollo Aplicaciones Web", 20);
            insertar(cf1);
            Asignatura as1 = new Asignatura("5678", "Programacion", 30, Curso.PRIMERO, 2, EspecialidadProfesorado.INFORMATICA, cf1);
            insertar(as1);
            Matricula m1 = new Matricula(9876, "24-25", LocalDate.now(), a1, this.asignaturas.get());
            insertar(m1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void terminar() {
        System.out.println("Aplicacion terminada.");
    }

    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        this.alumnos.insertar(alumno);
    }

    public Alumno buscar(Alumno alumno) {
        return this.alumnos.buscar(alumno);
    }

    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }
        this.alumnos.borrar(alumno);
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos.get();
    }

    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        this.asignaturas.insertar(asignatura);
    }

    public Asignatura buscar(Asignatura asignatura) {
        return this.asignaturas.buscar(asignatura);
    }

    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }
        this.asignaturas.borrar(asignatura);
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas.get();
    }

    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        this.ciclosFormativos.insertar(cicloFormativo);
    }

    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        return this.ciclosFormativos.buscar(cicloFormativo);
    }

    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        this.ciclosFormativos.borrar(cicloFormativo);
    }

    public ArrayList<CicloFormativo> getCiclosFormativos() {
        return ciclosFormativos.get();
    }

    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        this.matriculas.insertar(matricula);
    }

    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        return this.matriculas.buscar(matricula);
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        this.matriculas.borrar(matricula);
    }

    public ArrayList<Matricula> getMatriculas() throws OperationNotSupportedException {
        return matriculas.get();
    }

    public ArrayList<Matricula> getMatriculas(Alumno alumno) {
        return matriculas.get(alumno);
    }

    public ArrayList<Matricula> getMatriculas(CicloFormativo cicloFormativo) {
        return matriculas.get(cicloFormativo);
    }

    public ArrayList<Matricula> getMatriculas(String cursoAcademico) {
        return matriculas.get(cursoAcademico);
    }
}