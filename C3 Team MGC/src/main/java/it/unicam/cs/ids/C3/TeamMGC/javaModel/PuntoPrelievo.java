package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class PuntoPrelievo {

    private final String indirizzo;
    private final String nome;
    private IMagazziniere magazziniere = null;

    public PuntoPrelievo(String indirizzo, String nome) {
        this.indirizzo = indirizzo;
        this.nome = nome;
    }

    public void setMagazziniere(IMagazziniere magazziniere) {
        this.magazziniere = magazziniere;
    }

    public IMagazziniere getMagazziniere() {
        return magazziniere;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Ritorna l'insieme degli ordini effettuati dal cliente e presenti in tale punto di prelievo
     *
     * @param IDCliente    id del cliente
     * @param codiceRitiro codice necessario per ritirare gli ordini
     */
    public ArrayList<Ordine> getOrdini(int IDCliente, int codiceRitiro) {
        ArrayList<Ordine> lista = new ArrayList<>();
        if (magazziniere.verificaCodice(codiceRitiro)) {
            try {
                ResultSet rs = executeQuery("SELECT * from ordini\n" +
                        "WHERE IDCliente = " + IDCliente + " AND puntoPrelievo = \"" + this.nome + "\";");
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String nomeCliente = rs.getString("nomeCliente");
                    String cognomeCliente = rs.getString("cognomeCliente");
                    double totalePrezzo = rs.getDouble("totalePrezzo");
                    StatoOrdine stato = StatoOrdine.valueOf(rs.getString("stato"));
                    lista.add(new Ordine(id, IDCliente, nomeCliente, cognomeCliente, totalePrezzo, stato, this));
                }
            } catch (SQLException exception) {
                //todo
                exception.printStackTrace();
                return null;
            }
        }
        return lista;
    }

    /**
     * Ritorna la lista di tutte le merci appartenenti a tale ordine e presenti nel punto di prelievo
     *
     * @param IDOrdine id dell'ordine
     */
    public ArrayList<MerceOrdine> getMerceMagazzino(int IDOrdine) {
        ArrayList<MerceOrdine> lista = new ArrayList<>();
        try {
            ResultSet rs = executeQuery("SELECT * from merci\n" +
                    "where IDOrdine = " + IDOrdine + " and stato = '" + StatoOrdine.IN_DEPOSITO.toString() + "';");
            while (rs.next()) {
                int id = rs.getInt("ID");
                int idOrdine = rs.getInt("IDOrdine");
                double prezzo = rs.getDouble("prezzo");
                String descrizione = rs.getString("descrizione");
                int quantita = rs.getInt("quantita");
                StatoOrdine stato = StatoOrdine.valueOf(rs.getString("stato"));
                lista.add(new MerceOrdine(id, idOrdine, prezzo, descrizione, quantita, stato));
            }
            return lista;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * Ritorna la lista di tutte le merci appartenenti a tale ordine
     *
     * @param IDOrdine id dell'ordine
     */
    public ArrayList<MerceOrdine> getMerceTotale(int IDOrdine) {
        ArrayList<MerceOrdine> lista = new ArrayList<>();
        try {
            ResultSet rs = executeQuery("SELECT * from merci\n" +
                    "where IDOrdine = " + IDOrdine + ";");
            while (rs.next()) {
                int id = rs.getInt("ID");
                int idOrdine = rs.getInt("IDOrdine");
                double prezzo = rs.getDouble("prezzo");
                String descrizione = rs.getString("descrizione");
                int quantita = rs.getInt("quantita");
                StatoOrdine stato = StatoOrdine.valueOf(rs.getString("stato"));
                lista.add(new MerceOrdine(id, idOrdine, prezzo, descrizione, quantita, stato));
            }
            return lista;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
            return null;
        }
    }


    public ArrayList<String> getDettagli() {
        ArrayList<String> dettagli = new ArrayList<>();
        dettagli.add(this.nome);
        dettagli.add(this.indirizzo);
        return dettagli;
    }

}