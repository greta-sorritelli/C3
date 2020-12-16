package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Negozio {
    private final ArrayList<Merce> inventario = new ArrayList<>();
    private int IDNegozio;
    private String nome;
    private String categoria;
    private String orarioApertura;
    private String orarioChiusura;
    private String indirizzo;
    private String telefono;

    public Negozio(String nome, String categoria, String orarioApertura, String orarioChiusura, String indirizzo, String telefono) {
        try {
            updateData("INSERT INTO sys.negozi (nome, categoria, orarioApertura, orarioChiusura, indirizzo, telefono) " +
                    "VALUES ('" + nome + "', '" + categoria + "', '" + orarioApertura + "', '" + orarioChiusura + "', '" +
                    indirizzo + "', '" + telefono + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from inventario;");
            rs.next();
            IDNegozio = rs.getInt("ID");
            this.nome = nome;
            this.categoria = categoria;
            this.orarioApertura = orarioApertura;
            this.orarioChiusura = orarioChiusura;
            this.indirizzo = indirizzo;
            this.telefono = telefono;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //todo
    public Negozio(int IDNegozio) {
        this.IDNegozio = IDNegozio;
    }

    /**
     * @param merce
     * @return
     */
    public boolean removeMerce(Merce merce) {
        merce.delete();
        return inventario.remove(merce);
    }

    /**
     * Aggiunge la {@link Merce} al {@link Negozio}.
     *
     * @param merce Merce da aggiungere
     * @return {@code true} se la Merce viene inserita correttamente, {@code false} altrimenti
     */
    public boolean addMerce(Merce merce) {
        if (!inventario.contains(merce))
            return inventario.add(merce);
        else
            return false;
    }

    /**
     * Controlla se la {@link Merce} che si vuole creare e' gia' presente nell' Inventario. Se non e' presente
     * viene creata e aggiunta all' Inventario del {@link Negozio}.
     *
     * @return la Merce
     */
    private Merce addMerceInventario(ResultSet rs) throws SQLException {
        for (Merce merce : inventario)
            if (merce.getID() == rs.getInt("ID"))
                return merce;
        Merce toReturn = new Merce(rs.getInt("ID"), rs.getInt("IDNegozio"),
                rs.getDouble("prezzo"), rs.getString("descrizione"),
                rs.getInt("quantita"));
        addMerce(toReturn);
        return toReturn;
    }

    /**
     * Ritorna la {@link Merce} collegata all' {@code ID}.
     *
     * @param ID Codice Identificativo della Merce
     * @return la Merce desiderata
     */
    public Merce getMerce(int ID) {
        //todo controllare che l'IDNegozio della merce corrisponda all'ID del Negozio in java
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
     * Ritorna tutta la {@link Merce} all' interno del {@link Negozio}.
     *
     * @return l'elenco della Merce del Negozio
     */
    public ArrayList<Merce> getMerceDisponibile() {
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.inventario where IDNegozio='" + IDNegozio + "';");
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