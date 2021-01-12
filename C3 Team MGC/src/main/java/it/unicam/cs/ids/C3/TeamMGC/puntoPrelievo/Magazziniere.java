package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la creazione di un {@link Magazziniere}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class Magazziniere {
    private final int IDPuntoPrelievo;
    private final int ID ;
    private final String nome ;
    private final String cognome ;

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID del Magazziniere
     * @throws SQLException Errore causato da una query SQL
     */
    public Magazziniere(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from magazzinieri where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.IDPuntoPrelievo = rs.getInt("IDPuntoPrelievo");
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            disconnectToDB(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Costruttore per inserire i dati nel DB
     *
     * @throws SQLException eccezione causata una query SQL
     */
    public Magazziniere(int IDPuntoPrelievo, String nome, String cognome) throws SQLException {
        updateData("INSERT INTO sys.magazzinieri (IDPuntoPrelievo,nome,cognome) \n" +
                "VALUES ('" + IDPuntoPrelievo + "' , '" + nome + "', '" + cognome + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from magazzinieri;");
        rs.next();
        this.ID = rs.getInt("ID");
        this.nome = nome;
        this.cognome = cognome;
        this.IDPuntoPrelievo = IDPuntoPrelievo;
        disconnectToDB(rs);
    }

    public String getCognome() {
        return cognome;
    }

    public int getID() {
        return ID;
    }

    public int getIDPuntoPrelievo() {
        return IDPuntoPrelievo;
    }

    public String getNome() {
        return nome;
    }

    public void mandaAlert() {
        //todo
    }

}