package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public abstract class Grado {

    protected String nombre;
    protected String iniciales;
    protected int numAnios;

    public Grado(String nombre) {
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    protected void setNombre(String nombre) {
        if (nombre == null) {
            throw new NullPointerException("ERROR: El nombre del Grado no puede ser nulo.");
        }
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR: El nombre del Grado no puede estar vacío.");
        }
        this.nombre = nombre;
        setIniciales();
    }

    private void setIniciales() {
        String[] palabras = nombre.split("[ ]+");
        String s = "";
        for (String palabra : palabras) {
            s += palabra.charAt(0);
        }
        this.iniciales = s.toUpperCase();
    }

    public abstract void setNumAnios(int numAnios);

    public String toString() {
        return "(" + iniciales + ") - " + nombre;
    }

}