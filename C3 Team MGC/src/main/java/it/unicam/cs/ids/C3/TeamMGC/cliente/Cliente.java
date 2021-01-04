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

    public Cliente(String nome, String cognome) {
        try {
            updateData("INSERT INTO `sys`.`clienti` (`nome`, `cognome`) VALUES ('" + nome + "', '" + cognome + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from clienti;");
            rs.next();
            ID = rs.getInt("ID");
            this.nome = nome;
            this.cognome = cognome;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
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
    public ArrayList<String> getDettagli() {
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
    public void update() {
        try {
            ResultSet rs = executeQuery("select * from sys.clienti where ID= '" + this.ID + "';");
            if (rs.next()) {
                this.nome = rs.getString("nome");
                this.cognome = rs.getString("cognome");
                this.codiceRitiro = rs.getString("codiceRitiro");
                this.dataCreazioneCodice = rs.getString("dataCreazione");
            }
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    public String setCodiceRitiro(String codiceRitiro) {
        try {
            dataCreazioneCodice = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
            this.codiceRitiro = codiceRitiro;
            updateData("UPDATE `sys`.`clienti` SET `codiceRitiro` = '" + codiceRitiro + "', `dataCreazione` = '" + dataCreazioneCodice + "' WHERE (`ID` = '" + this.ID + "');");
            return codiceRitiro;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
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