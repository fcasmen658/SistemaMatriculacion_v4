package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

public class Matricula {
	public static final int MAXIMO_MESES_ANTERIOR_ANULACION = 6;
	public static final int MAXIMO_DIAS_ANTERIOR_MATRICULA = 15;
	public static final int MAXIMO_NUMERO_HORAS_MATRICULA = 1000;
	public static final int MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA = 10;
	private static final String ER_CURSO_ACADEMICO = "\\d{2}-\\d{2}";
	public static final String FORMATO_FECHA = "dd/MM/YYYY";
	
	private int idMatricula;
	private String cursoAcademico;
	private LocalDate fechaMatriculacion;
	private LocalDate fechaAnulacion;
	private Alumno alumno;
	private ArrayList<Asignatura> coleccionAsignaturas;

	public Matricula(int idMatricula, String cursoAcademico, LocalDate fechaMatriculacion, Alumno alumno,
			ArrayList<Asignatura> coleccionAsignaturas) throws OperationNotSupportedException {
		setIdMatricula(idMatricula);
		setCursoAcademico(cursoAcademico);
		setFechaMatriculacion(fechaMatriculacion);
		setAlumno(alumno);
		setColeccionAsignaturas(coleccionAsignaturas);
	}

	public Matricula(Matricula matricula) throws OperationNotSupportedException {
		if (matricula == null) {
			throw new NullPointerException("ERROR: No es posible copiar una matrícula nula.");
		}
		setIdMatricula(matricula.getIdMatricula());
		setCursoAcademico(matricula.getCursoAcademico());
		setFechaMatriculacion(matricula.getFechaMatriculacion());
		setAlumno(matricula.getAlumno());
		setColeccionAsignaturas(matricula.getColeccionAsignaturas());
		if(matricula.getFechaAnulacion() != null) {
			setFechaAnulacion(matricula.getFechaAnulacion());
		}
	}

	public int getIdMatricula() {
		return this.idMatricula;
	}

	public void setIdMatricula(int idMatricula) {
		if (idMatricula <= 0) {
			throw new IllegalArgumentException("ERROR: El identificador de una matrícula no puede ser menor o igual a 0.");
		}
		this.idMatricula = idMatricula;
	}

	public String getCursoAcademico() {
		return this.cursoAcademico;
	}

	public void setCursoAcademico(String cursoAcademico) {
		if (cursoAcademico == null) {
			throw new NullPointerException("ERROR: El curso académico de una matrícula no puede ser nulo.");
		}
		if (cursoAcademico.isBlank()) {
			throw new IllegalArgumentException("ERROR: El curso académico de una matrícula no puede estar vacío.");
		}
		if (!cursoAcademico.matches(ER_CURSO_ACADEMICO)) {
			throw new IllegalArgumentException("ERROR: El formato del curso académico no es correcto.");
		}
		this.cursoAcademico = cursoAcademico;
	}

	public LocalDate getFechaMatriculacion() {
		return this.fechaMatriculacion;
	}

	public void setFechaMatriculacion(LocalDate fechaMatriculacion) throws IllegalArgumentException {
		if (fechaMatriculacion == null) {
			throw new NullPointerException("ERROR: La fecha de matriculación de una mátricula no puede ser nula.");
		}
		if (fechaMatriculacion.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser posterior a hoy.");
		}
		if (fechaMatriculacion.isBefore(LocalDate.now().minusDays(MAXIMO_DIAS_ANTERIOR_MATRICULA))) {
			throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser anterior a "
					+ MAXIMO_DIAS_ANTERIOR_MATRICULA + " días.");
		}
		this.fechaMatriculacion = fechaMatriculacion;
	}

	public LocalDate getFechaAnulacion() {
		return this.fechaAnulacion;
	}

	public void setFechaAnulacion(LocalDate fechaAnulacion) {

		if (fechaAnulacion == null){
			throw new NullPointerException("La fecha de anulación no puede ser nulo");
		}
		if (fechaAnulacion.isAfter(LocalDate.now())){
			throw new IllegalArgumentException("ERROR: La fecha de anulación de una matrícula no puede ser posterior a hoy.");
		}
		if(fechaAnulacion.isBefore(getFechaMatriculacion().minus(MAXIMO_MESES_ANTERIOR_ANULACION, ChronoUnit.MONTHS))){
			throw new IllegalArgumentException("ERROR: La fecha de anulación no puede ser anterior a la fecha de matriculación.");
		}
		if(fechaAnulacion.isBefore(getFechaMatriculacion())){
			throw new IllegalArgumentException("ERROR: La fecha de anulación no puede ser anterior a la fecha de matriculación.");
		}

		this.fechaAnulacion = fechaAnulacion;
	}

