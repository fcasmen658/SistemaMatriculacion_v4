package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import java.util.Objects;

public class Asignatura {

    public static final int MAX_NUM_HORAS_ANUALES = 300;
    public static final int MAX_NUM_HORAS_DESDOBLES = 6;
    private static final String ER_CODIGO = "^\\d{4}$";

    private String codigo;
    private String nombre;
    private int horasAnuales;
    private Curso curso;
    private int horasDesdoble;
    private EspecialidadProfesorado especialidadProfesorado;
    private CicloFormativo cicloFormativo;

    public Asignatura(String codigo, String nombre, int horasAnuales, Curso curso, int horasDesdoble,
            EspecialidadProfesorado especialidadProfesorado, CicloFormativo cicloFormativo) {
        setCodigo(codigo);
        setNombre(nombre);
        setHorasAnuales(horasAnuales);
        setCurso(curso);
        setHorasDesdoble(horasDesdoble);
        setEspecialidadProfesorado(especialidadProfesorado);
        setCicloFormativo(cicloFormativo);
    }

    public Asignatura(Asignatura asignatura) {
        if (asignatura == null) {
            throw new IllegalArgumentException("ERROR: No es posible copiar una asignatura nula.");
        }
        setCodigo(asignatura.codigo);
        setNombre(asignatura.nombre);
        setHorasAnuales(asignatura.horasAnuales);
        setCurso(asignatura.curso);
        setHorasDesdoble(asignatura.horasDesdoble);
        setEspecialidadProfesorado(asignatura.especialidadProfesorado);
        setCicloFormativo(asignatura.cicloFormativo);
    }

    public String getCodigo() {
        return codigo;
    }

    private void setCodigo(String codigo) {
        if (codigo == null) {
            throw new IllegalArgumentException("ERROR: El código de una asignatura no puede ser nulo.");
        }
        if (codigo.isBlank()) {
            throw new IllegalArgumentException("ERROR: El código de una asignatura no puede estar vacío.");
        }
        if (!codigo.matches(ER_CODIGO)) {
            throw new IllegalArgumentException("ERROR: El código de la asignatura no tiene un formato válido.");
        }
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("ERROR: El nombre de una asignatura no puede ser nulo.");
        }
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR: El nombre de una asignatura no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public int getHorasAnuales() {
        return horasAnuales;
    }

    public void setHorasAnuales(int horasAnuales) {
        if (horasAnuales <= 0 || horasAnuales > MAX_NUM_HORAS_ANUALES) {
            throw new IllegalArgumentException("ERROR: El número de horas de una asignatura no puede ser menor o igual a 0 ni mayor a " + MAX_NUM_HORAS_ANUALES + ".");
        }
        this.horasAnuales = horasAnuales;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("ERROR: El curso de una asignatura no puede ser nulo.");
        }

        if (curso != Curso.PRIMERO && curso != Curso.SEGUNDO) {
            throw new IllegalArgumentException("ERROR: El curso de una asignatura debe ser Primero o Segundo.");
        }
        this.curso = curso;
    }

    public int getHorasDesdoble() {
        return horasDesdoble;
    }

    public void setHorasDesdoble(int horasDesdoble) {
        if (horasDesdoble < 0 || horasDesdoble > MAX_NUM_HORAS_DESDOBLES) {
            throw new IllegalArgumentException(
                    "ERROR: El número de horas de desdoble de una asignatura no puede ser menor a 0 ni mayor a " + MAX_NUM_HORAS_DESDOBLES + ".");
        }
        this.horasDesdoble = horasDesdoble;
    }

    public EspecialidadProfesorado getEspecialidadProfesorado() {
        return especialidadProfesorado;
    }

    public void setEspecialidadProfesorado(EspecialidadProfesorado especialidadProfesorado) {
        if (especialidadProfesorado == null) {
            throw new IllegalArgumentException("ERROR: La especialidad del profesorado de una asignatura no puede ser nula.");
        }

        this.especialidadProfesorado = especialidadProfesorado;
    }

    public CicloFormativo getCicloFormativo() {
        return cicloFormativo;
    }

    public void setCicloFormativo(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: El ciclo formativo de una asignatura no puede ser nulo.");
        }

        this.cicloFormativo = cicloFormativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asignatura that = (Asignatura) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "Código asignatura=" + codigo + ", nombre asignatura=" + nombre
                + ", ciclo formativo=Código ciclo formativo=" + cicloFormativo.getCodigo() + ", nombre ciclo formativo="
                + cicloFormativo.getNombre();
    }

    public String imprimir() {
        return "Código asignatura=" + codigo + ", nombre asignatura=" + nombre
                + ", ciclo formativo=Código ciclo formativo=" + cicloFormativo.getCodigo() + ", nombre ciclo formativo="
                + cicloFormativo.getNombre();
    }
}