package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

public class FuenteDatosMySQL implements IFuenteDatos {

	@Override
	public IAlumnos crearAlumnos() {
		return new Alumnos();
	}

	@Override
	public ICiclosFormativos crearCiclosFormativos() {
		return new CiclosFormativos();
	}

	@Override
	public IAsignaturas crearAsignaturas() {
		return new Asignaturas();
	}

	@Override
	public IMatriculas crearMatriculas() {
		return new Matriculas();
	}

}