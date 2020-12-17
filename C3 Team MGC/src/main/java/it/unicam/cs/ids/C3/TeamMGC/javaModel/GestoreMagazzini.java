package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class GestoreMagazzini {

    //todo
//    public Magazziniere getMagazziniere(PuntoPrelievo magazzino) {
//		return magazzino.getMagazziniere();
//	}

    /**
     * @return l'elenco dei Magazzini Disponibili
     */
    public ArrayList<PuntoPrelievo> getMagazziniDisponibili() {
        try {
            ArrayList<PuntoPrelievo> puntoPrel = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * FROM sys.punti_prelievo;");
            while (rs.next()) {
                PuntoPrelievo tmp = new PuntoPrelievo(rs.getInt("ID"), rs.getString("indirizzo"), rs.getString("nome"));
                puntoPrel.add(tmp);
            }
            return puntoPrel;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }

}