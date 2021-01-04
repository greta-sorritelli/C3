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

    /**
     * @return ArrayList<String> dei dettagli del punto di prelievo.
     */
    public ArrayList<String> getDettagli() {
        ArrayList<String> dettagli = new ArrayList<>();
        dettagli.add((String.valueOf(getID())));
        dettagli.add(getNome());
        dettagli.add(getIndirizzo());
        return dettagli;
    }

    /**
     * @param IDOrdine  ID dell'ordine
     * @return          ArrayList<ArrayList<String>> dei dettagli della merce presente nel magazzino
     *                  che fa parte dell'ordine
     */
    //todo test
    public ArrayList<ArrayList<String>> getDettagliMerceMagazzino(int IDOrdine) {
        try {
            ArrayList<MerceOrdine> merceOrdine = new ArrayList<>();
            ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * from merci\n" + "where IDOrdine = " + IDOrdine + " and stato = " +
                    "'" + StatoOrdine.IN_DEPOSITO.toString() + "';");
            while (rs.next()) {
                MerceOrdine tmp = new MerceOrdine(rs.getInt("ID"), rs.getInt("IDOrdine"),
                        rs.getDouble("prezzo"), rs.getString("descrizione"),
                        rs.getInt("quantita"), StatoOrdine.valueOf(rs.getString("stato")));
                merceOrdine.add(tmp);
            }
            for (MerceOrdine m : merceOrdine) {
                dettagli.add(m.getDettagli());
            }
            return dettagli;
        } catch (SQLException exception) {
            //todo eccezione
            exception.printStackTrace();
        }
        return null;
    }

    public int getID() {
        return ID;
    }

    public String getIndirizzo() {
        return indirizzo;
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
                MerceOrdine merceOrdine = new MerceOrdine(rs.getInt("ID"),rs.getInt("IDOrdine"),
                        rs.getDouble("prezzo"),rs.getString("descrizione"),rs.getInt("quantita"),
                        StatoOrdine.valueOf(rs.getString("stato")));
                lista.add(merceOrdine);
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
            ResultSet rs = executeQuery("SELECT * from merci\n" + "where IDOrdine = " + IDOrdine + ";");
            while (rs.next()) {
                MerceOrdine merceOrdine = new MerceOrdine(rs.getInt("ID"),rs.getInt("IDOrdine"),
                        rs.getDouble("prezzo"),rs.getString("descrizione"),rs.getInt("quantita"),
                        StatoOrdine.valueOf(rs.getString("stato")));
                lista.add(merceOrdine);
            }
            return lista;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
            return null;
        }
    }

    public String getNome() {
        return nome;
    }

    /**
     * Ritorna l' insieme degli ordini effettuati dal cliente e presenti in tale punto di prelievo.
     *
     * @param IDCliente id del cliente
     */
    public ArrayList<Ordine> getOrdini(int IDCliente) {
        ArrayList<Ordine> lista = new ArrayList<>();
        try {
            ResultSet rs = executeQuery("SELECT * from ordini\n" +
                    "WHERE IDCliente = " + IDCliente + " AND IDPuntoPrelievo = \"" + this.ID + "\";");
            while (rs.next()) {
                Ordine ordine = new Ordine(rs.getInt("ID"),IDCliente, rs.getString("nomeCliente"),rs.getString("cognomeCliente"),
                        rs.getDouble("totalePrezzo"),StatoOrdine.valueOf(rs.getString("stato")), this.ID);
                lista.add(ordine);
            }
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
            return null;
        }
        return lista;
    }

}