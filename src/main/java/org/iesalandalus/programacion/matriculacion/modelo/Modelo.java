package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Matriculas;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

/**
 * Clase Modelo que gestiona las operaciones sobre los objetos de negocio:
 * alumnos, asignaturas, ciclos formativos y matrículas.
 * Permite insertar, buscar, borrar y obtener colecciones de estos objetos.
 */
public class Modelo {

    private IAlumnos alumnos;
    private IMatriculas matriculas;
    private IAsignaturas asignaturas;
    private ICiclosFormativos ciclosFormativos;
    private IFuenteDatos fuenteDatos;

    public Modelo(FactoriaFuenteDatos factoriaFuenteDatos) {
        setFuenteDatos(factoriaFuenteDatos);
    }

    private void setFuenteDatos(FactoriaFuenteDatos factoriaFuenteDatos) {
        this.fuenteDatos = factoriaFuenteDatos.crear();
    }

    /**
     * Inicializa las colecciones de alumnos, asignaturas, ciclos formativos y matrículas
     * con la capacidad definida.
     */
    public void comenzar() {
        this.alumnos = fuenteDatos.crearAlumnos();
        this.asignaturas = fuenteDatos.crearAsignaturas();
        this.ciclosFormativos = fuenteDatos.crearCiclosFormativos();
        this.matriculas = fuenteDatos.crearMatriculas();

//        try {
//			Alumno a1 = new Alumno("Pepe", "12345678Z", "a@a.com", "666666666", LocalDate.of(2000, 1, 1));
//			insertar(a1);
//			CicloFormativo cf1 = new CicloFormativo(1234, "DAW", new GradoE("GE", 1, 1), "Desarrollo Aplicaciones Web", 20);
//			insertar(cf1);
//			Asignatura as1 = new Asignatura("5678", "Programacion", 30, Curso.PRIMERO, 2, EspecialidadProfesorado.INFORMATICA, cf1);
//			insertar(as1);
//			Matricula m1 = new Matricula(9876, "24-25", LocalDate.now(), a1, this.asignaturas.get());
//			insertar(m1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }

    /**
     * Finaliza la aplicación mostrando un mensaje por consola.
     */
    public void terminar() {
        this.alumnos.terminar();
        this.asignaturas.terminar();
        this.ciclosFormativos.terminar();
        this.matriculas.terminar();
    }

    /**
     * Inserta un alumno en la colección de alumnos.
     *
     * @param alumno El alumno a insertar.
     * @throws OperationNotSupportedException Si ocurre un error al insertar el alumno.
     * @throws SQLException
     */
    public void insertar(Alumno alumno) throws OperationNotSupportedException, SQLException {
        this.alumnos.insertar(alumno);
    }

    /**
     * Busca un alumno en la colección de alumnos.
     *
     * @param alumno El alumno a buscar.
     * @return El alumno encontrado o null si no se encuentra.
     * @throws SQLException
     * @throws OperationNotSupportedException
     */
    public Alumno buscar(Alumno alumno) throws OperationNotSupportedException, SQLException {
        return this.alumnos.buscar(alumno);
    }

    /**
     * Borra un alumno de la colección de alumnos.
     *
     * @param alumno El alumno a borrar.
     * @throws OperationNotSupportedException Si ocurre un error al borrar el alumno.
     * @throws SQLException
     */
    public void borrar(Alumno alumno) throws OperationNotSupportedException, SQLException {
        this.alumnos.borrar(alumno);
    }

    /**
     * Obtiene todos los alumnos de la colección.
     *
     * @return Un array con todos los alumnos.
     * @throws SQLException
     * @throws OperationNotSupportedException
     */
    public ArrayList<Alumno> getAlumnos() throws OperationNotSupportedException, SQLException {
        return alumnos.get();
    }

    /**
     * Inserta una asignatura en la colección de asignaturas.
     *
     * @param asignatura La asignatura a insertar.
     * @throws OperationNotSupportedException Si ocurre un error al insertar la asignatura.
     * @throws SQLException
     */
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException, SQLException {
        this.asignaturas.insertar(asignatura);
    }

    /**
     * Busca una asignatura en la colección de asignaturas.
     *
     * @param asignatura La asignatura a buscar.
     * @return La asignatura encontrada o null si no se encuentra.
     * @throws SQLException
     */
    public Asignatura buscar(Asignatura asignatura) throws SQLException {
        return this.asignaturas.buscar(asignatura);
    }

    /**
     * Borra una asignatura de la colección de asignaturas.
     *
     * @param asignatura La asignatura a borrar.
     * @throws OperationNotSupportedException Si ocurre un error al borrar la asignatura.
     * @throws SQLException
     */
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException, SQLException {
        this.asignaturas.borrar(asignatura);
    }

    /**
     * Obtiene todas las asignaturas de la colección.
     *
     * @return Un array con todas las asignaturas.
     * @throws SQLException
     */
    public ArrayList<Asignatura> getAsignaturas() throws SQLException {
        return asignaturas.get();
    }

    /**
     * Inserta un ciclo formativo en la colección de ciclos formativos.
     *
     * @param cicloFormativo El ciclo formativo a insertar.
     * @throws OperationNotSupportedException Si ocurre un error al insertar el ciclo formativo.
     * @throws SQLException
     */
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        this.ciclosFormativos.insertar(cicloFormativo);
    }

