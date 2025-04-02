package org.iesalandalus.programacion.matriculacion.controlador;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

public class Controlador {

    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        if (modelo == null) {
            throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
        }
        if (vista == null) {
            throw new NullPointerException("ERROR: La vista no puede ser nula");
        }
        this.modelo = modelo;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    public void insertar(Alumno alumno) throws OperationNotSupportedException, SQLException {
        modelo.insertar(alumno);
    }

    public Alumno buscar(Alumno alumno) throws SQLException, OperationNotSupportedException {
        return modelo.buscar(alumno);
    }

    public void borrar(Alumno alumno) throws OperationNotSupportedException, SQLException {
        this.modelo.borrar(alumno);
    }

    public ArrayList<Alumno> getAlumnos() throws SQLException, OperationNotSupportedException {
        return modelo.getAlumnos();
    }

    public void insertar(Asignatura asignatura) throws OperationNotSupportedException, SQLException {
        modelo.insertar(asignatura);
    }

    public Asignatura buscar(Asignatura asignatura) throws SQLException {
        return modelo.buscar(asignatura);
    }

    public void borrar(Asignatura asignatura) throws OperationNotSupportedException, SQLException {
        modelo.borrar(asignatura);
    }

    public ArrayList<Asignatura> getAsignaturas() throws SQLException {
        return modelo.getAsignaturas();
    }

    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        modelo.insertar(cicloFormativo);
    }

    public CicloFormativo buscar(CicloFormativo cicloFormativo) throws SQLException {
        return modelo.buscar(cicloFormativo);
    }

    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        modelo.borrar(cicloFormativo);
    }

    public ArrayList<CicloFormativo> getCicloFormativos() throws SQLException {
        return modelo.getCiclosFormativos();
    }

    public void insertar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        modelo.insertar(matricula);
    }

    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        return modelo.buscar(matricula);
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        modelo.borrar(matricula);
    }

    public ArrayList<Matricula> getMatriculas() throws OperationNotSupportedException, SQLException {
        return modelo.getMatriculas();
    }

    public ArrayList<Matricula> getMatriculas(Alumno alumno) throws OperationNotSupportedException, SQLException {
        return modelo.getMatriculas(alumno);
    }

    public ArrayList<Matricula> getMatriculas(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        return modelo.getMatriculas(cicloFormativo);
    }

    public ArrayList<Matricula> getMatriculas(String cursoAcademico) throws OperationNotSupportedException, SQLException {
        return modelo.getMatriculas(cursoAcademico);
    }
}