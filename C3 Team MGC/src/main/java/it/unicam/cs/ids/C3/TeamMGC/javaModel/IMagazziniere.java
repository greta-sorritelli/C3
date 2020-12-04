package it.unicam.cs.ids.C3.TeamMGC.javaModel;

public interface IMagazziniere {

	/**
	 * 
	 * @param IDCliente
	 */
	void cercaCliente(int IDCliente);

	/**
	 * 
	 * @param codiceRitiro
	 */
	void verificaCodice(int codiceRitiro);

}