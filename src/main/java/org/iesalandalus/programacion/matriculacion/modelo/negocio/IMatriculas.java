package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IMatriculas {

    void comenzar();

    void terminar();

    ArrayList<Matricula> get() throws OperationNotSupportedException, SQLException;

    int getTamano() throws SQLException;

    void insertar(Matricula matricula) throws OperationNotSupportedException, SQLException;

    Matricula buscar(Matricula matricula) throws OperationNotSupportedException, SQLException;

    void borrar(Matricula matricula) throws OperationNotSupportedException, SQLException;

    ArrayList<Matricula> get(Alumno alumno) throws OperationNotSupportedException, SQLException;

    ArrayList<Matricula> get(String cursoAcademico) throws OperationNotSupportedException, SQLException;

    ArrayList<Matricula> get(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException;
}
