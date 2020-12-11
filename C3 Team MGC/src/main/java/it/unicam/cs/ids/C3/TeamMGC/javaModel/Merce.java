package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Merce {
    private int ID;
    private int IDOrdine = 0;
    private double prezzo = 0;
    private String descrizione = "";
    private int quantita = 0;
    private StatoOrdine stato;

    public Merce(double prezzo, String descrizione) {
        try {
            updateData("INSERT INTO `sys`.`merci` (`prezzo`, `descrizione`) VALUES ('" + prezzo + "', '" + descrizione + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from merci;");
            rs.next();
            ID = rs.getInt("ID");
            this.prezzo = prezzo;
            this.descrizione = descrizione;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    /**
     * Costruttore per importare i dati dal DB.
     */
    public Merce(int ID, int IDOrdine, double prezzo, String descrizione, int quantita, StatoOrdine stato) {
        this.ID = ID;
        this.IDOrdine = IDOrdine;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.stato = stato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @return
     */
    public ArrayList<String> getDettagli() {
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(String.valueOf(getID()));
        toReturn.add(String.valueOf(getIDOrdine()));
        toReturn.add(String.valueOf(getPrezzo()));
        toReturn.add(getDescrizione());
        toReturn.add(String.valueOf(getQuantita()));
        toReturn.add(getStato().toString());
        return toReturn;
    }

    public int getID() {
        return ID;
    }

    public int getIDOrdine() {
        return IDOrdine;
    }

    public void setIDOrdine(int IDOrdine) {
        try {
            updateData("UPDATE `sys`.`merci` SET `IDOrdine` = '" + IDOrdine + "' WHERE (`ID` = '" + getID() + "');");
            this.IDOrdine = IDOrdine;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        try {
            updateData("UPDATE `sys`.`merci` SET `quantita` = '" + quantita + "' WHERE (`ID` = '" + getID() + "');");
            this.quantita = quantita;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public StatoOrdine getStato() {
        return stato;
    }

    /**
     * @param riRITIRATO
     */
    public void setStato(int riRITIRATO) {
        // TODO - implement Merce.setStato
        throw new UnsupportedOperationException();
    }

    /**
     * @param IDOrdine
     */
    public void setOrdine(int IDOrdine) {
        // TODO - implement Merce.setOrdine
        throw new UnsupportedOperationException();
    }

}