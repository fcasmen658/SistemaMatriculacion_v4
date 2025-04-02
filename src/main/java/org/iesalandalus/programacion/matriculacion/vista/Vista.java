package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.time.LocalDate;
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
        } catch (OperationNotSupportedException | IllegalArgumentException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarAlumno() {
        try {
            Alumno alumno = controlador.buscar(Consola.getAlumnoPorDni());
            controlador.buscar(alumno);
            System.out.printf("Los datos del alumno solicitado son: %s", alumno + "\n\n");
        } catch (NullPointerException | IllegalArgumentException | SQLException | OperationNotSupportedException e) {
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
        ArrayList<Alumno> arrayAlumnos = null;
        try {
            arrayAlumnos = controlador.getAlumnos();
        } catch (SQLException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }

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
            CicloFormativo ciclo = null;
            try {
                ciclo = controlador.buscar(cicloFormativo);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            if (ciclo == null) {
                System.out.println("No existe el ciclo formativo indicado.");
                return;
            }
            Asignatura asignatura = Consola.leerAsignatura(ciclo);
            try {
                controlador.insertar(asignatura);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Asignatura insertada correctamente.");
        } catch (IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede insertar una Asignatura nula.");
        }
    }

    public void buscarAsignatura() {
        try {
            Asignatura asignaturaBuscar = null;
            try {
                asignaturaBuscar = controlador.buscar(Consola.getAsignaturaPorCodigo());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            Asignatura encontrada = null;
            try {
                encontrada = controlador.buscar(asignaturaBuscar);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            if (encontrada != null) {
                System.out.printf("Los datos de la asignatura solicitada son: %s", asignaturaBuscar);
            } else {
                System.out.println("No existe ninguna asignatura con tales datos.");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede buscar una asignatura nula.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void borrarAsignatura() {
        try {
            Asignatura asignaturaBorrar = Consola.getAsignaturaPorCodigo();
            try {
                controlador.borrar(asignaturaBorrar);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Asignatura borrada correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede borrar una asignatura nula.");
        } catch (IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarAsignaturas() {
        ArrayList<Asignatura> arrayAsignatura = null;
        try {
            arrayAsignatura = controlador.getAsignaturas();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (arrayAsignatura == null || arrayAsignatura.isEmpty()) {
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
        } catch (OperationNotSupportedException | IllegalArgumentException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarCicloFormativo() {
        try {
            CicloFormativo cicloFormativoBuscar = null;
            try {
                cicloFormativoBuscar = controlador.buscar(Consola.getCicloFormativoPorCodigo());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            CicloFormativo encontrada = null;
            try {
                encontrada = controlador.buscar(cicloFormativoBuscar);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            if (encontrada != null) {
                System.out.printf("Los datos del ciclo formativo solicitado son: %s", cicloFormativoBuscar);
            } else {
                System.out.println("No existe ningún ciclo formativo con tales datos.");
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
            try {
                controlador.borrar(cicloFormativoBorrar);
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
            System.out.println("Ciclo formativo borrada correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede borrar un ciclo formativo nulo.");
        } catch (IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarCiclosFormativos() {
        ArrayList<CicloFormativo> arrayCicloFormativo = null;
        try {
            arrayCicloFormativo = controlador.getCicloFormativos();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
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
        } catch (OperationNotSupportedException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarMatricula() {
        try {
            Matricula matriculaBuscar = null;
            try {
                matriculaBuscar = controlador.buscar(Consola.getMatriculaPorIdentificador());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            Matricula encontrada = null;
            try {
                encontrada = controlador.buscar(matriculaBuscar);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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
            Matricula matriculaAnular = null;
            try {
                matriculaAnular = controlador.buscar(Consola.getMatriculaPorIdentificador());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            if (matriculaAnular != null && matriculaAnular.getAlumno().equals(alumno)) {
                LocalDate fechaAnular = Consola.leerFecha("Indique la fecha de anulación:");
                matriculaAnular.setFechaAnulacion(fechaAnular);
                System.out.println("Matricula anulada correctamente.");
            } else {
                System.out.println("No se ha encontrado la matricula o no corresponde al alumno indicado.");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede anular una matricula nula.");
        } catch (OperationNotSupportedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarMatriculas() {
        try {
            ArrayList<Matricula> arrayMatriculas = null;
            try {
                arrayMatriculas = controlador.getMatriculas();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

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
            ArrayList<Matricula> arrayMatricula = null;
            try {
                arrayMatricula = controlador.getMatriculas(alumno);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

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
            try {
                cicloFormativo = controlador.buscar(cicloFormativo);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            if (cicloFormativo == null) {
                System.out.println("No existe ningún ciclo formativo con tales datos.");
                return;
            }

            ArrayList<Matricula> matriculaCiclo = null;
            try {
                matriculaCiclo = controlador.getMatriculas(cicloFormativo);
            } catch (OperationNotSupportedException | SQLException e) {
                System.out.println(e.getMessage());
            }

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

            ArrayList<Matricula> arrayMatriculas = null;
            try {
                arrayMatriculas = controlador.getMatriculas(cursoAcademico);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

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