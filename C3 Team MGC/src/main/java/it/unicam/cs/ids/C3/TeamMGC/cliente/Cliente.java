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
    private String codiceRitiro = null;
    private String dataCreazioneCodice = null;

    public Cliente(int ID, String nome, String cognome, String codiceRitiro, String dataCreazioneCodice) {
        this.ID = ID;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceRitiro = codiceRitiro;
        this.dataCreazioneCodice = dataCreazioneCodice;
    }

    public Cliente(String nome, String cognome) throws SQLException {
        updateData("INSERT INTO `sys`.`clienti` (`nome`, `cognome`) VALUES ('" + nome + "', '" + cognome + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from clienti;");
        rs.next();
        ID = rs.getInt("ID");
        this.nome = nome;
        this.cognome = cognome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return getID() == cliente.getID() && getNome().equals(cliente.getNome()) && getCognome().equals(cliente.getCognome());
    }

    public String getCodiceRitiro() {

        return codiceRitiro;
    }

    /**
     * @return
     */
    //todo test
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> cliente = new ArrayList<>();
        cliente.add(String.valueOf(getID()));
        cliente.add(getNome());
        cliente.add(getCognome());
        cliente.add(String.valueOf(getCodiceRitiro()));
        cliente.add(getDataCreazioneCodice());
        return cliente;
    }

    /**
     *
     */
    //todo test
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.clienti where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            this.codiceRitiro = rs.getString("codiceRitiro");
            this.dataCreazioneCodice = rs.getString("dataCreazione");
        }
    }

    public String setCodiceRitiro(String codiceRitiro) throws SQLException {
        dataCreazioneCodice = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
        this.codiceRitiro = codiceRitiro;
        updateData("UPDATE `sys`.`clienti` SET `codiceRitiro` = '" + codiceRitiro + "', `dataCreazione` = '" + dataCreazioneCodice + "' WHERE (`ID` = '" + this.ID + "');");
        return codiceRitiro;
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

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getNome(), getCognome());
    }
}