//	public void setFechaAnulacion(LocalDate fechaAnulacion) {
//		if (fechaAnulacion == null) {
//			throw new NullPointerException("ERROR: La fecha de anulación de una matrícula no puede ser nula.");
//		}
//		if (fechaAnulacion.isAfter(LocalDate.now())) {
//			throw new IllegalArgumentException(
//					"ERROR: La fecha de anulación de una matrícula no puede ser posterior a hoy.");
//		}
//		if (fechaAnulacion.isBefore(getFechaMatriculacion())) {
//			throw new IllegalArgumentException(
//					"ERROR: La fecha de anulación no puede ser anterior a la fecha de matriculación.");
//		}
//		if (fechaAnulacion.isAfter(getFechaMatriculacion().plusMonths(MAXIMO_MESES_ANTERIOR_ANULACION))) {
//			throw new IllegalArgumentException("ERROR: La fecha de anulación no puede ser posterior a los "
//					+ MAXIMO_MESES_ANTERIOR_ANULACION + " meses desde la fecha de matriculación.");
//		}
//
//		this.fechaAnulacion = fechaAnulacion;
//	}

	public Alumno getAlumno() {
		return this.alumno;
	}

	public void setAlumno(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno de una matrícula no puede ser nulo.");
		}
		this.alumno = alumno;
	}

	public ArrayList<Asignatura> getColeccionAsignaturas() {
		return new ArrayList<>(this.coleccionAsignaturas);
	}

	public void setColeccionAsignaturas(ArrayList<Asignatura> coleccionAsignaturas) throws OperationNotSupportedException {
		if (coleccionAsignaturas == null) {
			throw new NullPointerException("ERROR: La lista de asignaturas de una matrícula no puede ser nula.");
		}
		if (superaMaximoNumeroHorasMatricula(coleccionAsignaturas)) {
			throw new OperationNotSupportedException("ERROR: No se puede realizar la matrícula ya que supera el máximo de horas permitidas ("
							+ Matricula.MAXIMO_NUMERO_HORAS_MATRICULA + " horas).");
		}
		if (coleccionAsignaturas.size() > MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA) {
			throw new OperationNotSupportedException("ERROR: No se puede realizar la matrícula ya que supera el máximo de asignaturas permitidas ("
							+ Matricula.MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA + " asignaturas).");
		}
		this.coleccionAsignaturas = new ArrayList<Asignatura>();
		for (Asignatura a : coleccionAsignaturas) {
			if (a != null) {
				this.coleccionAsignaturas.add(new Asignatura(a));
			}
		}
	}

	private boolean superaMaximoNumeroHorasMatricula(ArrayList<Asignatura> asignaturasMatricula) {
		int horasMatricula = 0;
		for (Asignatura a : asignaturasMatricula) {
			if (a != null) {
				horasMatricula += a.getHorasAnuales();
			}
		}
		return horasMatricula > MAXIMO_NUMERO_HORAS_MATRICULA;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Matricula matricula = (Matricula) o;
		return this.idMatricula == matricula.idMatricula;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.idMatricula);
	}

	private String asignaturasMatricula() {
		String s = "";
		for (int i = 0; i < coleccionAsignaturas.size(); i++) {
			if (coleccionAsignaturas.get(i) != null) {
				s += coleccionAsignaturas.get(i).toString();
				if (i < coleccionAsignaturas.size() - 1) {
					s += ", ";
				}
			}
		}
		return s;
	}

	public String imprimir() {
		return "idMatricula=" + getIdMatricula() + ", " + "curso académico=" + getCursoAcademico() + ", "
				+ "fecha matriculación=" + getFechaMatriculacion().format(DateTimeFormatter.ofPattern(FORMATO_FECHA))
				+ ", " + "alumno=" + "{" + getAlumno().imprimir() + "}";

	}

	public String toString() {
		if (fechaAnulacion == null) {
			return "idMatricula=" + getIdMatricula() + ", " + "curso académico=" + getCursoAcademico() + ", "
					+ "fecha matriculación="
					+ getFechaMatriculacion().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) + ", " + "alumno="
					+ getAlumno().imprimir() + ", " + "Asignaturas=" + "{ " + asignaturasMatricula() + "}";

		} else {
			return "idMatricula=" + getIdMatricula() + ", " + "curso académico=" + getCursoAcademico() + ", "
					+ "fecha matriculación="
					+ getFechaMatriculacion().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) + ", "
					+ "fecha anulación=" + getFechaAnulacion().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) + ", "
					+ "alumno=" + getAlumno().imprimir() + ", " + "Asignaturas=" + "{ " + asignaturasMatricula() + "}";
		}
	}

}