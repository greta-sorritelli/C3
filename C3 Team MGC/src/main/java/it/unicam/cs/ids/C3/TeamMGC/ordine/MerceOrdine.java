package it.unicam.cs.ids.C3.TeamMGC.ordine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

/**
 * Classe per la creazione di una {@link it.unicam.cs.ids.C3.TeamMGC.negozio.Merce} all'interno di un {@link Ordine}
 * L'oggetto diventa di tipo {@link MerceOrdine}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class MerceOrdine {
    private final int ID;
    private int IDOrdine = -1;
    private double prezzo = 0;
    private String descrizione = "";
    private int quantita = 0;
    private StatoOrdine stato;

    /**
     * Costruttore per inserire i dati nel DB
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    public MerceOrdine(double prezzo, String descrizione, StatoOrdine stato, int IDOrdine) throws SQLException {
        updateData("INSERT INTO sys.merci (prezzo, descrizione, stato, IDOrdine) " +
                "VALUES ('" + prezzo + "', '" + descrizione + "', '" + stato + "', '" + IDOrdine + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from merci;");
        rs.next();
        ID = rs.getInt("ID");
        this.IDOrdine = IDOrdine;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.stato = stato;
    }

    /**
     * Costruttore per importare i dati dal DB
     *
     */
    //todo test
    public MerceOrdine(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from merci where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.IDOrdine = rs.getInt("IDOrdine");
            this.prezzo = rs.getDouble("prezzo");
            this.descrizione = rs.getString("descrizione");
            this.quantita = rs.getInt("quantita");
            this.stato = StatoOrdine.valueOf(rs.getString("stato"));
        } else
            throw new IllegalArgumentException("ID non valido.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerceOrdine that = (MerceOrdine) o;
        return ID == that.ID &&
                getIDOrdine() == that.getIDOrdine();
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) throws SQLException {
        updateData("UPDATE sys.merci SET descrizione = '" + descrizione + "' WHERE (ID = '" + ID + "');");
        this.descrizione = descrizione;
    }

    /**
     * @return ArrayList<String> dei dettagli della merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
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

    public int getID() {
        return ID;
    }

    public int getIDOrdine() {
        return IDOrdine;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) throws SQLException {
        updateData("UPDATE sys.merci SET prezzo = '" + prezzo + "' WHERE (ID = '" + ID + "');");
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) throws SQLException {
        updateData("UPDATE sys.merci SET quantita = '" + quantita + "' WHERE (ID = '" + ID + "');");
        this.quantita = quantita;
    }

    public StatoOrdine getStato() {
        return stato;
    }

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
        return "MerceOrdine{" +
                "ID=" + ID +
                ", IDOrdine=" + IDOrdine +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", quantita=" + quantita +
                ", stato=" + stato +
                '}';
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.merci where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.prezzo = rs.getDouble("prezzo");
            this.descrizione = rs.getString("descrizione");
            this.quantita = rs.getInt("quantita");
            this.stato = StatoOrdine.valueOf(rs.getString("stato"));
        }
    }
}
