package it.unicam.cs.ids.C3.TeamMGC.corriere;

import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleMerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la creazione di un {@link SimpleCorriere}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class SimpleCorriere implements Corriere {

    private final int ID;
    private String nome;
    private String cognome;
    private boolean disponibilita;
    private ArrayList<SimpleMerceOrdine> merceAffidata = new ArrayList<>();

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID del Corriere
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleCorriere(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from corrieri where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            this.disponibilita = rs.getBoolean("stato");
            disconnectToDB(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleCorriere(String nome, String cognome, boolean disponibilita) throws SQLException {
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato) VALUES ('" + nome + "', '" + cognome +
                "', '" + disponibilita + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from corrieri;");
        rs.next();
        ID = rs.getInt("ID");
        this.nome = nome;
        this.cognome = cognome;
        this.disponibilita = disponibilita;
        updateMerce();
        disconnectToDB(rs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleCorriere simpleCorriere = (SimpleCorriere) o;
        return getID() == simpleCorriere.getID();
    }

    public String getCognome() {
        return cognome;
    }

    /**
     * Ritorna un arraylist con i dettagli del {@link SimpleCorriere} in stringa.
     *
     * @return ArrayList dei dettagli
     */
    @Override
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> corriere = new ArrayList<>();
        corriere.add(String.valueOf(ID));
        corriere.add(nome);
        corriere.add(cognome);
        corriere.add(String.valueOf(getDisponibilita()));
        return corriere;
    }

    public boolean getDisponibilita() {
        return disponibilita;
    }

    /**
     * Imposta la nuova disponibilità del {@link SimpleCorriere}
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    public void setDisponibilita(boolean disponibilita) throws SQLException {
        updateData("UPDATE sys.corrieri SET stato = '" + disponibilita + "' WHERE (ID = '" + this.ID + "');");
        this.disponibilita = disponibilita;
    }

    public int getID() {
        return ID;
    }

    public String getNome() {
        return nome;
    }

    //todo test
    private void updateMerce() throws SQLException {
        merceAffidata.clear();
        ResultSet rs = executeQuery("select * from stato_merce where IDCorriere ='" + ID + "';");
        while (rs.next()) {
            int IDMerce = rs.getInt("IDMerce");
            merceAffidata.add(GestoreOrdini.getInstance().getMerceOrdine(IDMerce));
        }
    }

    //todo test
    public ArrayList<SimpleMerceOrdine> getMerceAffidata() throws SQLException {
        updateMerce();
        return merceAffidata;
    }

    //todo test
    public ArrayList<SimpleMerceOrdine> getMerce(StatoOrdine stato) throws SQLException {
        updateMerce();
        return merceAffidata.stream().filter(simpleMerceOrdine -> simpleMerceOrdine.getStato().equals(stato)).collect(Collectors.toCollection(ArrayList::new));
    }

    //todo test
    public ArrayList<ArrayList<String>> getDettagliMerceAffidata() throws SQLException {
        updateMerce();
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        for (SimpleMerceOrdine merce : merceAffidata) {
            dettagli.add(merce.getDettagli());
        }
        return dettagli;
    }

    //todo test
    public void associaMerce(int IDMerce) throws SQLException {
        updateMerce();
        StatoOrdine stato = GestoreOrdini.getInstance().getMerceOrdine(IDMerce).getStato();
        updateData("INSERT INTO sys.stato_merce (IDCorriere, IDMerce, stato_merce) " +
                "VALUES ('"+ ID + "', '" + IDMerce + "', '" + stato + "');");
        merceAffidata.add(GestoreOrdini.getInstance().getMerceOrdine(IDMerce));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    @Override
    public String toString() {
        return "Corriere{" +
                "ID=" + ID +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", disponibilita=" + disponibilita +
                '}';
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.corrieri where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.nome = rs.getString("nome");
            this.cognome = rs.getString("cognome");
            this.disponibilita = rs.getBoolean("stato");
        }
        disconnectToDB(rs);
    }

}