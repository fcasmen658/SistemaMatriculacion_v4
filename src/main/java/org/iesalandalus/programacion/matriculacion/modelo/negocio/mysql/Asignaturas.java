package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Curso;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.EspecialidadProfesorado;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.GradoE;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Asignaturas implements IAsignaturas{
	
	private Connection conexion;
	
	private static Asignaturas instancia = null;
	
    private ArrayList<Asignatura> coleccionAsignaturas;

    public Asignaturas() {
        this.coleccionAsignaturas = new ArrayList<>();
        comenzar();
    }
    
    public static Asignaturas getInstancia() {
    	if (instancia == null) {
        instancia = new Asignaturas();
    	}
    	return instancia;
    }
    
    public Curso getCurso(String curso) {
    	return Curso.valueOf(curso.toUpperCase());
    }
    
	public EspecialidadProfesorado getEspecialidadProfesorado(String especialidad) {
		return EspecialidadProfesorado.valueOf(especialidad.toUpperCase());
	}

    public ArrayList<Asignatura> get() throws SQLException {
        ArrayList<Asignatura> copiaAsignaturas = new ArrayList<>();
		String query = """
				SELECT a.codigo
					, a.nombre
				    , a.horasAnuales
				    , a.curso
				    , a.horasDesdoble
				    , a.especialidadProfesorado
				    , a.codigoCicloFormativo
				FROM asignatura a
				ORDER BY a.nombre
				    """;
		Statement sentencia = conexion.createStatement();
		ResultSet rs = sentencia.executeQuery(query);
		while (rs.next()) {
        	CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(rs.getInt("codigoCicloFormativo"), "ficticio", new GradoE("gradoe", 1, 1), "ficticio", 1));
			Asignatura asignatura = new Asignatura(
                    rs.getString("codigo"), 
                    rs.getString("nombre"), 
                    rs.getInt("horasAnuales"), 
                    getCurso(rs.getString("curso")), 
                    rs.getInt("horasDesdoble"), 
                    getEspecialidadProfesorado(rs.getString("especialidadProfesorado")), 
                    cicloFormativo);
			copiaAsignaturas.add(asignatura);
		}
		return copiaAsignaturas;
    }

	public int getTamano() throws SQLException {
		String query = """
	    		SELECT COUNT(codigo) 
	    		FROM cicloFormativo
	    		""";
		Statement stmt = conexion.createStatement(); 
		ResultSet rs = stmt.executeQuery(query);
		return rs.getInt(1);
	}

    public void insertar(Asignatura asignatura) throws OperationNotSupportedException, SQLException {
        Objects.requireNonNull(asignatura, "ERROR: No se puede insertar una asignatura nula.");
		if(buscar(asignatura) != null) {
			throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
		}
        String query = """
				INSERT INTO asignatura
					(codigo, 
					nombre, 
					horasAnuales, 
					curso, 
					horasDesdoble, 
					especialidadProfesorado, 
					codigoCicloFormativo)
				VALUES
					(?, ?, ?, ?, ?, ?, ?)
				""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, asignatura.getCodigo());
        pstmt.setString(2, asignatura.getNombre());
        pstmt.setInt(3, asignatura.getHorasAnuales());
        pstmt.setString(4, asignatura.getCurso().name().toLowerCase());
        pstmt.setInt(5, asignatura.getHorasDesdoble());
        pstmt.setString(6, asignatura.getEspecialidadProfesorado().name().toLowerCase());
        pstmt.setInt(7, asignatura.getCicloFormativo().getCodigo());
        pstmt.executeUpdate();
    }

    public Asignatura buscar(Asignatura asignatura) throws SQLException {
        Objects.requireNonNull(asignatura, "ERROR: No se puede buscar una asignatura nula.");
        String query = """
				SELECT a.codigo
					, a.nombre
				    , a.horasAnuales
				    , a.curso
				    , a.horasDesdoble
				    , a.especialidadProfesorado
				    , a.codigoCicloFormativo
				FROM asignatura a
				WHERE a.codigo = ?
				    """;
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, asignatura.getCodigo());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
        	CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(rs.getInt("codigoCicloFormativo"), "ficticio", new GradoE("gradoe", 1, 1), "ficticio", 1));
			return new Asignatura(
					rs.getString("codigo"), 
					rs.getString("nombre"), 
					rs.getInt("horasAnuales"),
					getCurso(rs.getString("curso")), 
					rs.getInt("horasDesdoble"),
					getEspecialidadProfesorado(rs.getString("especialidadProfesorado")), 
					cicloFormativo);
        }
        return null;
    }

    public void borrar(Asignatura asignatura) throws OperationNotSupportedException, SQLException {
        Objects.requireNonNull(asignatura, "ERROR: No se puede borrar una asignatura nula.");
		if (buscar(asignatura) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
		}
		String query = """
				DELETE FROM asignatura
                WHERE codigo = ?
				""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, asignatura.getCodigo());
        pstmt.executeUpdate();
    }

	@Override
	public void comenzar() {
		conexion = MySQL.establecerConexion();
	}

	@Override
	public void terminar() {
		MySQL.cerrarConexion();
	}

}