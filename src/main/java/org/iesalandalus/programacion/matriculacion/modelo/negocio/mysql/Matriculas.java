package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Curso;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.EspecialidadProfesorado;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.GradoE;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Matriculas implements IMatriculas{
	
	private Connection conexion;
	
	private static Matriculas instancia = null;
	
    private ArrayList<Matricula> coleccionMatriculas;

    public Matriculas() {
        coleccionMatriculas = new ArrayList<>();
        comenzar();
    }
    
	public static Matriculas getInstancia() {
    	if (instancia == null) {
    		instancia = new Matriculas();
    	}
    	return instancia;
	}

    public ArrayList<Matricula> get() throws OperationNotSupportedException, SQLException {
    	ArrayList<Matricula> copiaMatriculas = new ArrayList<>();
    	String query = """
    			SELECT m.idMatricula,
				    m.cursoAcademico,
				    m.fechaMatriculacion,
				    m.fechaAnulacion,
				    m.dni
				FROM matricula m
				LEFT JOIN alumno a ON m.dni = a.dni
				ORDER BY m.fechaMatriculacion DESC, a.nombre
    			""";
    	Statement sentencia = conexion.createStatement();
    	ResultSet rs = sentencia.executeQuery(query);
    	while (rs.next()) {
    		Alumno alumno = Alumnos.getInstancia().buscar(new Alumno("ficticio", rs.getString("dni"), "ficticio@fake.com", "666554433", LocalDate.of(2000, 1, 1)));
			Matricula matricula = new Matricula(rs.getInt("idMatricula"), 
					rs.getString("cursoAcademico"),
					rs.getDate("fechaMatriculacion").toLocalDate(),
					alumno,
					getAsignaturasMatricula(rs.getInt("idMatricula")));
			if (rs.getDate("fechaAnulacion") != null) {
				matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
			}
			copiaMatriculas.add(matricula);
    	}
        return copiaMatriculas;
    }
    
    private ArrayList<Asignatura> getAsignaturasMatricula(int idMatricula) throws SQLException{
    	String query = """
    			SELECT a.codigo
					, a.nombre
					, a.horasAnuales
					, a.curso
					, a.horasDesdoble
					, a.especialidadProfesorado
					, a.codigoCicloFormativo
				FROM asignaturasMatricula am
				LEFT JOIN asignatura a ON am.codigo = a.codigo
				WHERE am.idMatricula = ?
    			""";
    	PreparedStatement pstmt = conexion.prepareStatement(query);
    	pstmt.setInt(1, idMatricula);
    	ResultSet rs = pstmt.executeQuery();
    	ArrayList<Asignatura> asignaturas = new ArrayList<>();
		while (rs.next()) {
			CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(
					rs.getInt("codigoCicloFormativo"), "ficticio", new GradoE("gradoe", 1, 1), "ficticio", 1));
			Asignatura asignatura = new Asignatura(
					rs.getString("codigo"), 
					rs.getString("nombre"),
					rs.getInt("horasAnuales"), 
					Curso.valueOf(rs.getString("curso").toUpperCase()), 
					rs.getInt("horasDesdoble"),
					EspecialidadProfesorado.valueOf(rs.getString("especialidadProfesorado").toUpperCase()), 
					cicloFormativo);
			asignaturas.add(asignatura);
		}
		return asignaturas;
    }


    public int getTamano() throws SQLException {
    	String query = """
	    		SELECT COUNT(codigo) 
	    		FROM matricula
	    		""";
		Statement stmt = conexion.createStatement(); 
		ResultSet rs = stmt.executeQuery(query);
		return rs.getInt(1);
    }

    public void insertar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        Objects.requireNonNull(matricula, "ERROR: No se puede insertar una matrícula nula.");
		if (buscar(matricula) != null) {
			throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
		}
		String query = """
				INSERT INTO matricula
					(idMatricula,
					cursoAcademico,
					fechaMatriculacion,
					fechaAnulacion,
					dni)
				VALUES
					(?, ?, ?, ?, ?)
				""";
		PreparedStatement pstmt = conexion.prepareStatement(query);
		pstmt.setInt(1, matricula.getIdMatricula());
		pstmt.setString(2, matricula.getCursoAcademico());
		pstmt.setDate(3, java.sql.Date.valueOf(matricula.getFechaMatriculacion()));
		if (matricula.getFechaAnulacion() == null) {
			pstmt.setNull(4, java.sql.Types.DATE);
		}else {
			pstmt.setDate(4, java.sql.Date.valueOf(matricula.getFechaAnulacion()));
		}
		pstmt.setString(5, matricula.getAlumno().getDni());
		pstmt.executeUpdate();
        insertarAsignaturasMatricula(matricula.getIdMatricula(), matricula.getColeccionAsignaturas());
    }
    
    private void insertarAsignaturasMatricula(int idMatricula, ArrayList<Asignatura> coleccionAsigntauras) throws SQLException{
    	String query = """
    			INSERT INTO asignaturasMatricula
					(idMatricula
				    ,codigo)
				VALUES 
					(?, ?)
    			""";
    	PreparedStatement pstmt = conexion.prepareStatement(query);
		for (Asignatura asignatura : coleccionAsigntauras) {
			pstmt.setInt(1, idMatricula);
			pstmt.setString(2, asignatura.getCodigo());
			pstmt.executeUpdate();
		}
    	
    }

    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        Objects.requireNonNull(matricula, "ERROR: No se puede buscar una Matricula nula.");
        String query = """
        		SELECT m.idMatricula,
	                m.cursoAcademico,
	                m.fechaMatriculacion,
	                m.fechaAnulacion,
	                m.dni
                FROM matricula m
                WHERE m.idMatricula = ?
        		""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setInt(1, matricula.getIdMatricula());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
        	Alumno alumno = Alumnos.getInstancia().buscar(new Alumno("ficticio", rs.getString("dni"), "ficticio@fake.com", "666554433", LocalDate.of(2000, 1, 1)));
        	Matricula matriculaEncontrada = new Matricula(rs.getInt("idMatricula"),
        			rs.getString("cursoAcademico"),
        			rs.getDate("fechaMatriculacion").toLocalDate(),
        			alumno,
        			getAsignaturasMatricula(rs.getInt("idMatricula")));
            return matriculaEncontrada;
        }
        return null;
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        Objects.requireNonNull(matricula, "ERROR: No se puede borrar una matrícula nula.");
		if (buscar(matricula) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
		}
		String query = """
				UPDATE matricula SET fechaAnulacion = ?
                WHERE idMatricula = ?
                """;
		PreparedStatement pstmt = conexion.prepareStatement(query);
		pstmt.setDate(1, java.sql.Date.valueOf(matricula.getFechaAnulacion()));
		pstmt.setInt(2, matricula.getIdMatricula());
		pstmt.executeUpdate();
    }


    public ArrayList<Matricula> get(Alumno alumno) throws OperationNotSupportedException, SQLException {
    	ArrayList<Matricula> copiaMatriculas = new ArrayList<>();
    	String query = """
    			SELECT m.idMatricula,
				    m.cursoAcademico,
				    m.fechaMatriculacion,
				    m.fechaAnulacion,
				    m.dni,
				    a.nombre,
				    a.correo,
				    a.telefono,
				    a.fechaNacimiento
				FROM matricula m
				LEFT JOIN alumno a ON m.dni = a.dni
				WHERE a.dni = ?
				ORDER BY m.fechaMatriculacion DESC, a.nombre
    			""";
    	PreparedStatement pstmt = conexion.prepareStatement(query);
		pstmt.setString(1, alumno.getDni());
    	ResultSet rs = pstmt.executeQuery();
    	while (rs.next()) {
    		Alumno a = new Alumno(rs.getString("nombre"), rs.getString("dni")
    				, rs.getString("correo"), rs.getString("telefono")
    				, rs.getDate("fechaNacimiento").toLocalDate());
			Matricula matricula = new Matricula(rs.getInt("idMatricula"), 
					rs.getString("cursoAcademico"),
					rs.getDate("fechaMatriculacion").toLocalDate(),
					a,
					getAsignaturasMatricula(rs.getInt("idMatricula")));
			if (rs.getDate("fechaAnulacion") != null) {
				matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
			}
			copiaMatriculas.add(matricula);
    	}
        return copiaMatriculas;
    }

    public ArrayList<Matricula> get(String cursoAcademico) throws OperationNotSupportedException, SQLException {
    	ArrayList<Matricula> copiaMatriculas = new ArrayList<>();
    	String query = """
    			SELECT m.idMatricula,
				    m.cursoAcademico,
				    m.fechaMatriculacion,
				    m.fechaAnulacion,
				    m.dni,
				    a.nombre,
				    a.correo,
				    a.telefono,
				    a.fechaNacimiento
				FROM matricula m
				LEFT JOIN alumno a ON m.dni = a.dni
				WHERE m.cursoAcademico = ?
				ORDER BY m.fechaMatriculacion DESC, a.nombre
    			""";
    	PreparedStatement pstmt = conexion.prepareStatement(query);
		pstmt.setString(1, cursoAcademico);
    	ResultSet rs = pstmt.executeQuery();
    	while (rs.next()) {
    		Alumno a = new Alumno(rs.getString("nombre"), rs.getString("dni")
    				, rs.getString("correo"), rs.getString("telefono")
    				, rs.getDate("fechaNacimiento").toLocalDate());
			Matricula matricula = new Matricula(rs.getInt("idMatricula"), 
					rs.getString("cursoAcademico"),
					rs.getDate("fechaMatriculacion").toLocalDate(),
					a,
					getAsignaturasMatricula(rs.getInt("idMatricula")));
			if (rs.getDate("fechaAnulacion") != null) {
				matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
			}
			copiaMatriculas.add(matricula);
    	}
        return copiaMatriculas;
    }

    public ArrayList<Matricula> get(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
    	ArrayList<Matricula> copiaMatriculas = new ArrayList<>();
    	String query = """
    			SELECT m.idMatricula,
					m.cursoAcademico,
					m.fechaMatriculacion,
					m.fechaAnulacion,
					m.dni,
				    al.nombre,
				    al.correo,
				    al.telefono,
				    al.fechaNacimiento
				FROM matricula m
				LEFT JOIN asignaturasMatricula am ON m.idMatricula = am.idMatricula
				LEFT JOIN asignatura a ON am.codigo = a.codigo
				LEFT JOIN alumno al ON m.dni = al.dni
				WHERE a.codigoCicloFormativo = ?
				ORDER BY m.fechaMatriculacion DESC, al.nombre
    			""";
    	PreparedStatement pstmt = conexion.prepareStatement(query);
		pstmt.setInt(1, cicloFormativo.getCodigo());
    	ResultSet rs = pstmt.executeQuery();
    	while (rs.next()) {
    		Alumno a = new Alumno(rs.getString("nombre"), rs.getString("dni")
    				, rs.getString("correo"), rs.getString("telefono")
    				, rs.getDate("fechaNacimiento").toLocalDate());
			Matricula matricula = new Matricula(rs.getInt("idMatricula"), 
					rs.getString("cursoAcademico"),
					rs.getDate("fechaMatriculacion").toLocalDate(),
					a,
					getAsignaturasMatricula(rs.getInt("idMatricula")));
			if (rs.getDate("fechaAnulacion") != null) {
				matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
			}
			copiaMatriculas.add(matricula);
    	}
        return copiaMatriculas;
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