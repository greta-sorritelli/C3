package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class Negozio {
    private String nome;
    private String categoria;
    private String orarioApertura;
    private String orarioChiusura;
    private String indirizzo;
    private String telefono;

    /**
     * @return l'elenco della Merce del Negozio
     */
    public ArrayList<Merce> getMerceDisponibile() {
        try {
            ArrayList<Merce> toReturn = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * FROM sys.inventario;");
            while (rs.next()) {
                Merce tmp = new Merce(rs.getInt("ID"), rs.getInt("IDNegozio"),
                        rs.getDouble("prezzo"), rs.getString("descrizione"),
                        rs.getInt("quantita"));
                toReturn.add(tmp);
            }
            return toReturn;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * @param ID
     */
    public void getMerce(int ID) {
        // TODO - implement Negozio.getMerce
        throw new UnsupportedOperationException();
    }

}