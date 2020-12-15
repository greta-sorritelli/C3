package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Corriere {

    private int ID;
    private boolean disponibilita;
    private int capienza;

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

    /**
     * @return      ArrayList dei dettagli
     */
    public ArrayList<String> getDettagli() {
        ArrayList<String> corriere = new ArrayList<>();
        corriere.add(String.valueOf(getDisponibilita()));
        corriere.add(String.valueOf(getCapienza()));
        return corriere;
    }

    public void mandaAlert () {
            // TODO - implement Corriere.mandaAlert
            throw new UnsupportedOperationException();
        }

    public int getID() {
        return ID;
    }

    public boolean getDisponibilita() {
        return disponibilita;
    }

    public int getCapienza() {
        return capienza;
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

    public void setCapienza(int capienza) {
        try {
            updateData("UPDATE `sys`.`corrieri` SET `capienza` = '" + capienza + "' WHERE (`ID` = '" + this.ID + "');");
            this.capienza = capienza;
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }


}