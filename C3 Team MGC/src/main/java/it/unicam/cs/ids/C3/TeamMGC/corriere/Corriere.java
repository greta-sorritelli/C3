package it.unicam.cs.ids.C3.TeamMGC.corriere;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Corriere {
    private int ID;
    private String nome;
    private String cognome;
    private boolean disponibilita;
    private int capienza;

    //todo da aggiornare per il nome ed il cognome
    public Corriere(int ID, boolean disponibilita, int capienza) {
        this.ID = ID;
        this.disponibilita = disponibilita;
        this.capienza = capienza;
    }

    public Corriere(boolean disponibilita, int capienza) {
        try {
            updateData("INSERT INTO `sys`.`corrieri` (`stato`, `capienza`) VALUES ('" + disponibilita + "', '" + capienza + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from corrieri;");
            rs.next();
            ID = rs.getInt("ID");
            this.disponibilita = disponibilita;
            this.capienza = capienza;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        try {
            if (capienza < 0)
                //todo eccezione
                throw new IllegalArgumentException();
            updateData("UPDATE `sys`.`corrieri` SET `capienza` = '" + capienza + "' WHERE (`ID` = '" + this.ID + "');");
            this.capienza = capienza;
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    /**
     * @return ArrayList dei dettagli
     */
    public ArrayList<String> getDettagli() {
        ArrayList<String> corriere = new ArrayList<>();
        corriere.add(String.valueOf(getDisponibilita()));
        corriere.add(String.valueOf(getCapienza()));
        return corriere;
    }

    public boolean getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(boolean statoCorriere) {
        try {
            updateData("UPDATE `sys`.`corrieri` SET `stato` = '" + statoCorriere + "' WHERE (`ID` = '" + this.ID + "');");
            this.disponibilita = statoCorriere;
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    public int getID() {
        return ID;
    }

    public void mandaAlert() {
        // TODO - implement Corriere.mandaAlert
        throw new UnsupportedOperationException();
    }
}