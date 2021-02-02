package it.unicam.cs.ids.C3.TeamMGC.negozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * La classe implementa l' interfaccia {@link Merce} ed ha la responsabilità di gestire una Merce all' interno
 * dell' inventario di un {@link Negozio}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class SimpleMerce implements Merce {
    private int ID;
    private int IDNegozio;
    private double prezzo;
    private String descrizione;
    private int quantita;

    /**
     * Costruttore per la {@link SimpleMerce} all' interno dell' Inventario del Negozio.
     *
     * @param IDNegozio   ID del {@link Negozio}
     * @param prezzo      Prezzo della Merce
     * @param descrizione Descrizione della Merce
     * @param quantita    Quantità della Merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleMerce(int IDNegozio, double prezzo, String descrizione, int quantita) throws SQLException {
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) " +
                "VALUES ('" + IDNegozio + "', '" + prezzo + "', '" + descrizione + "', '" + quantita + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from inventario;");
        rs.next();
        ID = rs.getInt("ID");
        this.IDNegozio = IDNegozio;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.quantita = quantita;
        disconnectToDB(rs);
    }

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID della Merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleMerce(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from inventario where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.IDNegozio = rs.getInt("IDNegozio");
            this.prezzo = rs.getDouble("prezzo");
            this.descrizione = rs.getString("descrizione");
            this.quantita = rs.getInt("quantita");
            disconnectToDB(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }

    }

    //todo commento e test
    @Override
    public int compareTo(Merce o) {
        if (Objects.isNull(o))
            throw new NullPointerException();
        if (this.equals(o))
            return 0;
        if (this.getID() > o.getID())
            return 1;
        else return -1;
    }

    /**
     * Elimina la {@link SimpleMerce} dal DB e aggiorna i dati dell' oggetto.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void delete() throws SQLException {
        updateData("DELETE FROM sys.inventario WHERE (ID = '" + ID + "');");
        this.ID = -1;
        this.IDNegozio = -1;
        this.prezzo = -1;
        this.descrizione = "";
        this.quantita = -1;
    }

    /**
     * Ritorna la Descrizione della {@link SimpleMerce}.
     *
     * @return la descrizione della Merce
     */
    @Override
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta la nuova Descrizione della {@link SimpleMerce}.
     *
     * @param descrizione Nuova descrizione
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    @Override
    public void setDescrizione(String descrizione) throws SQLException {
        updateData("UPDATE sys.inventario SET descrizione = '" + descrizione + "' WHERE (ID = '" + getID() + "');");
        this.descrizione = descrizione;
    }

    /**
     * Ritorna la lista dei dettagli della {@link SimpleMerce } presente nel DB.
     *
     * @return ArrayList dei dettagli della merce.
     *
     * @throws SQLException Errore causato da una query SQL
     **/
    @Override
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(String.valueOf(getID()));
        toReturn.add(String.valueOf(getIDNegozio()));
        toReturn.add(String.valueOf(getPrezzo()));
        toReturn.add(getDescrizione());
        toReturn.add(String.valueOf(getQuantita()));
        return toReturn;
    }

    /**
     * Ritorna il Codice Identificativo della {@link SimpleMerce}.
     *
     * @return il Codice Identificativo
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Ritorna il Codice Identificativo del {@link Negozio} della {@link SimpleMerce}.
     *
     * @return il Codice Identificativo del Negozio
     */
    @Override
    public int getIDNegozio() {
        return IDNegozio;
    }

    /**
     * Ritorna il Prezzo della {@link SimpleMerce}.
     *
     * @return il Prezzo
     */
    @Override
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * Imposta il nuovo Prezzo della {@link SimpleMerce}.
     *
     * @param prezzo Nuovo prezzo
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    @Override
    public void setPrezzo(double prezzo) throws SQLException {
        updateData("UPDATE sys.inventario SET prezzo = '" + prezzo + "' WHERE (ID = '" + getID() + "');");
        this.prezzo = prezzo;
    }

    /**
     * Ritorna la Quantità della {@link SimpleMerce}.
     *
     * @return la Quantità
     */
    @Override
    public int getQuantita() {
        return quantita;
    }

    /**
     * Imposta la nuova Quantità della {@link SimpleMerce}.
     *
     * @param quantita Nuova quantità
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    @Override
    public void setQuantita(int quantita) throws SQLException {
        updateData("UPDATE sys.inventario SET quantita = '" + quantita + "' WHERE (ID = '" + getID() + "');");
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", IDNegozio=" + IDNegozio +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", quantita=" + quantita;
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.inventario where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.prezzo = rs.getDouble("prezzo");
            this.descrizione = rs.getString("descrizione");
            this.quantita = rs.getInt("quantita");
        }
        disconnectToDB(rs);
    }

}