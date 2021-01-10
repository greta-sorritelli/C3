package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

/**
 * Classe per la creazione di un {@link Magazziniere}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class Magazziniere {
    private int ID = 0;
    private final int IDPuntoPrelievo;
    private String nome = null;
    private String cognome = null;

    /**
     * Costruttore per importare i dati dal DB
     *
     */
    public Magazziniere(int ID, int IDPuntoPrelievo , String nome, String cognome) {
        this.ID = ID;
        this.IDPuntoPrelievo = IDPuntoPrelievo;
        this.nome = nome;
        this.cognome = cognome;
    }

    /**
     * Costruttore per inserire i dati nel DB
     *
     * @throws SQLException eccezione causata una query SQL
     */
    //todo test creazione
    public Magazziniere(int IDPuntoPrelievo, String nome, String cognome) throws SQLException {
            updateData("INSERT INTO `sys`.`magazzinieri` (`nome`,`cognome`,`puntoPrelievo`) \n" +
                    "VALUES ('" + nome + "' , '" + cognome + "', '" + IDPuntoPrelievo + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from magazzinieri;");
            rs.next();
            this.ID = rs.getInt("ID");
            this.nome = nome;
            this.cognome = cognome;
            this.IDPuntoPrelievo = IDPuntoPrelievo;
    }

    public int getID() {
        return ID;
    }


    public void mandaAlert() {
     //todo
    }

}