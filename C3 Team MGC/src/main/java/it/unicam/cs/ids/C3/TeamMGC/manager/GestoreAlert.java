package it.unicam.cs.ids.C3.TeamMGC.manager;

import org.checkerframework.checker.units.qual.A;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

public class GestoreAlert {

    private static GestoreAlert gestoreAlert;

    private GestoreAlert() {
    }

    /**
     * Metodo per ottenere l' istanza singleton del {@link GestoreAlert}
     *
     * @return l'unica istanza presente o una nuova se non è già esistente
     */
    public static GestoreAlert getInstance() {
        if (gestoreAlert == null)
            gestoreAlert = new GestoreAlert();
        return gestoreAlert;
    }

    public ArrayList<ArrayList<String>> getDettagliAlert(int ID, String tipologiaUtente) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        switch (tipologiaUtente) {
            case "CLIENTE":
                getMessaggi(toReturn, "select * from alert_clienti where IDCliente ='" + ID + "';");
                break;
            case "MAGAZZINIERE":
                getMessaggi(toReturn, "select * from alert_magazzinieri where IDMagazziniere ='" + ID + "';");
                break;
            case "CORRIERE":
                getMessaggi(toReturn, "select * from alert_corrieri where IDCorriere ='" + ID + "';");
                break;
        }
        return toReturn;
    }

    public void deleteAlert(int IDAlert, String tipologiaUtente) throws SQLException {
        switch (tipologiaUtente) {
            case "CLIENTE":
                updateData("DELETE FROM sys.alert_clienti WHERE (ID = '" + IDAlert + "');");
                break;
            case "MAGAZZINIERE":
                updateData("DELETE FROM sys.alert_magazzinieri WHERE (ID = '" + IDAlert + "');");
                break;
            case "CORRIERE":
                updateData("DELETE FROM sys.alert_corrieri WHERE (ID = '" + IDAlert + "');");
                break;
        }
    }

    private void getMessaggi(ArrayList<ArrayList<String>> tmp, String query) throws SQLException {
        ResultSet rs = executeQuery(query);
        while (rs.next()) {
            ArrayList<String> toAdd = new ArrayList<>();
            toAdd.add(rs.getString("ID"));
            toAdd.add(rs.getString("messaggio"));
            tmp.add(toAdd);
        }
        disconnectToDB(rs);
    }
}
