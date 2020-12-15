package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

/**
 * Rappresenta la Merce all' interno dell' Inventario di un {@link Negozio}.
 */
public class Merce {
    private int ID;
    private int IDNegozio;
    private double prezzo = 0;
    private String descrizione = "";
    private int quantita = 0;

    /**
     * Costruttore per la {@link Merce} all' interno dell' Inventario del Negozio.
     *
     * @param IDNegozio
     * @param prezzo
     * @param descrizione
     * @param quantita
     */
    public Merce(int IDNegozio, double prezzo, String descrizione, int quantita) {
        try {
            updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) " +
                    "VALUES ('" + IDNegozio + "', '" + prezzo + "', '" + descrizione + "', '" + quantita + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from inventario;");
            rs.next();
            ID = rs.getInt("ID");
            this.IDNegozio = IDNegozio;
            this.prezzo = prezzo;
            this.descrizione = descrizione;
            this.quantita = quantita;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    /**
     * todo
     * Costruttore per importare i dati dal DB.
     */
    public Merce(int ID, int IDNegozio, double prezzo, String descrizione, int quantita) {
        this.ID = ID;
        this.IDNegozio = IDNegozio;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.quantita = quantita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        try {
            updateData("UPDATE sys.inventario SET descrizione = '" + descrizione + "' WHERE (ID = '" + getID() + "');");
            this.descrizione = descrizione;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @return
     */
    public ArrayList<String> getDettagli() {
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(String.valueOf(getID()));
        toReturn.add(String.valueOf(getIDNegozio()));
        toReturn.add(String.valueOf(getPrezzo()));
        toReturn.add(getDescrizione());
        toReturn.add(String.valueOf(getQuantita()));
        return toReturn;
    }

    public int getID() {
        return ID;
    }

    public int getIDNegozio() {
        return IDNegozio;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        try {
            updateData("UPDATE sys.inventario SET prezzo = '" + prezzo + "' WHERE (ID = '" + getID() + "');");
            this.prezzo = prezzo;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        try {
            updateData("UPDATE sys.inventario SET quantita = '" + quantita + "' WHERE (ID = '" + getID() + "');");
            this.quantita = quantita;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void delete() {
        try {
            updateData("DELETE FROM sys.inventario WHERE (ID = '" + ID + "');");
            ID = -1;
            IDNegozio = -1;
            prezzo = -1;
            descrizione = null;
            quantita = -1;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}