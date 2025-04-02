package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Matriculas implements IMatriculas {
    private final ArrayList<Matricula> coleccionMatriculas;

    public Matriculas() {
        coleccionMatriculas = new ArrayList<>();
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }

    @Override
    public ArrayList<Matricula> get() throws OperationNotSupportedException {
        return copiaProfundaMatriculas();
    }

    private ArrayList<Matricula> copiaProfundaMatriculas() throws OperationNotSupportedException {
        ArrayList<Matricula>copiaMatriculas = new ArrayList<>();
        for (Matricula coleccionMatricula : coleccionMatriculas) {
            copiaMatriculas.add(new Matricula(coleccionMatricula));
        }
        return copiaMatriculas;
    }

    @Override
    public int getTamano() {
        return this.coleccionMatriculas.size();
    }

    @Override
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }

        int indice = this.coleccionMatriculas.indexOf(matricula);
    
        if (indice == -1) {
            this.coleccionMatriculas.add(new Matricula(matricula));
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
        }
    }

    @Override
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede buscar una matrícula nula.");
        }

        int indice = this.coleccionMatriculas.indexOf(matricula);
        if (indice == -1) {
            return null;
        } else {
            return this.coleccionMatriculas.get(indice);
        }
    }

    @Override
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }

        int indice = this.coleccionMatriculas.indexOf(matricula);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        } 
        coleccionMatriculas.remove(indice);
    }

    @Override
    public ArrayList<Matricula> get(Alumno alumno) {
        ArrayList<Matricula> aux = new ArrayList<>();
        for (Matricula m : coleccionMatriculas) {
            if (m.getAlumno().equals(alumno)) {
                aux.add(m);
            }
        }
        return aux;
    }

    @Override
    public ArrayList<Matricula> get(String cursoAcademico) {
        ArrayList<Matricula> aux = new ArrayList<>();
        for (Matricula m : coleccionMatriculas) {
            if (m.getCursoAcademico().equals(cursoAcademico)) {
                aux.add(m);
            }
        }
        return aux;
    }

    @Override
    public ArrayList<Matricula> get(CicloFormativo cicloFormativo) {
        ArrayList<Matricula> aux = new ArrayList<>();
        for (Matricula m : coleccionMatriculas) {
            for (Asignatura a : m.getColeccionAsignaturas()) {
                if (a.getCicloFormativo().equals(cicloFormativo)) {
                    aux.add(m);
                    break;
                }
            }
        }
        return aux;
    }
}