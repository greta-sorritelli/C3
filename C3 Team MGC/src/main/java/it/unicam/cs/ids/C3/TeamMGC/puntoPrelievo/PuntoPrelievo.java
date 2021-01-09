package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

/**
 * Classe per la creazione di un {@link PuntoPrelievo}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class PuntoPrelievo {
    private int ID = 0;
    private String indirizzo = "";
    private String nome = "";

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID del PuntoPrelievo
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public PuntoPrelievo(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from punti_prelievo where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.indirizzo = rs.getString("indirizzo");
            this.nome = rs.getString("nome");
        } else
            throw new IllegalArgumentException("ID non valido.");
    }

    /**
     * Costruttore per inserire i dati nel DB
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    public PuntoPrelievo(String indirizzo, String nome) throws SQLException {
        updateData("INSERT INTO sys.punti_prelievo (nome,indirizzo) \n" +
                "VALUES ('" + nome + "' , '" + indirizzo + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from punti_prelievo;");
        rs.next();
        this.ID = rs.getInt("ID");
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuntoPrelievo that = (PuntoPrelievo) o;
        return getID() == that.getID();
    }

    /**
     * todo
     *
     * @return ArrayList<String> dei dettagli del punto di prelievo.
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> dettagli = new ArrayList<>();
        dettagli.add((String.valueOf(getID())));
        dettagli.add(getNome());
        dettagli.add(getIndirizzo());
        return dettagli;
    }

//    public void setMagazziniere(IMagazziniere magazziniere) {
//        try {
//            updateData("UPDATE sys.punti_prelievo SET magazziniere = '" + magazziniere.getID() + "' WHERE (ID = '" + this.ID + "');");
//            this.magazziniere = magazziniere;
//        } catch (SQLException exception) {
//            //TODO
//            exception.printStackTrace();
//        }
//        this.magazziniere = magazziniere;
//
//    }

    public int getID() {
        return ID;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Ritorna la lista di tutte le merci appartenenti a tale ordine e presenti nel punto di prelievo
     *
     * @param IDOrdine id dell' ordine
     *
     * @throws SQLException Errore causato da una query SQL
     */
    //todo cambiare forse levare
    public ArrayList<MerceOrdine> getMerceMagazzino(int IDOrdine) throws SQLException {
        ArrayList<MerceOrdine> lista = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * from merci\n" +
                "where IDOrdine = " + IDOrdine + " and stato = '" + StatoOrdine.IN_DEPOSITO.toString() + "';");
        while (rs.next()) {
            MerceOrdine merceOrdine = new MerceOrdine(rs.getInt("ID"), rs.getInt("IDOrdine"),
                    rs.getDouble("prezzo"), rs.getString("descrizione"), rs.getInt("quantita"),
                    StatoOrdine.valueOf(rs.getString("stato")));
            lista.add(merceOrdine);
        }
        return lista;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Ritorna l' insieme degli ordini effettuati dal cliente e presenti in tale punto di prelievo.
     *
     * @param IDCliente id del cliente
     *
     * @throws SQLException Errore causato da una query SQL
     */
    //todo controllare test
    public ArrayList<Ordine> getOrdini(int IDCliente) throws SQLException {
        ArrayList<Ordine> lista = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * from ordini\n" + "WHERE IDCliente = " + IDCliente +
                " AND IDPuntoPrelievo = '" + this.ID + "' and stato = " + StatoOrdine.IN_DEPOSITO + " ;");
        while (rs.next()) {
            Ordine ordine = new Ordine(rs.getInt("ID"));
            lista.add(ordine);
        }
        return lista;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.punti_prelievo where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.indirizzo = rs.getString("indirizzo");
            this.nome = rs.getString("nome");
        }
    }

}