    /**
     * Busca un ciclo formativo en la colección de ciclos formativos.
     *
     * @param cicloFormativo El ciclo formativo a buscar.
     * @return El ciclo formativo encontrado o null si no se encuentra.
     * @throws SQLException
     */
    public CicloFormativo buscar(CicloFormativo cicloFormativo) throws SQLException {
        return this.ciclosFormativos.buscar(cicloFormativo);
    }

    /**
     * Borra un ciclo formativo de la colección de ciclos formativos.
     *
     * @param cicloFormativo El ciclo formativo a borrar.
     * @throws OperationNotSupportedException Si ocurre un error al borrar el ciclo formativo.
     * @throws SQLException
     */
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        this.ciclosFormativos.borrar(cicloFormativo);
    }

    /**
     * Obtiene todos los ciclos formativos de la colección.
     *
     * @return Un array con todos los ciclos formativos.
     * @throws SQLException
     */
    public ArrayList<CicloFormativo> getCiclosFormativos() throws SQLException {
        return ciclosFormativos.get();
    }

    /**
     * Inserta una matrícula en la colección de matrículas.
     *
     * @param matricula La matrícula a insertar.
     * @throws OperationNotSupportedException Si ocurre un error al insertar la matrícula.
     * @throws SQLException
     */
    public void insertar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        this.matriculas.insertar(matricula);
    }

    /**
     * Busca una matrícula en la colección de matrículas.
     *
     * @param matricula La matrícula a buscar.
     * @return La matrícula encontrada.
     * @throws OperationNotSupportedException Si ocurre un error al buscar la matrícula.
     * @throws SQLException
     */
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        return this.matriculas.buscar(matricula);
    }

    /**
     * Borra una matrícula de la colección de matrículas.
     *
     * @param matricula La matrícula a borrar.
     * @throws OperationNotSupportedException Si ocurre un error al borrar la matrícula.
     * @throws SQLException
     */
    public void borrar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        this.matriculas.borrar(matricula);
    }

    /**
     * Obtiene todas las matrículas de la colección.
     *
     * @return Un array con todas las matrículas.
     * @throws OperationNotSupportedException Si ocurre un error al obtener las matrículas.
     * @throws SQLException
     */
    public ArrayList<Matricula> getMatriculas() throws OperationNotSupportedException, SQLException {
        return matriculas.get();
    }

    /**
     * Obtiene todas las matrículas asociadas a un alumno.
     *
     * @param alumno El alumno cuyas matrículas se van a obtener.
     * @return Un array con las matrículas asociadas al alumno.
     * @throws OperationNotSupportedException Si ocurre un error al obtener las matrículas.
     * @throws SQLException
     */
    public ArrayList<Matricula> getMatriculas(Alumno alumno) throws OperationNotSupportedException, SQLException {
        return matriculas.get(alumno);
    }

    /**
     * Obtiene todas las matrículas asociadas a un ciclo formativo.
     *
     * @param cicloFormativo El ciclo formativo cuyas matrículas se van a obtener.
     * @return Un array con las matrículas asociadas al ciclo formativo.
     * @throws OperationNotSupportedException
     * @throws SQLException
     */
    public ArrayList<Matricula> getMatriculas(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        return matriculas.get(cicloFormativo);
    }

    /**
     * Obtiene todas las matrículas asociadas a un curso académico.
     *
     * @param cursoAcademico El curso académico cuyas matrículas se van a obtener.
     * @return Un array con las matrículas asociadas al curso académico.
     * @throws OperationNotSupportedException
     * @throws SQLException
     */
    public ArrayList<Matricula> getMatriculas(String cursoAcademico) throws OperationNotSupportedException, SQLException {
        return matriculas.get(cursoAcademico);
    }
}