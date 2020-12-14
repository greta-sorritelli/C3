package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Magazziniere {

    private int ID = 0;
    private PuntoPrelievo puntoPrelievo = null;
    private String nome = null;
    private String cognome = null;

    public Magazziniere(int ID, PuntoPrelievo puntoPrelievo, String nome, String cognome) {
        this.ID = ID;
        this.puntoPrelievo = puntoPrelievo;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Magazziniere(PuntoPrelievo puntoPrelievo, String nome, String cognome) {
        try {
            updateData("INSERT INTO `sys`.`magazzinieri` (`nome`,`cognome`,`puntoPrelievo`) \n" +
                    "VALUES ('" + nome + "' , '" + cognome + "', '" + puntoPrelievo.getNome() + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from magazzinieri;");
            rs.next();
            this.ID = rs.getInt("ID");
            this.nome = nome;
            this.cognome = cognome;
            this.puntoPrelievo = puntoPrelievo;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    public int getID() {
        return ID;
    }

}