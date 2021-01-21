package it.unicam.cs.ids.C3.TeamMGC.personale;

import java.sql.SQLException;

public class Commesso extends Personale {

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID dell' Addetto Magazzino
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public Commesso(int ID) throws SQLException {
        super(ID);
    }

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public Commesso(int IDNegozio, String nome, String cognome) throws SQLException {
        super(Ruolo.COMMESSO, IDNegozio, nome, cognome);
    }

}