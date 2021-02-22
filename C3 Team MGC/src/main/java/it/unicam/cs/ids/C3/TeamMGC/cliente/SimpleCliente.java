package it.unicam.cs.ids.C3.TeamMGC.cliente;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * La classe implementa l' interfaccia {@link Cliente} ed ha la responsabilità di gestire un Cliente.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class SimpleCliente implements Cliente {
    private final int ID;
    private String nome;
    private String cognome;
    private String codiceRitiro = "";
    private String dataCreazioneCodice = "";

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID del Cliente
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleCliente(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from clienti where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            this.codiceRitiro = rs.getString("codiceRitiro");
            this.dataCreazioneCodice = rs.getString("dataCreazione");
            disconnectToDB(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID cliente non valido.");
        }
    }

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @param nome    Nome del Cliente
     * @param cognome Cognome del Cliente
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleCliente(String nome, String cognome) throws SQLException {
        updateData("INSERT INTO sys.clienti (nome, cognome) VALUES ('" + nome + "', '" + cognome + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from clienti;");
        rs.next();
        ID = rs.getInt("ID");
        this.nome = nome;
        this.cognome = cognome;
        disconnectToDB(rs);
    }

    /**
     * Confronta 2 oggetti di tipo {@link Cliente} attraverso il loro {@code ID}.
     *
     * @param c Oggetto da confrontare
     *
     * @return <ul><li>0 se i due oggetti sono uguali,</li>
     * <li>1 se questo oggetto ha l'ID maggiore,</li>
     * <li>-1 se o ha l'ID maggiore.</li></ul>
     */
    @Override
    public int compareTo(@NonNull Cliente c) {
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
        Cliente simpleCliente = (SimpleCliente) o;
        return getID() == simpleCliente.getID();
    }

    /**
     * Ritorna il Codice di Ritiro collegato al {@link SimpleCliente}.
     *
     * @return il Codice di Ritiro
     */
    @Override
    public String getCodiceRitiro() {
        return codiceRitiro;
    }

    /**
     * Ritorna il Cognome del {@link SimpleCliente}.
     *
     * @return il Cognome
     */
    @Override
    public String getCognome() {
        return cognome;
    }

    /**
     * Ritorna la Data di Creazione del {@code Codice di Ritiro} collegato al {@link SimpleCliente}.
     *
     * @return la data di creazione del Codice
     */
    @Override
    public String getDataCreazioneCodice() {
        return dataCreazioneCodice;
    }

    /**
     * Ritorna un arraylist con i dettagli del {@link SimpleCliente}.
     *
     * @return ArrayList dei dettagli
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(String.valueOf(getID()));
        toReturn.add(getNome());
        toReturn.add(getCognome());
        toReturn.add(getCodiceRitiro());
        toReturn.add(getDataCreazioneCodice());
        return toReturn;
    }

    /**
     * Ritorna il Codice Identificativo del {@link SimpleCliente}.
     *
     * @return il Codice Identificativo
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Ritorna il Nome del {@link SimpleCliente}.
     *
     * @return il Nome
     */
    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    /**
     * Crea un nuovo codice di ritiro e lo associa alla data in cui il {@link SimpleCliente} effettua un acquisto.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void setCodiceRitiro(String codiceRitiro) throws SQLException {
        dataCreazioneCodice = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
        this.codiceRitiro = codiceRitiro;
        updateData("UPDATE sys.clienti SET codiceRitiro = '" + codiceRitiro + "', dataCreazione = '" + dataCreazioneCodice + "' WHERE (ID = '" + this.ID + "');");
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.clienti where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            if (Objects.isNull(rs.getString("codiceRitiro"))) {
                this.codiceRitiro = "";
                this.dataCreazioneCodice = "";
            } else {
                this.codiceRitiro = rs.getString("codiceRitiro");
                this.dataCreazioneCodice = rs.getString("dataCreazione");
            }
        }
        disconnectToDB(rs);
    }
}