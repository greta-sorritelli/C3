package it.unicam.cs.ids.C3.TeamMGC.personale;

import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;

import java.sql.SQLException;

/**
 * La classe estende la classe astratta {@link Personale} ed ha la responsabilità di gestire un Addetto Magazzino
 * del {@link Negozio}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class AddettoMagazzinoNegozio extends Personale {

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID dell' Addetto Magazzino
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public AddettoMagazzinoNegozio(int ID) throws SQLException {
        super(ID);
    }

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @param IDNegozio ID del {@link Negozio}
     * @param nome      Nome dell' Addetto Magazzino
     * @param cognome   Cognome dell' Addetto Magazzino
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public AddettoMagazzinoNegozio(int IDNegozio, String nome, String cognome) throws SQLException {
        super(Ruolo.ADDETTO_MAGAZZINO, IDNegozio, nome, cognome);
    }
}