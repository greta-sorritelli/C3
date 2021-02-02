package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleMerce;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la creazione di una {@link SimpleMerce} all' interno di un {@link SimpleOrdine}.
 * L' oggetto diventa di tipo {@link SimpleMerceOrdine}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class SimpleMerceOrdine implements MerceOrdine {
    private final int ID;
    private int IDOrdine;
    private double prezzo;
    private String descrizione;
    private int quantita = 0;
    private StatoOrdine stato;
    private int IDCorriere = -1;

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @param prezzo      Prezzo della Merce
     * @param descrizione Descrizione della Merce
     * @param stato       {@link StatoOrdine Stato} della Merce
     * @param IDOrdine    ID dell' Ordine
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    public SimpleMerceOrdine(double prezzo, String descrizione, StatoOrdine stato, int IDOrdine) throws SQLException {
        updateData("INSERT INTO sys.merci (prezzo, descrizione, stato, IDOrdine) " +
                "VALUES ('" + prezzo + "', '" + descrizione + "', '" + stato + "', '" + IDOrdine + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from merci;");
        rs.next();
        ID = rs.getInt("ID");
        this.IDOrdine = IDOrdine;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.stato = stato;
        disconnectToDB(rs);
    }

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID della Merce dell' Ordine
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    public SimpleMerceOrdine(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from merci where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.IDOrdine = rs.getInt("IDOrdine");
            this.prezzo = rs.getDouble("prezzo");
            this.descrizione = rs.getString("descrizione");
            this.quantita = rs.getInt("quantita");
            this.stato = StatoOrdine.valueOf(rs.getString("stato"));
            disconnectToDB(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Confronta 2 oggetti di tipo {@link MerceOrdine} attraverso il loro {@code ID}.
     *
     * @param m Oggetto da confrontare
     *
     * @return <ul><li>0 se i due oggetti sono uguali,</li>
     * <li>1 se questo oggetto ha l'ID maggiore,</li>
     * <li>-1 se m ha l'ID maggiore.</li></ul>
     */
    @Override
    public int compareTo(MerceOrdine m) {
        if (Objects.isNull(m))
            throw new NullPointerException();
        if (this.equals(m))
            return 0;
        if (this.getID() > m.getID())
            return 1;
        else return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleMerceOrdine that = (SimpleMerceOrdine) o;
        return ID == that.ID &&
                getIDOrdine() == that.getIDOrdine();
    }

    /**
     * Ritorna la Descrizione della {@link SimpleMerceOrdine}.
     *
     * @return la descrizione della merce
     */
    @Override
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta la Descrizione alla {@link SimpleMerceOrdine}.
     *
     * @param descrizione Nuova Descrizione
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    @Override
    public void setDescrizione(String descrizione) throws SQLException {
        updateData("UPDATE sys.merci SET descrizione = '" + descrizione + "' WHERE (ID = '" + ID + "');");
        this.descrizione = descrizione;
    }

    /**
     * Ritorna un arraylist con i dettagli della {@link SimpleMerceOrdine}.
     *
     * @return ArrayList dei dettagli della merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(String.valueOf(getID()));
        toReturn.add(String.valueOf(getIDOrdine()));
        toReturn.add(String.valueOf(getPrezzo()));
        toReturn.add(getDescrizione());
        toReturn.add(String.valueOf(getQuantita()));
        toReturn.add(String.valueOf(getStato()));
        return toReturn;
    }

    /**
     * Ritorna il Codice Identificativo della {@link SimpleMerceOrdine}.
     *
     * @return l'ID della merce
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Ritorna il Codice Identificativo del {@link Corriere} che trasporta la {@link SimpleMerceOrdine}.
     *
     * @return l'ID del corriere
     */
    @Override
    public int getIDCorriere() {
        return IDCorriere;
    }

    /**
     * Associa il {@link Corriere} alla {@link SimpleMerceOrdine}.
     *
     * @param IDCorriere Codice Identificativo del Corriere
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    @Override
    public void setIDCorriere(int IDCorriere) throws SQLException {
        updateData("UPDATE sys.merci SET IDCorriere = '" + IDCorriere + "' WHERE (ID = '" + ID + "');");
        this.IDCorriere = IDCorriere;
    }

    /**
     * Ritorna il Codice Identificativo dell' {@link Ordine} della {@link SimpleMerceOrdine}.
     *
     * @return l'ID dell' Ordine
     */
    @Override
    public int getIDOrdine() {
        return IDOrdine;
    }

    /**
     * Associa l' {@link Ordine} alla {@link SimpleMerceOrdine}.
     *
     * @param IDOrdine Codice Identificativo dell' Ordine
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    @Override
    public void setIDOrdine(int IDOrdine) throws SQLException {
        updateData("UPDATE sys.merci SET IDOrdine = '" + IDOrdine + "' WHERE (ID = '" + ID + "');");
        this.IDOrdine = IDOrdine;
    }

    /**
     * Ritorna il Prezzo della {@link SimpleMerceOrdine}.
     *
     * @return il prezzo della merce
     */
    @Override
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * Imposta il Prezzo alla {@link SimpleMerceOrdine}.
     *
     * @param prezzo Nuovo Prezzo
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    @Override
    public void setPrezzo(double prezzo) throws SQLException {
        updateData("UPDATE sys.merci SET prezzo = '" + prezzo + "' WHERE (ID = '" + ID + "');");
        this.prezzo = prezzo;
    }

    /**
     * Ritorna la Quantità della {@link SimpleMerceOrdine}.
     *
     * @return la quantità della merce
     */
    @Override
    public int getQuantita() {
        return quantita;
    }

    /**
     * Imposta la Quantità alla {@link SimpleMerceOrdine}.
     *
     * @param quantita Nuova Quantità
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    @Override
    public void setQuantita(int quantita) throws SQLException {
        updateData("UPDATE sys.merci SET quantita = '" + quantita + "' WHERE (ID = '" + ID + "');");
        this.quantita = quantita;
    }

    /**
     * Ritorna lo {@link StatoOrdine Stato} della {@link SimpleMerceOrdine}.
     *
     * @return lo stato della merce
     */
    @Override
    public StatoOrdine getStato() {
        return stato;
    }

    /**
     * Imposta lo {@link StatoOrdine Stato} alla {@link SimpleMerceOrdine}.
     *
     * @param stato Stato della Merce
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    @Override
    public void setStato(StatoOrdine stato) throws SQLException {
        updateData("UPDATE sys.merci SET stato = '" + stato + "' WHERE (ID = '" + ID + "');");
        this.stato = stato;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, getIDOrdine());
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", IDOrdine=" + IDOrdine +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", quantita=" + quantita +
                ", stato=" + stato;
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.merci where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.prezzo = rs.getDouble("prezzo");
            this.descrizione = rs.getString("descrizione");
            this.quantita = rs.getInt("quantita");
            this.stato = StatoOrdine.valueOf(rs.getString("stato"));
            this.IDCorriere = rs.getInt("IDCorriere");
        }
        disconnectToDB(rs);
    }
}
