package it.unicam.cs.ids.C3.TeamMGC.negozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

/**
 * Classe per la creazione di un {@link Negozio}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class Negozio {
    private final ArrayList<Merce> inventario = new ArrayList<>();
    //todo ID
    private int IDNegozio;
    private String nome;
    private String categoria;
    private String orarioApertura;
    private String orarioChiusura;
    private String indirizzo;
    private String telefono;

    /**
     * Costruttore per inserire i dati nel DB
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    public Negozio(String nome, String categoria, String orarioApertura, String orarioChiusura, String indirizzo, String telefono) throws SQLException {
        updateData("INSERT INTO sys.negozi (nome, categoria, orarioApertura, orarioChiusura, indirizzo, telefono) " +
                "VALUES ('" + nome + "', '" + categoria + "', '" + orarioApertura + "', '" + orarioChiusura + "', '" +
                indirizzo + "', '" + telefono + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from negozi;");
        rs.next();
        IDNegozio = rs.getInt("ID");
        this.nome = nome;
        this.categoria = categoria;
        this.orarioApertura = orarioApertura;
        this.orarioChiusura = orarioChiusura;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
    }

    /**
     * Costruttore per importare i dati dal DB
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    public Negozio(int IDNegozio) throws SQLException {
        ResultSet rs = executeQuery("select * from negozi where ID ='" + IDNegozio + "';");
        if (rs.next()) {
            this.IDNegozio = IDNegozio;
            this.nome = rs.getString("nome");
            this.categoria = rs.getString("categoria");
            this.orarioApertura = rs.getString("orarioApertura");
            this.orarioChiusura = rs.getString("orarioChiusura");
            this.indirizzo = rs.getString("indirizzo");
            this.telefono = rs.getString("telefono");
        } else
            throw new IllegalArgumentException("ID non valido.");
    }

    /**
     * Controlla se la {@link Merce} che si vuole creare e' gia' presente nell' Inventario. Se non e' presente
     * viene creata e aggiunta all' Inventario del {@link Negozio}.
     *
     * @return la Merce
     * @throws SQLException Errore causato da una query SQL
     */
    private Merce addMerceInventario(ResultSet rs) throws SQLException {
        for (Merce merce : inventario)
            if (merce.getID() == rs.getInt("ID"))
                return merce;
        Merce toReturn = new Merce(rs.getInt("ID"), rs.getInt("IDNegozio"),
                rs.getDouble("prezzo"), rs.getString("descrizione"),
                rs.getInt("quantita"));
        addMerceToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link Merce} all' inventario.
     *
     * @param merce Merce da aggiungere
     */
    private void addMerceToList(Merce merce) {
        if (!inventario.contains(merce))
            inventario.add(merce);
    }

    public String getCategoria() {
        return categoria;
    }

    /**
     * Ritorna la lista dei dettagli del {@link Negozio } presente nel DB.
     *
     * @return ArrayList<String> dei dettagli del negozio.
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(String.valueOf(getIDNegozio()));
        toReturn.add(getNome());
        toReturn.add(getCategoria());
        toReturn.add(getOrarioApertura());
        toReturn.add(getOrarioChiusura());
        toReturn.add(getIndirizzo());
        toReturn.add(getTelefono());
        toReturn.add(String.valueOf(getInventario()));
        return toReturn;

    }

    public int getIDNegozio() {
        return IDNegozio;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public ArrayList<Merce> getInventario() {
        return inventario;
    }

    /**
     * Ritorna la {@link Merce} collegata all' {@code ID}.
     *
     * @param ID Codice Identificativo della Merce
     * @return la Merce desiderata
     * @throws SQLException Errore causato da una query SQL
     */
    public Merce getMerce(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.inventario where ID='" + ID + "' and IDNegozio = '" +
                this.IDNegozio + "' ;");
        if (rs.next())
            return addMerceInventario(rs);
        else
            throw new IllegalArgumentException("ID non valido.");
    }

    /**
     * Ritorna tutta la {@link Merce} all' interno del {@link Negozio}.
     *
     * @return l'elenco della Merce del Negozio
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<Merce> getMerceDisponibile() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.inventario where IDNegozio='" + IDNegozio + "';");
        while (rs.next())
            addMerceInventario(rs);
        return inventario;
    }

    public String getNome() {
        return nome;
    }

    public String getOrarioApertura() {
        return orarioApertura;
    }

    public String getOrarioChiusura() {
        return orarioChiusura;
    }

    public String getTelefono() {
        return telefono;
    }

    /**
     * Crea e inserisce una nuova {@link Merce} all 'interno dell' inventario.
     *
     * @param prezzo      Prezzo della merce da inserire
     * @param descrizione Descrizione della merce da inserire
     * @param quantita    Quantita della merce da inserire
     * @return ArrayList<String> dei dettagli della merce creata
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> inserisciNuovaMerce(double prezzo, String descrizione, int quantita) throws SQLException {
        Merce merce = new Merce(this.IDNegozio, prezzo, descrizione, quantita);
        addMerceToList(merce);
        return merce.getDettagli();
    }

    /**
     * Rimuove la {@link Merce} dall'inventario.
     *
     * @param IDMerce ID della Merce da rimuovere.
     * @throws SQLException Errore causato da una query SQL
     */
    public void removeMerce(int IDMerce) throws SQLException {
        inventario.remove(getMerce(IDMerce));
        getMerce(IDMerce).delete();
    }

    /**
     * Seleziona la {@link Merce} tramite l' {@code ID}.
     *
     * @param IDMerce ID della merce da selezionare
     * @return ArrayList<String> dei dettagli della merce
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> selezionaMerce(int IDMerce) throws SQLException {
        return getMerce(IDMerce).getDettagli();
    }

    public void setQuantita(int IDMerce, int quantita) throws SQLException {
        getMerce(IDMerce).setQuantita(quantita);
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.negozi where ID= '" + this.IDNegozio + "';");
        if (rs.next()) {
            this.nome = rs.getString("nome");
            this.categoria = rs.getString("categoria");
            this.orarioApertura = rs.getString("orarioApertura");
            this.orarioChiusura = rs.getString("orarioChiusura");
            this.indirizzo = rs.getString("indirizzo");
            this.telefono = rs.getString("telefono");
        }
    }

}