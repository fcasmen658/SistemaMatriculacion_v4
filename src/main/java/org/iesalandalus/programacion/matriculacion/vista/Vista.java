package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Vista {

    private Controlador controlador;
    private Vista vista;

    public Vista() {
        Opcion.setVista(this);
    }

    public void comenzar() {
        Opcion opcion;
        System.out.println("\n - - - Bienvenido al sistema de gestión de matrículas - - -\n");
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            opcion.ejecutar();
        } while (opcion != Opcion.SALIR);
    }

    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
    }

    public void terminar() {
    }

    public void insertarAlumno() {
        try {
            Alumno alumno = Consola.leerAlumno();
            controlador.insertar(alumno);
            System.out.println("Alumno insertado correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede insertar un Alumno nulo.");
        } catch (OperationNotSupportedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarAlumno() {
        try {
            Alumno alumno = controlador.buscar(Consola.getAlumnoPorDni());
            controlador.buscar(alumno);
            System.out.printf("Los datos del alumno solicitado son: %s", alumno);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void borrarAlumno() {
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            controlador.borrar(alumno);
            System.out.println("Alumno borrado correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarAlumnos() {
        ArrayList<Alumno> arrayAlumnos = controlador.getAlumnos();

        if (arrayAlumnos.isEmpty()) {
            System.out.println("No existen alumnos.");
        } else {
            arrayAlumnos.sort(Comparator.comparing(Alumno::getNombre, String.CASE_INSENSITIVE_ORDER));

            for (Alumno alumno : arrayAlumnos) {
                System.out.println(alumno);
            }
        }
    }

    public void insertarAsignatura() {
        try {
            mostrarCiclosFormativos();
            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            CicloFormativo ciclo = controlador.buscar(cicloFormativo);
            if (ciclo == null) {
                System.out.println("No existe el ciclo formativo indicado.");
                return;
            }
            Asignatura asignatura = Consola.leerAsignatura(ciclo);
            controlador.insertar(asignatura);
            System.out.println("Asignatura insertada correctamente.");
        } catch (IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede insertar una Asignatura nula.");
        }
    }

    public void buscarAsignatura() {
        try {
            Asignatura asignaturaBuscar = controlador.buscar(Consola.getAsignaturaPorCodigo());
            Asignatura encontrada = controlador.buscar(asignaturaBuscar);
            if (encontrada != null) {
                System.out.printf("Los datos de la asignatura solicitada son: %s", asignaturaBuscar);
            } else {
                System.out.println("No existe ninguna asignatura con tales datos.");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede buscar una asignatura nula.");
        }
    }

    public void borrarAsignatura() {
        try {
            Asignatura asignaturaBorrar = Consola.getAsignaturaPorCodigo();
            controlador.borrar(asignaturaBorrar);
            System.out.println("Asignatura borrada correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede borrar una asignatura nula.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarAsignaturas() {
        ArrayList<Asignatura> arrayAsignatura = controlador.getAsignaturas();
        if (arrayAsignatura.isEmpty()) {
            System.out.println("No existen asignaturas.");
        } else {
            arrayAsignatura.sort(Comparator.comparing(Asignatura::getNombre, String.CASE_INSENSITIVE_ORDER));
            for (Asignatura asignatura : arrayAsignatura) {
                System.out.println(asignatura);
            }
        }
    }

    public void insertarCicloFormativo() {
        try {
            CicloFormativo ciclosFormativo = Consola.leerCicloFormativo();
            controlador.insertar(ciclosFormativo);
            System.out.println("Ciclo formativo insertada correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede insertar Ciclo Formativo nulo.");
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarCicloFormativo() {
        try {
            CicloFormativo cicloFormativoBuscar = controlador.buscar(Consola.getCicloFormativoPorCodigo());
            CicloFormativo encontrada = controlador.buscar(cicloFormativoBuscar);
            if (encontrada != null) {
                System.out.printf("Los datos del ciclo formativo solicitado son: %s", cicloFormativoBuscar);
            } else {
                System.out.println("No existe ningun ciclo formativo con tales datos.");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede buscar un ciclo formativo nulo.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void borrarCicloFormativo() {
        try {
            CicloFormativo cicloFormativoBorrar = Consola.getCicloFormativoPorCodigo();
            controlador.borrar(cicloFormativoBorrar);
            System.out.println("Ciclo formativo borrada correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede borrar un ciclo formativo nulo.");
        } catch (IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarCiclosFormativos() {
        ArrayList<CicloFormativo> arrayCicloFormativo = controlador.getCicloFormativos();
        if (arrayCicloFormativo.isEmpty()) {
            System.out.println("No existen ciclos formativos.");
        } else {
            arrayCicloFormativo.sort(Comparator.comparing(CicloFormativo::getNombre, String.CASE_INSENSITIVE_ORDER));

            for (CicloFormativo cicloFormativo : arrayCicloFormativo) {
                System.out.println(cicloFormativo);
            }
        }
    }

    public void insertarMatricula() {
        try {
            System.out.println("Datos del alumno:");
            Alumno alumno = Consola.getAlumnoPorDni();
            Alumno a = controlador.buscar(alumno);
            if (a == null) {
                System.out.println("No existe el alumno indicado.");
                return;
            }
            System.out.println("Asignaturas de la matricula:");
            ArrayList<Asignatura> matriculaAsignaturas = Consola.elegirAsignaturasMatricula(controlador.getAsignaturas());
            System.out.println("Datos de la matricula:");
            Matricula matricula = Consola.leerMatricula(a, matriculaAsignaturas);
            controlador.insertar(matricula);
            System.out.println("Matricula insertada correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede insertar una Matricula nula.");
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarMatricula() {
        try {
            Matricula matriculaBuscar = controlador.buscar(Consola.getMatriculaPorIdentificador());
            Matricula encontrada = controlador.buscar(matriculaBuscar);
            if (encontrada != null) {
                System.out.printf("Los datos del ciclo formativo solicitado son: %s", matriculaBuscar);
            } else {
                System.out.println("No existe ningún ciclo formativo con tales datos.");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede buscar un ciclo formativo nulo.");
        } catch (IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void anularMatricula() {
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            Matricula matriculaAnular = controlador.buscar(Consola.getMatriculaPorIdentificador());
            if (matriculaAnular != null && matriculaAnular.getAlumno().equals(alumno)) {
                LocalDate fechaAnular = Consola.leerFecha("Indique la fecha de anulación:");
                matriculaAnular.setFechaAnulacion(fechaAnular);
                System.out.println("Matricula anulada correctamente.");
            } else {
                System.out.println("No se ha encontrado la matricula o no corresponde al alumno indicado.");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede anular una matricula nula.");
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarMatriculas() {
        try {
            ArrayList<Matricula> arrayMatriculas = controlador.getMatriculas();

            if (arrayMatriculas.isEmpty()) {
                System.out.println("No existen Matrículas.");
            } else {
                arrayMatriculas
                        .sort(Comparator.comparing(Matricula::getFechaMatriculacion)
                        .reversed()
                        .thenComparing(m -> m.getAlumno().getNombre(), String.CASE_INSENSITIVE_ORDER));

                for (Matricula matricula : arrayMatriculas) {
                    System.out.println(matricula);
                }
            }
        } catch (OperationNotSupportedException e) {
            System.out.println("ERROR: No se pudo mostrar matrículas.");
        }
    }

    public void mostrarMatriculasPorAlumno() {
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            ArrayList<Matricula> arrayMatricula = controlador.getMatriculas(alumno);

            if (arrayMatricula.isEmpty()) {
                System.out.println("No existen matrículas para el alumno indicado.");
            } else {
                arrayMatricula.sort(Comparator.comparing(Matricula::getFechaMatriculacion)
                        .reversed()
                        .thenComparing(m -> m.getAlumno().getNombre(), String.CASE_INSENSITIVE_ORDER));

                for (Matricula matricula : arrayMatricula) {
                    System.out.println(matricula);
                }
            }
        } catch (OperationNotSupportedException e) {
            System.out.println("ERROR: No se pueden mostrar matrículas por alumno.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarMatriculasPorCicloFormativo() {
        try {
            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            cicloFormativo = controlador.buscar(cicloFormativo);

            if (cicloFormativo == null) {
                System.out.println("No existe ningún ciclo formativo con tales datos.");
                return;
            }

            ArrayList<Matricula> matriculaCiclo = controlador.getMatriculas(cicloFormativo);

            if (matriculaCiclo.isEmpty()) {
                System.out.println("No existen matrículas para el ciclo formativo indicado.");
                return;
            }

            matriculaCiclo.sort(Comparator.comparing(Matricula::getFechaMatriculacion)
                    .reversed()
                    .thenComparing(m -> m.getAlumno().getNombre()));

            System.out.println("Matrículas del ciclo formativo "+cicloFormativo.getCodigo()+":");
            for (Matricula matricula : matriculaCiclo) {
                System.out.println(matricula);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    public void mostrarMatriculasPorCursoAcademico() {
        try {
            System.out.println("Indique el curso académico: ");
            System.out.println("El formato del curso es YY-YY");
            String cursoAcademico = Entrada.cadena();

            ArrayList<Matricula> arrayMatriculas = controlador.getMatriculas(cursoAcademico);

            if (arrayMatriculas.isEmpty()) {
                System.out.println("No existen matrículas para el curso académico indicado.");
                return;
            }

            arrayMatriculas.sort(Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                    .thenComparing(matricula -> matricula.getAlumno().getNombre(), String.CASE_INSENSITIVE_ORDER));

            System.out.println("Matrículas del curso académico " + cursoAcademico + ": ");
            for (Matricula matricula : arrayMatriculas) {
                System.out.println(matricula);
            }
        } catch (OperationNotSupportedException e) {
            System.out.println("ERROR: No se pudo mostrar matrículas por curso académico.");
        }
    }

}