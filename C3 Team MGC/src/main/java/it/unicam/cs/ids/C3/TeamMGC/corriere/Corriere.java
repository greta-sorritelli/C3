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

    public Corriere(String nome, String cognome, boolean disponibilita, int capienza) {
        try {
            updateData("INSERT INTO sys.corrieri (nome, cognome, stato, capienza) VALUES ('" + nome + "', '" + cognome +
                    "', '" + disponibilita + "', '" + capienza + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from corrieri;");
            rs.next();
            ID = rs.getInt("ID");
            this.nome = nome;
            this.cognome = cognome;
            this.disponibilita = disponibilita;
            this.capienza = capienza;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        try {
            if (capienza < 0)
                //todo eccezione
                throw new IllegalArgumentException();
            updateData("UPDATE sys.corrieri SET capienza = '" + capienza + "' WHERE (`ID` = '" + this.ID + "');");
            this.capienza = capienza;
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    public String getCognome() {
        return cognome;
    }

    /**
     * Ritorna un arraylist con i dettagli del {@link Corriere} in stringa.
     *
     * @return ArrayList dei dettagli
     */
    public ArrayList<String> getDettagli() {
        ArrayList<String> corriere = new ArrayList<>();
        corriere.add(nome);
        corriere.add(cognome);
        corriere.add(String.valueOf(getDisponibilita()));
        corriere.add(String.valueOf(getCapienza()));
        return corriere;
    }

    public boolean getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(boolean disponibilita) {
        try {
            updateData("UPDATE sys.corrieri SET stato = '" + disponibilita + "' WHERE (`ID` = '" + this.ID + "');");
            this.disponibilita = disponibilita;
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
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


}