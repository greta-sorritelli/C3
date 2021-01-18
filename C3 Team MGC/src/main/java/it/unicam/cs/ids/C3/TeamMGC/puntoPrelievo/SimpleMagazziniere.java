package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la creazione di un {@link SimpleMagazziniere}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class SimpleMagazziniere implements Magazziniere {
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
    public SimpleMagazziniere(int ID) throws SQLException {
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
    public SimpleMagazziniere(int IDPuntoPrelievo, String nome, String cognome) throws SQLException {
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

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getIDPuntoPrelievo() {
        return IDPuntoPrelievo;
    }

    @Override
    public String getNome() {
        return nome;
    }
}