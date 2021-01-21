package it.unicam.cs.ids.C3.TeamMGC.personale;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.disconnectToDB;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class GestorePersonale implements Gestore<Personale> {

    private final int IDNegozio;
    ArrayList<Personale> personale = new ArrayList<>();

    public GestorePersonale(int IDNegozio) {
        this.IDNegozio = IDNegozio;
    }

    /**
     * Controlla se il {@link Personale Lavoratore} che si vuole creare e' gia' presente nella lista del personale.
     * Se non e' presente viene creato e aggiunto alla lista.
     *
     * @return Il lavoratore
     *
     * @throws SQLException Errore causato da una query SQL
     */
    private Personale addPersonale(ResultSet rs) throws SQLException {
        for (Personale p : personale)
            if (p.getID() == rs.getInt("ID"))
                return p;
        Personale tmp;
        switch (Ruolo.valueOf(rs.getString("ruolo"))) {
            case COMMESSO:
                tmp = new Commesso(rs.getInt("ID"));
                break;
            case COMMERCIANTE:
                tmp = new Commerciante(rs.getInt("ID"));
                break;
            case ADDETTO_MAGAZZINO:
                tmp = new AddettoMagazzinoNegozio(rs.getInt("ID"));
                break;
            default:
                throw new IllegalArgumentException("ID non valido.");
        }
        addPersonaleToList(tmp);
        return tmp;
    }

    /**
     * Aggiunge un  alla lista del personale.
     *
     * @param p Personale da aggiungere
     */
    private void addPersonaleToList(Personale p) {
        if (!personale.contains(p))
            personale.add(p);
    }

    /**
     * Ritorna la lista dei dettagli dei {@link Personale lavoratori} presenti nel DB.
     *
     * @return ArrayList di ArrayList dei dettagli dei lavoratori.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.personale where IDNegozio='" + IDNegozio + "';");
        while (rs.next())
            addPersonale(rs);
        for (Personale p : personale)
            dettagli.add(p.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    /**
     * Ritorna il {@link Personale lavoratore} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del personale
     *
     * @return Il lavoratore desiderato
     */
    @Override
    public Personale getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.personale where ID='" + ID + "' and IDNegozio = '" +
                this.IDNegozio + "' ;");
        if (rs.next()) {
            Personale personale = addPersonale(rs);
            disconnectToDB(rs);
            return personale;
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Ritorna la lista dei {@link Personale lavoratori} di un negozio presenti nel DB.
     *
     * @return ArrayList del personale.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<Personale> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.personale where IDNegozio='" + IDNegozio + "';");
        while (rs.next())
            addPersonale(rs);
        disconnectToDB(rs);
        return new ArrayList<>(personale);
    }

    /**
     * Ritorna la lista dei {@link Commesso commessi} di un negozio presenti nel DB.
     *
     * @return ArrayList dei commessi.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<Personale> getCommessi() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.personale where IDNegozio='" + IDNegozio + "' and ruolo='" + Ruolo.COMMESSO + "';");
        while (rs.next())
            addPersonale(rs);
        disconnectToDB(rs);
        return personale.stream().filter(commesso -> commesso.getRuolo().equals(Ruolo.COMMESSO)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Ritorna il {@link Commerciante} di un negozio presente nel DB.
     *
     * @return Il commerciante.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public Personale getCommerciante() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.personale where IDNegozio='" + IDNegozio + "' and ruolo='" + Ruolo.COMMERCIANTE + "';");
        Personale tmp = null;
        if (rs.next())
            tmp = addPersonale(rs);
        disconnectToDB(rs);
        return tmp;
    }

    /**
     * Ritorna la lista degli {@link AddettoMagazzinoNegozio addetti} di un negozio presenti nel DB.
     *
     * @return ArrayList degli addetti.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<Personale> getAddetti() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.personale where IDNegozio='" + IDNegozio + "' and ruolo='" + Ruolo.ADDETTO_MAGAZZINO + "';");
        while (rs.next())
            addPersonale(rs);
        disconnectToDB(rs);
        return personale.stream().filter(commesso -> commesso.getRuolo().equals(Ruolo.ADDETTO_MAGAZZINO)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Crea e inserisce un nuovo {@link AddettoMagazzinoNegozio} nella lista.
     *
     * @param nome    Nome dell' Addetto da inserire
     * @param cognome Cognome dell' Addetto da inserire
     *
     * @return ArrayList dei dettagli dell' Addetto creato
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> inserisciAddetto(int IDNegozio, String nome, String cognome) throws SQLException {
        Personale addetto = new AddettoMagazzinoNegozio(IDNegozio, nome, cognome);
        addPersonaleToList(addetto);
        return addetto.getDettagli();
    }

    /**
     * Crea e inserisce un nuovo {@link Commerciante} nella lista.
     *
     * @param nome    Nome del Commerciante da inserire
     * @param cognome Cognome del Commerciante da inserire
     *
     * @return ArrayList dei dettagli del Commerciante creato
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> inserisciCommerciante(int IDNegozio, String nome, String cognome) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.personale where IDNegozio='" + IDNegozio + "' and ruolo='" + Ruolo.COMMERCIANTE + "';");
        if (rs.next())
            throw new IllegalArgumentException("Commerciante già presente.");
        Personale commerciante = new Commerciante(IDNegozio, nome, cognome);
        addPersonaleToList(commerciante);
        return commerciante.getDettagli();
    }

    /**
     * Crea e inserisce un nuovo {@link Commesso} nella lista.
     *
     * @param nome    Nome del commesso da inserire
     * @param cognome Cognome del commesso da inserire
     *
     * @return ArrayList dei dettagli del commesso creato
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> inserisciCommesso(int IDNegozio, String nome, String cognome) throws SQLException {
        Personale commesso = new Commesso(IDNegozio, nome, cognome);
        addPersonaleToList(commesso);
        return commesso.getDettagli();
    }
}
