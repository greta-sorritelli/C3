package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class MerceOrdine {
    private int ID;
    private int IDOrdine = -1;
    private double prezzo = 0;
    private String descrizione = "";
    private int quantita = 0;
    private StatoOrdine stato;

    public MerceOrdine(double prezzo, String descrizione, int quantita, StatoOrdine stato) {
        try {
            updateData("INSERT INTO `sys`.`merci` (`prezzo`, `descrizione`, `quantita`, `stato`) " +
                    "VALUES ('" + prezzo + "', '" + descrizione + "', '" + quantita + "', '" + stato + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from merci;");
            rs.next();
            ID = rs.getInt("ID");
            this.prezzo = prezzo;
            this.descrizione = descrizione;
            this.quantita = quantita;
            this.stato = stato;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    public MerceOrdine(int ID, int IDOrdine, double prezzo, String descrizione, int quantita, StatoOrdine stato) {
        this.ID = ID;
        this.IDOrdine = IDOrdine;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.stato = stato;
    }

    public int getIDOrdine() {
        return IDOrdine;
    }

    public void setIDOrdine(int IDOrdine) {
        try {
            updateData("UPDATE `sys`.`merci` SET `IDOrdine` = '" + IDOrdine + "' WHERE (`ID` = '" + ID + "');");
            this.IDOrdine = IDOrdine;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
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
}
