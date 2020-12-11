package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Corriere {

    private int ID;
    private StatoCorriere stato;
    private int capienza;

    public Corriere(int ID, StatoCorriere stato, int capienza) {
        this.ID = ID;
        this.stato = stato;
        this.capienza = capienza;
    }

    public Corriere(StatoCorriere stato, int capienza) {
        try {
            updateData("INSERT INTO `sys`.`corrieri` (`stato`, `capienza`) VALUES ('" + stato + "', '" + capienza + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from clienti;");
            rs.next();
            ID = rs.getInt("ID");
            this.stato = stato;
            this.capienza = capienza;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }

    }


    public ArrayList<String> getDettagli() {
        ArrayList<String> corriere = new ArrayList<>();
        corriere.add(getStato().toString());
        corriere.add(String.valueOf(getCapienza()));
        return corriere;
    }


        public void mandaAlert () {
            // TODO - implement Corriere.mandaAlert
            throw new UnsupportedOperationException();
        }

    public StatoCorriere getStato() {
        return stato;
    }

    public int getCapienza() {
        return capienza;
    }
}