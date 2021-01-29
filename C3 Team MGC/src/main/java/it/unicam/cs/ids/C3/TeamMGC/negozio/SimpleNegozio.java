package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la creazione di un {@link SimpleNegozio}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class SimpleNegozio implements Negozio {
    private final ArrayList<Merce> inventario = new ArrayList<>();
    private int ID;
    private String nome;
    private CategoriaNegozio categoria;
    private String orarioApertura;
    private String orarioChiusura;
    private String indirizzo;
    private String telefono;

    /**
     * Costruttore per inserire i dati nel DB
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    public SimpleNegozio(String nome, CategoriaNegozio categoria, String orarioApertura, String orarioChiusura, String indirizzo, String telefono) throws SQLException {
        updateData("INSERT INTO sys.negozi (nome, categoria, orarioApertura, orarioChiusura, indirizzo, telefono) " +
                "VALUES ('" + nome + "', '" + categoria + "', '" + orarioApertura + "', '" + orarioChiusura + "', '" +
                indirizzo + "', '" + telefono + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from negozi;");
        rs.next();
        ID = rs.getInt("ID");
        this.nome = nome;
        this.categoria = categoria;
        this.orarioApertura = orarioApertura;
        this.orarioChiusura = orarioChiusura;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        disconnectToDB(rs);
    }

    /**
     * Costruttore per importare i dati dal DB
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    public SimpleNegozio(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from negozi where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.nome = rs.getString("nome");
            this.categoria = CategoriaNegozio.valueOf(rs.getString("categoria"));
            this.orarioApertura = rs.getString("orarioApertura");
            this.orarioChiusura = rs.getString("orarioChiusura");
            this.indirizzo = rs.getString("indirizzo");
            this.telefono = rs.getString("telefono");
            disconnectToDB(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Controlla se la {@link Merce} che si vuole creare e' gia' presente nell' Inventario. Se non e' presente
     * viene creata e aggiunta all' Inventario del {@link SimpleNegozio}.
     *
     * @return la Merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
    private Merce addMerceInventario(ResultSet rs) throws SQLException {
        for (Merce simpleMerce : inventario)
            if (simpleMerce.getID() == rs.getInt("ID"))
                return simpleMerce;
        Merce toReturn = new SimpleMerce(rs.getInt("ID"));
        addMerceToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link Merce} all' inventario.
     *
     * @param simpleMerce Merce da aggiungere
     */
    private void addMerceToList(Merce simpleMerce) {
        if (!inventario.contains(simpleMerce))
            inventario.add(simpleMerce);
    }

    /**
     * Elimina il {@link SimpleNegozio} dal db e aggiorna i dati dell' oggetto.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    //todo test
    public void delete() throws SQLException {
        updateData("DELETE FROM sys.negozi WHERE (ID = '" + ID + "');");
        this.ID = -1;
        this.nome = "";
        this.categoria = null;
        this.telefono = "";
        this.indirizzo = "";
        this.orarioApertura = "";
        this.orarioChiusura = "";
    }

    //todo test e commento
    @Override
    public void eliminaPromozione(int IDMerce) throws SQLException {
        ResultSet rs = executeQuery("SELECT prezzoPrecedente FROM sys.promozioni where IDMerce = " + IDMerce + ";");
        double prezzoPrecedente;
        if (rs.next())
            prezzoPrecedente = rs.getInt("prezzoPrecedente");
        else
            throw new IllegalArgumentException("IDMerce non valido.");
        updateData("DELETE FROM sys.promozioni WHERE (IDNegozio = '" + ID + "') and (IDMerce = '" + IDMerce + "');");
        getItem(IDMerce).setPrezzo(prezzoPrecedente);
    }

    @Override
    public CategoriaNegozio getCategoria() {
        return categoria;
    }

    @Override
    public void setCategoria(CategoriaNegozio categoria) throws SQLException {
        updateData("UPDATE sys.negozi SET categoria = '" + categoria + "' WHERE (ID = '" + getID() + "');");
        this.categoria = categoria;
    }

    /**
     * Ritorna la lista dei dettagli del {@link SimpleNegozio} presente nel DB.
     *
     * @return ArrayList dei dettagli del negozio.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(String.valueOf(getID()));
        toReturn.add(getNome());
        toReturn.add(String.valueOf(getCategoria()));
        toReturn.add(getOrarioApertura());
        toReturn.add(getOrarioChiusura());
        toReturn.add(getIndirizzo());
        toReturn.add(getTelefono());
        toReturn.add(String.valueOf(getInventario()));
        return toReturn;

    }

    /**
     * Ritorna la lista dei dettagli di tutta la {@link Merce} all' interno del {@link SimpleNegozio}.
     *
     * @return ArrayList di ArrayList dei dettagli della merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT ID FROM sys.inventario where IDNegozio='" + ID + "';");
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        while (rs.next())
            toReturn.add(addMerceInventario(rs).getDettagli());
        disconnectToDB(rs);
        return toReturn;
    }

    //todo test e commento
    @Override
    public ArrayList<ArrayList<String>> getDettagliPromozioni() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.promozioni where IDNegozio = " + ID + ";");
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        while (rs.next()) {
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add(rs.getString("IDMerce"));
            tmp.add(rs.getString("messaggio"));
            tmp.add(rs.getString("prezzoAttuale"));
            tmp.add(rs.getString("prezzoPrecedente"));
            toReturn.add(tmp);
        }
        return toReturn;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String getIndirizzo() {
        return indirizzo;
    }

    @Override
    public ArrayList<Merce> getInventario() throws SQLException {
        update();
        return inventario;
    }

    /**
     * Ritorna la {@link Merce} collegata all' {@code ID}.
     *
     * @param ID Codice Identificativo della Merce
     *
     * @return la Merce desiderata
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public Merce getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.inventario where ID='" + ID + "' and IDNegozio = '" +
                this.ID + "' ;");
        if (rs.next()) {
            Merce simpleMerce = addMerceInventario(rs);
            disconnectToDB(rs);
            return simpleMerce;
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Ritorna tutta la {@link Merce} all' interno del {@link SimpleNegozio}.
     *
     * @return l'elenco della Merce del Negozio
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<Merce> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.inventario where IDNegozio='" + ID + "';");
        while (rs.next())
            addMerceInventario(rs);
        disconnectToDB(rs);
        return new ArrayList<>(inventario);
    }

    /**
     * Calcola la percentuale della merce venduta rispetto all' inventario del {@link Negozio}.
     *
     * @return La percentuale della merce venduta.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public int getMerceVenduta() throws SQLException {
        ArrayList<ArrayList<String>> tmp = GestoreOrdini.getInstance().getDettagliMerciNegozio(ID);
        if (tmp.isEmpty() && getInventario().isEmpty())
            return 0;
        if (tmp.isEmpty() && !getInventario().isEmpty())
            return 0;
        if (!tmp.isEmpty() && getInventario().isEmpty())
            return 100;
        if (!tmp.isEmpty() && !getInventario().isEmpty()) {
            int merceVenduta = 0;
            int merceInventario;

            for (ArrayList<String> merce : tmp)
                merceVenduta += Integer.parseInt(merce.get(4));

            merceInventario = merceVenduta;

            for (Merce merce : getInventario())
                merceInventario += merce.getQuantita();

            return (merceVenduta * 100) / merceInventario;
        }
        return 0;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getOrarioApertura() {
        return orarioApertura;
    }

    @Override
    public String getOrarioChiusura() {
        return orarioChiusura;
    }

    /**
     * Calcola il prezzo medio della merce del {@link Negozio}.
     *
     * @return Prezzo medio.
     */
    @Override
    public Double getPrezzoMedio() throws SQLException {
        update();
        double prezzo = 0;
        int contatore = 0;
        for (Merce merce : getInventario()) {
            prezzo += merce.getPrezzo();
            contatore++;
        }
        return prezzo / contatore;
    }

    //todo test e commento
    @Override
    public ArrayList<String> getPromozione(int IDMerce) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.promozioni where IDMerce = " + IDMerce + ";");
        ArrayList<String> toReturn = new ArrayList<>();
        if (rs.next()) {
            toReturn.add(rs.getString("IDMerce"));
            toReturn.add(rs.getString("messaggio"));
            toReturn.add(rs.getString("prezzoAttuale"));
            toReturn.add(rs.getString("prezzoPrecedente"));
        } else
            throw new IllegalArgumentException("IDMerce non valido.");
        return toReturn;
    }

    @Override
    public String getTelefono() {
        return telefono;
    }

    /**
     * Crea e inserisce una nuova {@link Merce} all 'interno dell' inventario.
     *
     * @param prezzo      Prezzo della merce da inserire
     * @param descrizione Descrizione della merce da inserire
     * @param quantita    Quantita della merce da inserire
     *
     * @return ArrayList dei dettagli della merce creata
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<String> inserisciNuovaMerce(double prezzo, String descrizione, int quantita) throws SQLException {
        Merce simpleMerce = new SimpleMerce(this.ID, prezzo, descrizione, quantita);
        addMerceToList(simpleMerce);
        return simpleMerce.getDettagli();
    }

    //todo test e commento
    @Override
    public void lanciaPromozione(int IDMerce, double nuovoPrezzo, String messaggio) throws SQLException {
        Merce tmp = getItem(IDMerce);
        updateData("INSERT INTO sys.promozioni (IDNegozio, IDMerce, Messaggio, prezzoAttuale, prezzoPrecedente) VALUES ('" + ID + "', '" + IDMerce
                + "', '" + messaggio + "', '" + nuovoPrezzo + "', '" + tmp.getPrezzo() + "');");
        tmp.setPrezzo(nuovoPrezzo);
    }

    /**
     * Rimuove la {@link Merce} dall' inventario.
     *
     * @param IDMerce ID della Merce da rimuovere.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void removeMerce(int IDMerce) throws SQLException {
        Merce toDelete = getItem(IDMerce);
        inventario.remove(toDelete);
        toDelete.delete();
    }

    //todo test e commento
    @Override
    public void setNuoviDatiPromozione(int IDMerce, double prezzo, String messaggio) throws SQLException {
        updateData("UPDATE sys.promozioni SET messaggio = '" + messaggio + "', prezzoAttuale = '" + prezzo + "' WHERE (IDNegozio = '" + ID + "') and (IDMerce = '" + IDMerce + "');");
    }

    @Override
    public void setQuantitaMerce(int IDMerce, int quantita) throws SQLException {
        getItem(IDMerce).setQuantita(quantita);
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", nome='" + nome + '\'' +
                ", categorie=" + categoria +
                ", indirizzo='" + indirizzo + '\'';
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.negozi where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.nome = rs.getString("nome");
            this.categoria = CategoriaNegozio.valueOf(rs.getString("categoria"));
            this.orarioApertura = rs.getString("orarioApertura");
            this.orarioChiusura = rs.getString("orarioChiusura");
            this.indirizzo = rs.getString("indirizzo");
            this.telefono = rs.getString("telefono");

            rs = executeQuery("select ID from inventario where IDNegozio ='" + ID + "';");
            while (rs.next())
                addMerceInventario(rs);
        }
        disconnectToDB(rs);
    }

}