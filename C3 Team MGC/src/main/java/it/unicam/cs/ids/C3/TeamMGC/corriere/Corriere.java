package it.unicam.cs.ids.C3.TeamMGC.corriere;

import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Corriere {
    private int ID;
    private String nome;
    private String cognome;
    private boolean disponibilita;
    private int capienza;

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID del Corriere
     * @throws SQLException
     */
    public Corriere(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from corrieri where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            this.disponibilita = rs.getBoolean("stato");
            this.capienza = rs.getInt("capienza");
        } else
            throw new IllegalArgumentException("ID non valido.");
    }

    public Corriere(String nome, String cognome, boolean disponibilita, int capienza) throws SQLException {
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato, capienza) VALUES ('" + nome + "', '" + cognome +
                "', '" + disponibilita + "', '" + capienza + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from corrieri;");
        rs.next();
        ID = rs.getInt("ID");
        this.nome = nome;
        this.cognome = cognome;
        this.disponibilita = disponibilita;
        this.capienza = capienza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corriere corriere = (Corriere) o;
        return getID() == corriere.getID();
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) throws SQLException {
        if (capienza < 0)
            throw new IllegalArgumentException("Capienza non valida.");
        updateData("UPDATE sys.corrieri SET capienza = '" + capienza + "' WHERE (`ID` = '" + this.ID + "');");
        if (capienza == 0) {
            updateData("UPDATE sys.corrieri SET stato = '" + false + "' WHERE (`ID` = '" + this.ID + "');");
            this.disponibilita = false;
        }
        this.capienza = capienza;
    }

    public String getCognome() {
        return cognome;
    }

    /**
     * Ritorna un arraylist con i dettagli del {@link Corriere} in stringa.
     *
     * @return ArrayList dei dettagli
     */
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> corriere = new ArrayList<>();
        corriere.add(String.valueOf(ID));
        corriere.add(nome);
        corriere.add(cognome);
        corriere.add(String.valueOf(getDisponibilita()));
        corriere.add(String.valueOf(getCapienza()));
        return corriere;
    }

    public boolean getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(boolean disponibilita) throws SQLException {
        updateData("UPDATE sys.corrieri SET stato = '" + disponibilita + "' WHERE (`ID` = '" + this.ID + "');");
        this.disponibilita = disponibilita;
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

    public void mandaAlert() {
        // TODO - implement Corriere.mandaAlert
        throw new UnsupportedOperationException();
    }

    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.corrieri where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            this.disponibilita = rs.getBoolean("stato");
            this.capienza = rs.getInt("capienza");
        }
    }

}