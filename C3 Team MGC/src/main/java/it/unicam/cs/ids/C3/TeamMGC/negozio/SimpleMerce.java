package it.unicam.cs.ids.C3.TeamMGC.negozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per rappresentare la {@link SimpleMerce} all' interno dell' Inventario di un {@link Negozio}.
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

    /**
     * Elimina la {@link SimpleMerce} dal db e aggiorna i dati dell' oggetto.
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

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public void setDescrizione(String descrizione) throws SQLException {
        updateData("UPDATE sys.inventario SET descrizione = '" + descrizione + "' WHERE (ID = '" + getID() + "');");
        this.descrizione = descrizione;
    }

    /**
     * Ritorna la lista dei dettagli della {@link SimpleMerce } presente nel DB.
     *
     * @return ArrayList<String> dei dettagli della merce.
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

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getIDNegozio() {
        return IDNegozio;
    }

    @Override
    public double getPrezzo() {
        return prezzo;
    }

    @Override
    public void setPrezzo(double prezzo) throws SQLException {
        updateData("UPDATE sys.inventario SET prezzo = '" + prezzo + "' WHERE (ID = '" + getID() + "');");
        this.prezzo = prezzo;
    }

    @Override
    public int getQuantita() {
        return quantita;
    }

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