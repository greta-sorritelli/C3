package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class ICommesso {
    Negozio negozio;
    GestoreOrdini gestoreOrdini;

    /**
     *
     * @param IDOrdine
     * @param indirizzo
     */
    public void addResidenza(int IDOrdine, String indirizzo) {
        //todo
    }

    //todo gestoreClienti
    private void creazioneCodice(Cliente cliente, Ordine ordine) {
        try {
            updateData("INSERT INTO `sys`.`codici_ritiro` (`codice`,`IDCliente`,`IDOrdine`,`dataCreazione`) \n" +
                    "VALUES ('" + cliente.getCodiceRitiro() + "', '" + cliente.getID() + "', '" + ordine.getID() + "', '" + cliente.getDataCreazioneCodice() + "');");
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    /**
     * Genera il nuovo {@code Codice di Ritiro} del {@link Cliente}.
     *
     * @return il Codice generato
     */
    private String generaCodiceRitiro() {
        Random rand = new Random();
        String tmp = "";
        for (int i = 0; i < 12; i++)
            tmp = tmp.concat(String.valueOf(rand.nextInt(10)));
        return tmp;
    }

    /**
     * @return
     */
    public ArrayList<String> getDettagliMagazziniDisponibili() {
     //todo
        return null;
    }

    /**
     * @param ID       Descrizione della merce
     * @param quantita Quantita della merce
     * @param ordine   Ordine in cui registrare la merce
     */
    //todo IDOrdine
    public void registraMerce(int ID, int quantita, Ordine ordine) {

        gestoreOrdini.registraMerce(ID, quantita, ordine);
    }

    /**
     * @param IDCliente ID del Cliente
     * @param Nome      Nome del Cliente
     * @param Cognome   Cognome del Cliente
     */
    void registraOrdine(int IDCliente, String Nome, String Cognome) {
        gestoreOrdini.registraOrdine(IDCliente, Nome, Cognome);
    }

    public void riceviPagamento() {
        //todo
    }

    public void sceltaCorriere() {
        //todo
    }

    public void sceltaPuntoPrelievo(int ID){
        //todo
    }

    public void selezionaCorriere(int ID) {
        //todo
    }

    public void selezionaPuntoPrelievo(int IDOrdine){
        //todo
    }

    /**
     * Imposta il negozio collegato all' interfaccia.
     *
     * @param negozio Negozio da impostare
     */
    public void setNegozio(Negozio negozio) {

        this.negozio = negozio;
    }

    /**
     * @param ordine
     * @param magazzino
     */
    //todo IDOrdine
    public void setPuntoPrelievo(Ordine ordine, PuntoPrelievo magazzino) {
        try {
            ordine.setPuntoPrelievo(magazzino);
            updateData("UPDATE `sys`.`ordini` SET `puntoPrelievo` = '" + magazzino.getNome() + "' WHERE (`ID` = '" + ordine.getID() + "');");
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    /**
     * @param ordine
     * @param statoOrdine
     */
    //todo IDOrdine
    public void setStatoOrdine(Ordine ordine, StatoOrdine statoOrdine) {
        try {
            ordine.setStato(statoOrdine);
            updateData("UPDATE `sys`.`ordini` SET `stato` = '" + statoOrdine + "' WHERE (`ID` = '" + ordine.getID() + "');");
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    /**
     *
     * @param ordine
     */
    //todo IDOrdine
    public void terminaOrdine(Ordine ordine){
        gestoreOrdini.terminaOrdine(ordine);
    }

    /**
     *
     * @param cliente
     * @param ordine
     * @return
     */
    //todo IDOrdine IDCliente
    public String verificaEsistenzaCodice(Cliente cliente, Ordine ordine) {
        try {
            ResultSet rs = executeQuery("select dataCreazione from sys.clienti where ID = " + cliente.getID() + ";");
            if (rs.next()) {
                String date = rs.getString("dataCreazione");
                String dataOdierna = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
                if (Objects.isNull(date) || !date.equals(dataOdierna)) {
                    cliente.setCodiceRitiro(generaCodiceRitiro());
                    this.creazioneCodice(cliente, ordine);
                    return cliente.getCodiceRitiro();
                }
                return cliente.getCodiceRitiro();
            }
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return cliente.getCodiceRitiro();
    }


}