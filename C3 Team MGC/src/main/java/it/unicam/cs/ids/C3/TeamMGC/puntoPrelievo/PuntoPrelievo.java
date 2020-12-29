package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class PuntoPrelievo {

    private int ID = 0;
    private String indirizzo = null;
    private String nome = null;
//    private IMagazziniere magazziniere = null;


    public PuntoPrelievo(int ID, String indirizzo, String nome) {
        this.ID = ID;
        this.indirizzo = indirizzo;
        this.nome = nome;
    }

    public PuntoPrelievo(String indirizzo, String nome) {
        try {
            updateData("INSERT INTO `sys`.`punti_prelievo` (`nome`,`indirizzo`) \n" +
                    "VALUES ('" + nome + "' , '" + indirizzo + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from punti_prelievo;");
            rs.next();
            this.ID = rs.getInt("ID");
            this.nome = nome;
            this.indirizzo = indirizzo;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

//    public void setMagazziniere(IMagazziniere magazziniere) {
//        try {
//            updateData("UPDATE `sys`.`punti_prelievo` SET `magazziniere` = '" + magazziniere.getID() + "' WHERE (`ID` = '" + this.ID + "');");
//            this.magazziniere = magazziniere;
//        } catch (SQLException exception) {
//            //TODO
//            exception.printStackTrace();
//        }
//        this.magazziniere = magazziniere;
//
//    }


    public String getNome() {
        return nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Ritorna l'insieme degli ordini effettuati dal cliente e presenti in tale punto di prelievo
     *
     * @param IDCliente    id del cliente
     */
    public ArrayList<Ordine> getOrdini(int IDCliente) {
        ArrayList<Ordine> lista = new ArrayList<>();
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