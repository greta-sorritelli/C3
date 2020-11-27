package it.unicam.cs.ids.C3.TeamMGC.javaModel;

public enum StatoOrdine {
	PAGATO("Pagato"),
	AFFIDATOALCORRIERE("Affidato al corriere"),
	INTRANSITO("In transito"),
	INDEPOSITO("In deposito"),
	RITIRATO("Ritirato");

	StatoOrdine(String pagato) {
	}
}