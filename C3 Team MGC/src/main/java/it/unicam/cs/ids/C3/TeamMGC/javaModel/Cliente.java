package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;


public class Cliente {

    private int ID;
    private String nome;
    private String cognome;
    private String codiceRitiro;
    private String dataCreazioneCodice;

    private Cliente() {
        codiceRitiro = null;
        dataCreazioneCodice = "1900-01-01";
    }

    public Cliente(String nome, String cognome) {
        this();
        try {
            updateData("INSERT INTO `sys`.`clienti` (`nome`, `cognome`) VALUES ('" + nome + "', '" + cognome + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from clienti;");
            rs.next();
            ID = rs.getInt("ID");

        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }

        this.nome = nome;
        this.cognome = cognome;
    }

    public String getCodiceRitiro() {

        return codiceRitiro;
    }

    /**
     * @param codiceRitiro
     */
    public void setCodiceRitiro(String codiceRitiro) {
        try {
            dataCreazioneCodice = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
            this.codiceRitiro = codiceRitiro;
            updateData("UPDATE `sys`.`clienti` SET `codiceRitiro` = '" + codiceRitiro + "', `dataCreazione` = '" + dataCreazioneCodice + "' WHERE (`ID` = '" + this.ID + "');");
        }catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    public String getCognome() {
        return cognome;
    }

    public String getDataCreazioneCodice() {
        return dataCreazioneCodice;
    }

    public int getID() {
        return ID;
    }

    public String getNome() {
        return nome;
    }

}