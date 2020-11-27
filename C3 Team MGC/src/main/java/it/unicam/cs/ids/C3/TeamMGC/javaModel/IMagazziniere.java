package it.unicam.cs.ids.C3.TeamMGC.javaModel;

public interface IMagazziniere {


	void getOrdini(String idCliente);

	void getID(int ordine);

	void getMerceMagazzino(String idOrdine);

	void selezionaStatoMerce(StatoOrdine RITIRATO);

	void getMerceTotale(String idOrdine);

	void selezionaStatoOrdine(StatoOrdine RITIRATO);

}