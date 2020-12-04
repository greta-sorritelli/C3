package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;


public class Cliente {

    private String ID;
    private String nome;
    private String cognome;
    private String codiceRitiro;
    private Date dataCreazioneCodice;

    private Cliente() {
        codiceRitiro = null;
        dataCreazioneCodice = null;
    }

    public Cliente(String nome, String cognome) {
        this();
        //todo generare ID dal Database
        try {
            executeQuery("INSERT INTO clienti ('nome', 'cognome') VALUES ('nome  ', 'cognome ');");
            //executeQuery("INSERT INTO clienti ('nome', 'cognome') VALUES ('" + nome + "', '" + cognome + "');");
        } catch (SQLException throwables) {
            //todo
            throwables.printStackTrace();
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
        this.codiceRitiro = codiceRitiro;
        dataCreazioneCodice = Date.from(Instant.now());
    }

    public String getCognome() {
        return cognome;
    }

    public Date getDataCreazioneCodice() {
        return dataCreazioneCodice;
    }

    public String getID() {
        return ID;
    }

    public String getNome() {
        return nome;
    }

}