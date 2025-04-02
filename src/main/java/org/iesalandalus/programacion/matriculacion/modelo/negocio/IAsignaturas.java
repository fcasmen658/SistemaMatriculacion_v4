package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IAsignaturas {

    void comenzar();

    void terminar();

    ArrayList<Asignatura> get() throws SQLException;

    int getTamano() throws SQLException;

    void insertar(Asignatura asignatura) throws OperationNotSupportedException, SQLException;

    Asignatura buscar(Asignatura asignatura) throws SQLException;

    void borrar(Asignatura asignatura) throws OperationNotSupportedException, SQLException;
}
