package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class Negozio {
    private int ID;
    private String nome;
    private String categoria;
    private String orarioApertura;
    private String orarioChiusura;
    private String indirizzo;
    private String telefono;
    private final ArrayList<Merce> inventario = new ArrayList<>();

    //todo
    public Negozio(int ID) {
        this.ID = ID;
    }

    private Merce addMerceInventario(ResultSet rs) throws SQLException {
        Merce toReturn = new Merce(rs.getInt("ID"), rs.getInt("IDNegozio"),
                rs.getDouble("prezzo"), rs.getString("descrizione"),
                rs.getInt("quantita"));
        inventario.add(toReturn);
        return toReturn;
    }

    /**
     * @param ID
     * @return
     */
    public Merce getMerce(int ID) {
        //todo controllare che l'IDNegozio della merce corrisponda all'ID del Negozio in java

        for (Merce toReturn : inventario) {
            if (toReturn.getID() == ID)
                return toReturn;
        }

        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.inventario where ID='" + ID + "';");
            if (rs.next())
                return addMerceInventario(rs);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * @return l'elenco della Merce del Negozio
     */
    public ArrayList<Merce> getMerceDisponibile() {
        inventario.clear();
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.inventario where IDNegozio='" + ID + "';");
            while (rs.next())
                addMerceInventario(rs);
            return inventario;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }

}