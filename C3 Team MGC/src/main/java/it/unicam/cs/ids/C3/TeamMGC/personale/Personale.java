package it.unicam.cs.ids.C3.TeamMGC.personale;

public abstract class Personale {

    private int ID;
    private int IDNegozio;
    private String nome;
    private String cognome;

    public Personale( int IDNegozio, String nome, String cognome) {
        this.IDNegozio = IDNegozio;
        this.nome = nome;
        this.cognome = cognome;
    }

}
