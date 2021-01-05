package it.unicam.cs.ids.C3.TeamMGC.corriere;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Corriere {
    private int ID;
    private String nome;
    private String cognome;
    private boolean disponibilita;
    private int capienza;

    public Corriere(int ID, String nome, String cognome, boolean disponibilita, int capienza) {
        this.ID = ID;
        this.nome = nome;
        this.cognome = cognome;
        this.disponibilita = disponibilita;
        this.capienza = capienza;
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

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) throws SQLException {
        if (capienza < 0)
            throw new IllegalArgumentException("Capienza non valida.");
        updateData("UPDATE sys.corrieri SET capienza = '" + capienza + "' WHERE (`ID` = '" + this.ID + "');");
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