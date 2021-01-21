package it.unicam.cs.ids.C3.TeamMGC.personale;

import java.sql.SQLException;

public class Commerciante extends Personale {

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID del Commerciante
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public Commerciante(int ID) throws SQLException {
        super(ID);
    }

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public Commerciante(int IDNegozio, String nome, String cognome) throws SQLException {
        super(Ruolo.COMMERCIANTE, IDNegozio, nome, cognome);
    }

}