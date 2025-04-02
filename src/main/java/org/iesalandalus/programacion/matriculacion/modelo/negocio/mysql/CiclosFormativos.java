package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Grado;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.GradoD;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.GradoE;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Modalidad;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import javax.naming.OperationNotSupportedException;

public class CiclosFormativos implements ICiclosFormativos{
	
	private Connection conexion;
	
	private static CiclosFormativos instancia = null;
	
    private ArrayList<CicloFormativo> coleccionCiclosFormativos;

    public CiclosFormativos() {
        this.coleccionCiclosFormativos = new ArrayList<>();
        comenzar();
    }
    
	public static CiclosFormativos getInstancia() {
		if (instancia == null) {
			instancia = new CiclosFormativos();
		}
		return instancia;
	}
	
	public Grado getGrado(String tipoGrado, String nombreGrado, int numAniosGrado, String modalidad, int numEdiciones) {
		Grado grado = null;
		Modalidad modalidadGrado = Modalidad.valueOf(modalidad.toUpperCase());
		if (tipoGrado.equals("gradod")) {
			grado = new GradoD(nombreGrado, numAniosGrado, modalidadGrado);
		}else {
			grado = new GradoE(nombreGrado, numAniosGrado, numEdiciones);
		}
		return grado;
	}

    public ArrayList<CicloFormativo> get() throws SQLException {
        ArrayList<CicloFormativo> copiaCiclosFormativos = new ArrayList<>();
        String query = """
        		SELECT codigo
				    , familiaProfesional
				    , grado
				    , nombre
				    , horas
				    , nombreGrado
				    , numAniosGrado
				    , modalidad
				    , numEdiciones
				    FROM cicloFormativo
				    ORDER BY nombre
        		""";
        Statement sentencia = conexion.createStatement();
        ResultSet rs = sentencia.executeQuery(query);
        while(rs.next()) {
        	Grado grado = getGrado(rs.getString(3), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getInt(9));
        	CicloFormativo cicloFormativo = new CicloFormativo(rs.getInt(1), rs.getString(2), grado, rs.getString(4), rs.getInt(5));
        	copiaCiclosFormativos.add(cicloFormativo);
        }
        return copiaCiclosFormativos;
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

    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        Objects.requireNonNull(cicloFormativo, "ERROR: No se puede insertar un ciclo formativo nulo.");
        if(buscar(cicloFormativo) != null) {
        	throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }
        String query = """
        		INSERT INTO cicloFormativo
					(codigo,
					familiaProfesional,
					grado,
					nombre,
					horas,
					nombreGrado,
					numAniosGrado,
					modalidad,
					numEdiciones)
				VALUES
					(?, ?, ?, ?, ?, ?, ?, ?, ?)
        		""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
    	pstmt.setInt(1, cicloFormativo.getCodigo());
    	pstmt.setString(2, cicloFormativo.getFamiliaProfesional());
    	pstmt.setString(3, cicloFormativo.getGrado().getClass().getSimpleName().toLowerCase());
    	pstmt.setString(4, cicloFormativo.getNombre());
    	pstmt.setInt(5, cicloFormativo.getHoras());
        pstmt.setString(6, cicloFormativo.getGrado().getNombre());
        pstmt.setInt(7, cicloFormativo.getGrado().getNumAnios());
        if(cicloFormativo.getGrado() instanceof GradoD) {
        	pstmt.setString(8, ((GradoD)cicloFormativo.getGrado()).getModalidad().toString());
        	pstmt.setNull(9, java.sql.Types.INTEGER);
        }else {
			pstmt.setNull(8, java.sql.Types.VARCHAR);
			pstmt.setInt(9, ((GradoE) cicloFormativo.getGrado()).getNumEdiciones());
        }
    	pstmt.executeUpdate();
    }

    public CicloFormativo buscar(CicloFormativo cicloFormativo) throws SQLException {
        Objects.requireNonNull(cicloFormativo, "ERROR: No se puede buscar un Ciclo Formativo nulo.");
        String query = """
        		SELECT codigo
        			, familiaProfesional
        			,grado
        			,nombre
        			,horas
        			,nombreGrado
        			,numAniosGrado
        			,modalidad
        			,numEdiciones
        		FROM cicloFormativo
        		WHERE codigo = ?
        		""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setInt(1, cicloFormativo.getCodigo());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
        	Grado grado = null;
        	if(rs.getString(3).equals("gradod")) {
        		grado = new GradoD(rs.getString(6), rs.getInt(7), Modalidad.valueOf(rs.getString(8).toUpperCase()));
    		}else {
    			grado = new GradoE(rs.getString(6), rs.getInt(7), rs.getInt(9));
    		}
        	return new CicloFormativo(rs.getInt(1), rs.getString(2), grado, rs.getString(4), rs.getInt(5));
        }
        return null;
    }

    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        Objects.requireNonNull(cicloFormativo, "ERROR: No se puede borrar un ciclo formativo nulo.");
        if (buscar(cicloFormativo) == null) {
            throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");
        }
		String query = """
				DELETE FROM cicloFormativo
				WHERE codigo = ?
				""";
		PreparedStatement pstmt = conexion.prepareStatement(query);
		pstmt.setInt(1, cicloFormativo.getCodigo());
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