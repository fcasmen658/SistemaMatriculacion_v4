package org.iesalandalus.programacion.matriculacion;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

public class MainApp {

    public static void main(String[] args) {

        Modelo modelo = procesarArgumentosFuenteDatos(args);
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);
        controlador.comenzar();
        controlador.terminar();
    }

    private static Modelo procesarArgumentosFuenteDatos(String[] args) {
        if (args.length == 0) {
            System.out.println("Fuente de datos por defecto: memoria.");
            return new Modelo(FactoriaFuenteDatos.MEMORIA);
        }

        switch (args[0].toLowerCase()) {
            case "-fdmysql":
                System.out.println("----------------------------------------");
                System.out.println("Fuente de datos seleccionada: MySQL.");
                System.out.println("----------------------------------------");
                return new Modelo(FactoriaFuenteDatos.MYSQL);
            case "-fdmemoria":
                System.out.println("----------------------------------------");
                System.out.println("Fuente de datos seleccionada: Memoria.");
                System.out.println("-----------------------------------------");
                System.out.println("Iniciando sistema de matriculación en memoria...");
                return new Modelo(FactoriaFuenteDatos.MEMORIA);
            default:
                System.out.println("----------------------------------------------------------");
                System.out.println("Fuente de datos no reconocida. Usando memoria por defecto.");
                System.out.println("----------------------------------------------------------");
                return new Modelo(FactoriaFuenteDatos.MEMORIA);
        }
    }

}