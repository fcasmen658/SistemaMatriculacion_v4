package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum TiposGrado {
	
	GRADOD("Grado D"),
	GRADOE("Grado E");
	
	private final String cadenaAMostrar;
	
	TiposGrado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }
	
	public String imprimir() {
		int digito = 0;
		if (cadenaAMostrar.equals(GRADOD.cadenaAMostrar)) {
        } else if (cadenaAMostrar.equals(GRADOE.cadenaAMostrar)) {
			digito = 1;
		} else {
			digito = 2;
		}
		return digito + ".-" + cadenaAMostrar;
	}
	
	public String toString() {
		return "Grado seleccionado: " + cadenaAMostrar;
	}

}