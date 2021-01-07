package it.unicam.cs.ids.C3.TeamMGC.cliente;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;


public class Cliente {
    private int ID;
    private String nome;
    private String cognome;
    private String codiceRitiro = "";
    private String dataCreazioneCodice = "";

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID del Cliente
     * @throws SQLException
     */
    public Cliente(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from clienti where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            this.codiceRitiro = rs.getString("codiceRitiro");
            this.dataCreazioneCodice = rs.getString("dataCreazione");
        } else
            throw new IllegalArgumentException("ID non valido.");
    }

    public Cliente(String nome, String cognome) throws SQLException {
        updateData("INSERT INTO sys.clienti (nome, cognome) VALUES ('" + nome + "', '" + cognome + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from clienti;");
        rs.next();
        ID = rs.getInt("ID");
        this.nome = nome;
        this.cognome = cognome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return getID() == cliente.getID();
    }

    public String getCodiceRitiro() {
        return codiceRitiro;
    }

    public String getCognome() {
        return cognome;
    }

    public String getDataCreazioneCodice() {
        return dataCreazioneCodice;
    }

    /**
     * @return
     */
    //todo test
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(String.valueOf(getID()));
        toReturn.add(getNome());
        toReturn.add(getCognome());
        toReturn.add(getCodiceRitiro());
        toReturn.add(getDataCreazioneCodice());
        return toReturn;
    }

    public int getID() {
        return ID;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    public String setCodiceRitiro(String codiceRitiro) throws SQLException {
        dataCreazioneCodice = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
        this.codiceRitiro = codiceRitiro;
        updateData("UPDATE sys.clienti SET codiceRitiro = '" + codiceRitiro + "', dataCreazione = '" + dataCreazioneCodice + "' WHERE (ID = '" + this.ID + "');");
        return codiceRitiro;
    }

    /**
     * @throws SQLException
     */
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.clienti where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            if (Objects.isNull(rs.getString("codiceRitiro"))) {
                this.codiceRitiro = "";
                this.dataCreazioneCodice = "";
            } else {
                this.codiceRitiro = rs.getString("codiceRitiro");
                this.dataCreazioneCodice = rs.getString("dataCreazione");
            }
        }
    }
}