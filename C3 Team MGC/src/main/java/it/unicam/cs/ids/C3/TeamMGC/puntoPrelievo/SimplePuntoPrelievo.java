package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.ordine.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * La classe implementa l' interfaccia {@link PuntoPrelievo} ed ha la responsabilità di gestire un
 * Punto di Prelievo.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class SimplePuntoPrelievo implements PuntoPrelievo {
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
    public SimplePuntoPrelievo(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from punti_prelievo where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.indirizzo = rs.getString("indirizzo");
            this.nome = rs.getString("nome");
            disconnectToDB(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @param indirizzo Indirizzo del Punto di Prelievo
     * @param nome      Nome del Punto di Prelievo
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    public SimplePuntoPrelievo(String indirizzo, String nome) throws SQLException {
        updateData("INSERT INTO sys.punti_prelievo (nome,indirizzo) \n" +
                "VALUES ('" + nome + "' , '" + indirizzo + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from punti_prelievo;");
        rs.next();
        this.ID = rs.getInt("ID");
        this.nome = nome;
        this.indirizzo = indirizzo;
        disconnectToDB(rs);
    }

    //todo commento e test
    @Override
    public int compareTo(PuntoPrelievo o) {
        if (Objects.isNull(o))
            throw new NullPointerException();
        if (this.equals(o))
            return 0;
        if (this.getID() > o.getID())
            return 1;
        else return -1;
    }

    /**
     * Elimina il {@link SimplePuntoPrelievo} dal DB e aggiorna i dati dell' oggetto.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void delete() throws SQLException {
        updateData("DELETE FROM sys.punti_prelievo WHERE (ID = '" + ID + "');");
        this.ID = -1;
        this.nome = "";
        this.indirizzo = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuntoPrelievo that = (SimplePuntoPrelievo) o;
        return getID() == that.getID();
    }

    /**
     * Ritorna un arraylist con i dettagli del {@link SimplePuntoPrelievo}.
     *
     * @return ArrayList dei dettagli
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    @Override
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> dettagli = new ArrayList<>();
        dettagli.add((String.valueOf(getID())));
        dettagli.add(getNome());
        dettagli.add(getIndirizzo());
        return dettagli;
    }

    /**
     * Ritorna il Codice Identificativo del {@link SimplePuntoPrelievo}.
     *
     * @return l'ID del Punto di Prelievo
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Ritorna l'Indirizzo del {@link SimplePuntoPrelievo}.
     *
     * @return l'indirizzo del Punto di Prelievo
     */
    @Override
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Ritorna la lista di tutte le merci appartenenti a tale ordine e presenti nel {@link SimplePuntoPrelievo}.
     *
     * @param IDOrdine ID dell' ordine
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<MerceOrdine> getMerceMagazzino(int IDOrdine) throws SQLException {
        ArrayList<MerceOrdine> lista = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * from merci\n" +
                "where IDOrdine = " + IDOrdine + " and stato = '" + StatoOrdine.IN_DEPOSITO.toString() + "';");
        while (rs.next()) {
            MerceOrdine simpleMerceOrdine = new SimpleMerceOrdine(rs.getInt("ID"));
            lista.add(simpleMerceOrdine);
        }
        disconnectToDB(rs);
        return lista;
    }

    /**
     * Ritorna il Nome del {@link SimplePuntoPrelievo}.
     *
     * @return in nome del Punto di Prelievo
     */
    @Override
    public String getNome() {
        return nome;
    }

    /**
     * Ritorna l' insieme degli ordini effettuati dal cliente e presenti in tale {@link SimplePuntoPrelievo}.
     *
     * @param IDCliente ID del cliente
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<Ordine> getOrdini(int IDCliente) throws SQLException {
        ArrayList<Ordine> lista = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * from ordini WHERE IDCliente = '" + IDCliente +
                "' AND IDPuntoPrelievo = '" + this.ID + "' and stato = '" + StatoOrdine.IN_DEPOSITO + "' ;");
        while (rs.next()) {
            Ordine simpleOrdine = new SimpleOrdine(rs.getInt("ID"));
            for (MerceOrdine merciToAdd : getMerceMagazzino(simpleOrdine.getID()))
                simpleOrdine.addMerce(merciToAdd);
            lista.add(simpleOrdine);
        }
        disconnectToDB(rs);
        return lista;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", indirizzo='" + indirizzo + '\'' +
                ", nome='" + nome;
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.punti_prelievo where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.indirizzo = rs.getString("indirizzo");
            this.nome = rs.getString("nome");
        }
        disconnectToDB(rs);
    }

}