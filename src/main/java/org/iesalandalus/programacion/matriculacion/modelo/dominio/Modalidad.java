package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum Modalidad {
	
	PRESENCIAL("Presencial"),
	SEMIPRESENCIAL("Semipresencial");
	
	private final String cadenaAMostrar;
	
	Modalidad(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }
	
	public String imprimir() {
		int digito = 0;
		if (cadenaAMostrar.equals(PRESENCIAL.cadenaAMostrar)) {
        } else if (cadenaAMostrar.equals(SEMIPRESENCIAL.cadenaAMostrar)) {
			digito = 1;
		} else {
			digito = 2;
		}
		return digito + ".-" + cadenaAMostrar;
	}
	
	public String toString() {
		return "Modalidad seleccionada: " + cadenaAMostrar;
	}

}