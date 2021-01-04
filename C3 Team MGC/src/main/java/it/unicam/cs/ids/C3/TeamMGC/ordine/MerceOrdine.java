package it.unicam.cs.ids.C3.TeamMGC.ordine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class MerceOrdine {
    private int ID;
    private int IDOrdine = -1;
    private double prezzo = 0;
    private String descrizione = "";
    private int quantita = 0;
    private StatoOrdine stato;

    public MerceOrdine(double prezzo, String descrizione, StatoOrdine stato, int IDOrdine) {
        try {
            updateData("INSERT INTO sys.merci (prezzo, descrizione, stato, IDOrdine) " +
                    "VALUES ('" + prezzo + "', '" + descrizione + "', '" + stato + "', '" + IDOrdine + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from merci;");
            rs.next();
            ID = rs.getInt("ID");
            this.IDOrdine = IDOrdine;
            this.prezzo = prezzo;
            this.descrizione = descrizione;
            this.stato = stato;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    public MerceOrdine(int ID, int IDOrdine, double prezzo, String descrizione, int quantita, StatoOrdine stato) {
        this.ID = ID;
        this.IDOrdine = IDOrdine;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.stato = stato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerceOrdine that = (MerceOrdine) o;
        return ID == that.ID &&
                getIDOrdine() == that.getIDOrdine();
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        try {
            updateData("UPDATE sys.merci SET descrizione = '" + descrizione + "' WHERE (ID = '" + ID + "');");
            this.descrizione = descrizione;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<String> getDettagli() {
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(String.valueOf(getID()));
        toReturn.add(String.valueOf(getIDOrdine()));
        toReturn.add(String.valueOf(getPrezzo()));
        toReturn.add(getDescrizione());
        toReturn.add(String.valueOf(getQuantita()));
        toReturn.add(String.valueOf(getStato()));
        return toReturn;
}

    public int getID() {
        return ID;
    }

    public int getIDOrdine() {
        return IDOrdine;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        try {
            updateData("UPDATE sys.merci SET prezzo = '" + prezzo + "' WHERE (ID = '" + ID + "');");
            this.prezzo = prezzo;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        try {
            updateData("UPDATE sys.merci SET quantita = '" + quantita + "' WHERE (ID = '" + ID + "');");
            this.quantita = quantita;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public StatoOrdine getStato() {
        return stato;
    }

    public void setStato(StatoOrdine stato) {
        try {
            updateData("UPDATE sys.merci SET stato = '" + stato + "' WHERE (ID = '" + ID + "');");
            this.stato = stato;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, getIDOrdine());
    }

    @Override
    public String toString() {
        return "MerceOrdine{" +
                "ID=" + ID +
                ", IDOrdine=" + IDOrdine +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", quantita=" + quantita +
                ", stato=" + stato +
                '}';
    }
}
