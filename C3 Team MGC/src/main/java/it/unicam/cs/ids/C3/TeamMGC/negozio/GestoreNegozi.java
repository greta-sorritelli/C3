package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class GestoreNegozi implements Gestore<Negozio> {

    ArrayList<Negozio> negozi = new ArrayList<>();

    @Override
    public Negozio getItem(int ID) throws SQLException {
        //todo
        ResultSet rs = executeQuery("SELECT * FROM sys.negozi where ID='" + ID + "' ;");
        if (rs.next())
            return addNegozio(rs);
        else
            throw new IllegalArgumentException("ID non valido.");
    }

//todo controllare costruttore negozio
    private Negozio addNegozio(ResultSet rs) throws SQLException {
        for (Negozio negozio : negozi)
            if (negozio.getIDNegozio() == rs.getInt("ID"))
                return negozio;
        Negozio toReturn = new Negozio(rs.getInt("ID"));
        addNegozioToList(toReturn);
        return toReturn;
    }

    private boolean addNegozioToList(Negozio negozio) {
        if (!negozi.contains(negozio))
            return negozi.add(negozio);
        else
            return false;
    }

    @Override
    public ArrayList<Negozio> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.negozi;");
        while (rs.next())
            addNegozio(rs);
        return new ArrayList<>(negozi);
    }

    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.negozi;");
        while (rs.next())
            addNegozio(rs);
        for (Negozio negozio : negozi)
            dettagli.add(negozio.getDettagli());
        return dettagli;
    }
}
