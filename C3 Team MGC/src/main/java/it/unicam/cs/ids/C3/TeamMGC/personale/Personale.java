package it.unicam.cs.ids.C3.TeamMGC.personale;

import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Questa classe astratta gestisce il comportamento del Personale di un {@link Negozio}.
 * Permette di accedere e modificare le informazioni associate al personale.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public abstract class Personale implements Comparable<Personale> {
    private final int ID;
    private Ruolo ruolo;
    private int IDNegozio;
    private String nome;
    private String cognome;

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID del Commesso
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public Personale(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from personale where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.ruolo = Ruolo.valueOf(rs.getString("ruolo"));
            this.IDNegozio = rs.getInt("IDNegozio");
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            disconnectToDB(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @param ruolo     {@link Ruolo} del Personale
     * @param IDNegozio ID del {@link Negozio}
     * @param nome      Nome del Personale
     * @param cognome   Cognome del Personale
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public Personale(Ruolo ruolo, int IDNegozio, String nome, String cognome) throws SQLException {
        updateData("INSERT INTO sys.personale (ruolo, IDNegozio, nome, cognome) VALUES ('" + ruolo + "','" + IDNegozio + "','" + nome + "', '" + cognome + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from personale;");
        rs.next();
        ID = rs.getInt("ID");
        this.ruolo = ruolo;
        this.IDNegozio = IDNegozio;
        this.nome = nome;
        this.cognome = cognome;
    }

    /**
     * Confronta 2 oggetti di tipo {@link Personale} attraverso il loro {@code ID}.
     *
     * @param p Oggetto da confrontare
     *
     * @return <ul><li>0 se i due oggetti sono uguali,</li>
     * <li>1 se questo oggetto ha l'ID maggiore,</li>
     * <li>-1 se p ha l'ID maggiore.</li></ul>
     */
    @Override
    public int compareTo(@NonNull Personale p) {
        if (this.equals(p))
            return 0;
        if (this.getID() > p.getID())
            return 1;
        else return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personale personale = (Personale) o;
        return ID == personale.ID;
    }

    /**
     * Ritorna il Cognome del {@link Personale}.
     *
     * @return il cognome del Personale
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Ritorna un arraylist con i dettagli del {@link Personale}.
     *
     * @return ArrayList dei dettagli
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(String.valueOf(getID()));
        toReturn.add(String.valueOf(getRuolo()));
        toReturn.add(String.valueOf(getIDNegozio()));
        toReturn.add(getNome());
        toReturn.add(getCognome());
        return toReturn;
    }

    /**
     * Ritorna il Codice Identificativo del {@link Personale}.
     *
     * @return il Codice Identificativo
     */
    public int getID() {
        return ID;
    }

    /**
     * Ritorna il Codice Identificativo del {@link Negozio} del {@link Personale}.
     *
     * @return il Codice Identificativo del Negozio
     */
    public int getIDNegozio() {
        return IDNegozio;
    }

    /**
     * Imposta il Codice Identificativo del {@link Negozio}.
     *
     * @param IDNegozio ID del Negozio
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void setIDNegozio(int IDNegozio) throws SQLException {
        updateData("UPDATE sys.personale SET IDNegozio = '" + IDNegozio + "' WHERE (ID = '" + this.ID + "');");
        this.IDNegozio = IDNegozio;
    }

    /**
     * Ritorna il Nome del {@link Personale}.
     *
     * @return il nome del Personale
     */
    public String getNome() {
        return nome;
    }

    /**
     * Ritorna il {@link Ruolo} del {@link Personale}.
     *
     * @return il ruolo del Personale
     */
    public Ruolo getRuolo() {
        return ruolo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", ruolo=" + ruolo +
                ", IDNegozio=" + IDNegozio +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'';
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.personale where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.ruolo = Ruolo.valueOf(rs.getString("ruolo"));
            this.IDNegozio = rs.getInt("IDNegozio");
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
        }
        disconnectToDB(rs);
    }
}