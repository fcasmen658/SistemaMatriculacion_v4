package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Consola {
    private Consola() {
    }

    public static void mostrarMenu() {
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion.toString());
        }
    }

    public static Opcion elegirOpcion() {
        int opcion;
        do {
            System.out.print("\nElige una opción del menú: ");
            opcion = Entrada.entero();
        } while (!(opcion >= 0 && opcion <= Opcion.values().length-1));
        return Opcion.values()[opcion];
    }

    public static Alumno leerAlumno() {
        String nombre;
        String telefono;
        String correo;
        String dni;
        LocalDate fechaNacimiento;

        System.out.println(" - - Introduce los datos del alumno: - - ");
        do {
            System.out.print("\nIntroduce el nombre del alumno (Como mínimo 3 carácteres): ");
            nombre = Entrada.cadena();
        } while (nombre.isBlank() || nombre.length() < 3);

        do {
            System.out.print("Introduce el DNI del alumno: ");
            dni = Entrada.cadena();
        } while (dni.isBlank());

        do {
            System.out.print("Introduce el correo del alumno: ");
            correo = Entrada.cadena();
        } while (correo.isBlank());

        do {
            System.out.print("Introduce el teléfono del alumno: ");
            telefono = Entrada.cadena();
        } while (telefono.isBlank());

        String mensaje = "Introduce la fecha de nacimiento del alumno: ";
        fechaNacimiento = leerFecha(mensaje);

        return new Alumno(nombre, dni, correo, telefono, fechaNacimiento);

    }

    public static Alumno getAlumnoPorDni() {
        String nombre = "NombreFalso Apellido1 Apellido2";
        String telefono = "666010203";
        String correo = "pepe@wanadoo.com";
        String dni;
        LocalDate fechaNacimiento = LocalDate.of(1991, 12, 12);

        do {
            System.out.print("Introduce el DNI del alumno: ");
            dni = Entrada.cadena();
        } while (dni.isBlank());

        return new Alumno(nombre, dni, correo, telefono, fechaNacimiento);
    }

    public static LocalDate leerFecha(String mensaje) {
        do {
            try {
                System.out.printf(mensaje, Alumno.FORMATO_FECHA);
                return LocalDate.parse(Entrada.cadena(), DateTimeFormatter.ofPattern(Alumno.FORMATO_FECHA));
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: El formato de la fecha no es correcto.");
            }
        } while (true);
    }

    public static TiposGrado leerTiposGrado() {
        int seleccion;
        do {
            System.out.println("Introduce el código del tipo de grado: ");
            for (TiposGrado tiposGrado : TiposGrado.values()) {
                System.out.println(tiposGrado.imprimir());
            }
            seleccion = Entrada.entero();
        } while (seleccion < 0 || seleccion >= TiposGrado.values().length);
        return TiposGrado.values()[seleccion];
    }

    public static Modalidad leerModalidad() {
        int seleccion;
        do {
            System.out.println("Introduce el código de la modalidad: ");
            for (Modalidad modalidad : Modalidad.values()) {
                System.out.println(modalidad.imprimir());
            }
            seleccion = Entrada.entero();
        } while (seleccion < 0 || seleccion >= Modalidad.values().length);
        return Modalidad.values()[seleccion];
    }

    public static CicloFormativo leerCicloFormativo() {
        int codigo;
        String familiaProfesional;
        Grado grado;
        String nombre;
        int horas;
        do {
            ArrayList<CicloFormativo> cicloFormativos = new CiclosFormativos().get();
            System.out.println("Introduce el código del ciclo formativo (4 dígitos): ");
            codigo = Entrada.entero();
        } while (codigo < 1000 || codigo > 9999);

        do {
            System.out.println("Introduce la familia profesional del ciclo formativo: ");
            familiaProfesional = Entrada.cadena();
        } while (familiaProfesional.isBlank());

        grado = leerGrado();

        do {
            System.out.println("Introduce el nombre del ciclo formativo: ");
            nombre = Entrada.cadena();
        } while (nombre.isBlank());

        do {
            System.out.println("Introduce las horas del ciclo formativo: ");
            System.out.println("El maximo numero de horas es "+CicloFormativo.MAXIMO_NUMERO_HORAS);
            horas = Entrada.entero();
        } while (horas < 0);
        if (horas > CicloFormativo.MAXIMO_NUMERO_HORAS) {
            throw new IllegalArgumentException(
                    "ERROR: El numero de horas no puede ser mayor que "+CicloFormativo.MAXIMO_NUMERO_HORAS);
        }

        return new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
    }

    public static Grado leerGrado() {
        TiposGrado tg = leerTiposGrado();

        String nombreGrado;
        do {
            System.out.println("Introduce el nombre del grado:");
            nombreGrado = Entrada.cadena();
        } while (nombreGrado.isBlank());

        int numAnios;
        do {
            System.out.println("Introduzca el numero de años:");
            numAnios = Entrada.entero();
        } while ((tg == TiposGrado.GRADOD && (numAnios < 2 || numAnios > 3)
                || tg == TiposGrado.GRADOE && numAnios != 1));

        if (tg == TiposGrado.GRADOD) {
            Modalidad m = leerModalidad();
            return new GradoD(nombreGrado, numAnios, m);
        } else {
            int numEdiciones;
            do {
                System.out.println("Introduzca el numero de ediciones:");
                numEdiciones = Entrada.entero();
            } while (numEdiciones < 1);
            return new GradoE(nombreGrado, numAnios, numEdiciones);
        }
    }

    public static void mostrarCiclosFormativos(ArrayList<CicloFormativo> ciclosFormativos) {
        System.out.println("Lista de ciclos formativos disponibles:");
        if (ciclosFormativos.size() == 0) {
            System.out.println("No hay ciclos formativos disponibles.");
        } else {
            for (CicloFormativo cicloFormativo : ciclosFormativos) {
                if (cicloFormativo != null) {
                    System.out.println(cicloFormativo);
                }
            }
        }
    }

    public static CicloFormativo getCicloFormativoPorCodigo() {
        int codigo;
        String familiaProfesional = "ficticia";
        Grado grado = new GradoD("grado ficticio", 2, Modalidad.PRESENCIAL);
        String nombre = "ficticio";
        int horas = 1;
        do {

            System.out.println("Introduce el código del ciclo formativo: ");
            codigo = Entrada.entero();
        } while (codigo < 0);
        return new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
    }

    public static Curso leerCurso() {
        int seleccion;
        do {
            System.out.println("Introduce el curso de la asignatura: ");
            for (Curso curso : Curso.values()) {
                System.out.println(curso.imprimir());
            }
            seleccion = Entrada.entero();
        } while (seleccion < 0 || seleccion >= Curso.values().length);
        return Curso.values()[seleccion];
    }

    public static EspecialidadProfesorado leerEspecialidadProfesorado() {
        int seleccion;
        do {
            System.out.println("Introduce la especialidad del profesorado de la asignatura: ");
            for (EspecialidadProfesorado especialidadProfesorado : EspecialidadProfesorado.values()) {
                System.out.println(especialidadProfesorado.imprimir());
            }
            seleccion = Entrada.entero();
        } while (seleccion < 0 || seleccion >= EspecialidadProfesorado.values().length);
        return EspecialidadProfesorado.values()[seleccion];
    }

    public static Asignatura leerAsignatura(CicloFormativo cicloFormativo) {
        String codigo;
        String nombre;
        int horasAnuales;
        Curso curso;
        int horasDesdoble;
        EspecialidadProfesorado especialidadProfesorado;
        do {
            ArrayList<Asignatura> asignaturas = new Asignaturas().get();
            System.out.println("Introduce el código de la asignatura: ");
            System.out.println("El código debe tener un formato de 4 dígitos.");
            codigo = Entrada.cadena();
        } while (codigo.isBlank());

        do {
            System.out.println("Introduce el nombre de la asignatura: ");
            nombre = Entrada.cadena();
        } while (nombre.isBlank());

        do {
            System.out.println("Introduce las horas anuales de la asignatura: ");
            System.out.println("El numero de horas tiene que ser entre 0 y 300.");
            horasAnuales = Entrada.entero();
        } while (horasAnuales < 0);

        curso = leerCurso();

        do {
            System.out.println("\nIntroduce las horas desdoble de la asignatura: ");
            System.out.println("El numero de horas de desdoble tiene que ser entre 0 y 6");
            horasDesdoble = Entrada.entero();
        } while (horasDesdoble < 0);

        especialidadProfesorado = leerEspecialidadProfesorado();

        return new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidadProfesorado,
                cicloFormativo);
    }

    public static Asignatura getAsignaturaPorCodigo() {
        String codigo;
        String nombre = "Asignaturaficticia";
        int horasAnuales = 13;
        Curso curso = Curso.PRIMERO;
        int horasDesdoble = 5;
        Grado grado = new GradoD("grado ficticio", 2, Modalidad.PRESENCIAL);
        EspecialidadProfesorado especialidadProfesorado = EspecialidadProfesorado.SISTEMAS;
        CicloFormativo cicloFormativo = new CicloFormativo(9999, "ficticio", grado, "ficticio", 200);
        Asignatura asignatura;
        do {
            System.out.println("\nIntroduce el codigo de la asignatura: ");
            codigo = Entrada.cadena();
        } while (codigo == null || codigo.isBlank());
        asignatura = new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidadProfesorado,
                cicloFormativo);

        return new Asignatura(asignatura);
    }

    private static void mostrarAsignaturas(ArrayList<Asignatura> asignaturas) {
        System.out.println("Listado de Asignaturas disponibles:");
        for (Asignatura asignatura : asignaturas) {
            if (asignatura != null) {
                System.out.println(asignatura);
            }
        }
    }

    private static boolean asignaturaYaMatriculada(ArrayList<Asignatura> asignaturasMatricula, Asignatura asignatura) {

        if (asignaturasMatricula != null) {
            for (Asignatura value : asignaturasMatricula) {
                if (value != null && value.equals(asignatura)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Matricula leerMatricula(Alumno alumno, ArrayList<Asignatura> asignaturas)
            throws OperationNotSupportedException {

        if (alumno == null) {
            throw new NullPointerException("ERROR: El alumno de una matrícula no puede ser nulo.");
        }
        if (asignaturas == null || asignaturas.size() == 0) {
            throw new NullPointerException("ERROR: Las asignaturas de una matrícula no pueden ser nulas.");
        }

        int idMatricula;
        String cursoAcademico;
        LocalDate fechaMatriculacion;
        Matricula matricula;

        System.out.println("Introduzca el Id de la Matrícula.");
        idMatricula = Entrada.entero();

        System.out.println("Introduzca el Curso Académico.");
        System.out.println("El curso académico tiene que tener el formato 24-25");
        cursoAcademico = Entrada.cadena();

        String mensaje = "Introduzca la Fecha de matriculación.";

        System.out.println("La fecha de matriculación como maximo puede ser de 15 días anterior al día actual");
        fechaMatriculacion = leerFecha(mensaje);

        matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturas);

        return matricula;
    }

    public static ArrayList<Asignatura> elegirAsignaturasMatricula(ArrayList<Asignatura> asignaturas)
            throws OperationNotSupportedException {

        if (asignaturas == null || asignaturas.size() == 0) {
            throw new IllegalArgumentException("ERROR: No hay asignaturas disponibles para seleccionar.");
        }

        ArrayList<Asignatura> asignaturasMatricula = new ArrayList<>();
        int opcion = 0;

        do {
            mostrarAsignaturas(asignaturas);

            System.out.print("Introduzca el código de la asignatura: ");
            String codigo = Entrada.cadena();
            Asignatura asignatura = null;

            for (Asignatura a : asignaturas) {
                if (a != null && a.getCodigo().equals(codigo)) {
                    asignatura = a;
                    break;
                }
            }

            if (asignatura == null) {
                System.out.println("ERROR: La asignatura seleccionada no es válida.");
                continue;
            }

            if (asignaturaYaMatriculada(asignaturasMatricula, asignatura)) {
                System.out.println("La asignatura ya está seleccionada.");
            } else {
                asignaturasMatricula.add(asignatura);
                System.out.println("Asignatura añadida correctamente.");
            }

            System.out.print("¿Desea añadir otra asignatura? (0 = No, 1 = Sí): ");
            opcion = Entrada.entero();
        } while (opcion == 1 && asignaturasMatricula.size() < asignaturas.size());

        return asignaturasMatricula;
    }

    public static Matricula getMatriculaPorIdentificador() throws OperationNotSupportedException {
        int idMatricula;
        String cursoAcademico = "24-25";
        LocalDate fechaMatriculacion = LocalDate.now();
        Alumno alumno = new Alumno("Nombre Apellido1 Apellido2", "00000000T", "pepe@wanadoo.com", "666010203",
                LocalDate.of(2000, 8, 21));

        System.out.println("Introduzca el id de la Matrícula.");
        idMatricula = Entrada.entero();
        return new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, new Asignaturas().get());

    }

}