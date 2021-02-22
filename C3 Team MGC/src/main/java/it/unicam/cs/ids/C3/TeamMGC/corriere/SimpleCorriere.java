package it.unicam.cs.ids.C3.TeamMGC.corriere;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * La classe implementa l' interfaccia {@link Corriere} ed ha la responsabilità di gestire un Corriere.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class SimpleCorriere implements Corriere {
    private final int ID;
    private String nome;
    private String cognome;
    private boolean disponibilita;

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID del Corriere
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleCorriere(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from corrieri where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            this.disponibilita = rs.getBoolean("stato");
            disconnectToDB(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @param nome          Nome del Corriere
     * @param cognome       Cognome del Corriere
     * @param disponibilita Stato di disponibilità del Corriere
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleCorriere(String nome, String cognome, boolean disponibilita) throws SQLException {
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato) VALUES ('" + nome + "', '" + cognome +
                "', '" + disponibilita + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from corrieri;");
        rs.next();
        ID = rs.getInt("ID");
        this.nome = nome;
        this.cognome = cognome;
        this.disponibilita = disponibilita;
        disconnectToDB(rs);
    }

    /**
     * Confronta 2 oggetti di tipo {@link Corriere} attraverso il loro {@code ID}.
     *
     * @param c Oggetto da confrontare
     *
     * @return <ul><li>0 se i due oggetti sono uguali,</li>
     * <li>1 se questo oggetto ha l'ID maggiore,</li>
     * <li>-1 se c ha l'ID maggiore.</li></ul>
     */
    @Override
    public int compareTo(@NonNull Corriere c) {
        if (this.equals(c))
            return 0;
        if (this.getID() > c.getID())
            return 1;
        else return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corriere simpleCorriere = (SimpleCorriere) o;
        return getID() == simpleCorriere.getID();
    }

    /**
     * Ritorna il Cognome del {@link SimpleCorriere}.
     *
     * @return il Cognome
     */
    @Override
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il nuovo Cognome del {@link SimpleCorriere}.
     *
     * @param cognome Nuovo cognome
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    @Override
    public void setCognome(String cognome) throws SQLException {
        updateData("UPDATE sys.corrieri SET cognome = '" + cognome + "' WHERE (ID = '" + this.ID + "');");
        this.cognome = cognome;
    }

    /**
     * Ritorna un arraylist con i dettagli del {@link SimpleCorriere}.
     *
     * @return ArrayList dei dettagli
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    @Override
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> corriere = new ArrayList<>();
        corriere.add(String.valueOf(ID));
        corriere.add(nome);
        corriere.add(cognome);
        corriere.add(String.valueOf(getDisponibilita()));
        return corriere;
    }

    /**
     * Ritorna la Disponibilità del {@link SimpleCorriere}.
     *
     * @return lo stato del corriere
     */
    @Override
    public boolean getDisponibilita() {
        return disponibilita;
    }

    /**
     * Imposta la nuova Disponibilità del {@link SimpleCorriere}.
     *
     * @param disponibilita Nuovo valore della disponibilità
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    @Override
    public void setDisponibilita(boolean disponibilita) throws SQLException {
        updateData("UPDATE sys.corrieri SET stato = '" + disponibilita + "' WHERE (ID = '" + this.ID + "');");
        this.disponibilita = disponibilita;
    }

    /**
     * Ritorna il Codice Identificativo del {@link SimpleCorriere}.
     *
     * @return il Codice Identificativo
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Ritorna il Nome del {@link SimpleCorriere}.
     *
     * @return il Nome
     */
    @Override
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nuovo Nome del {@link SimpleCorriere}.
     *
     * @param nome Nuovo nome
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    @Override
    public void setNome(String nome) throws SQLException {
        updateData("UPDATE sys.corrieri SET nome = '" + nome + "' WHERE (ID = '" + this.ID + "');");
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", disponibilita=" + disponibilita;
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.corrieri where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            this.disponibilita = rs.getBoolean("stato");
        }
        disconnectToDB(rs);
    }
}