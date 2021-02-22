package it.unicam.cs.ids.C3.TeamMGC.personale;

import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;

import java.sql.SQLException;

/**
 * La classe estende la classe astratta {@link Personale} ed ha la responsabilità di gestire un Commesso.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class Commesso extends Personale {

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID del Commesso
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public Commesso(int ID) throws SQLException {
        super(ID);
    }

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @param IDNegozio ID del {@link Negozio}
     * @param nome      Nome del Commesso
     * @param cognome   Cognome del Commesso
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public Commesso(int IDNegozio, String nome, String cognome) throws SQLException {
        super(Ruolo.COMMESSO, IDNegozio, nome, cognome);
    }
}