package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class ICommesso {
    Negozio negozio;
    GestoreOrdine gestoreOrdine;

    /**
     * @param via
     * @param NCivico
     */
    public void addResidenza(String via, int NCivico) {
        //todo
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

    public void getMagazziniDisponibili() {
     //todo
    }

    /**
     * @param ID       Descrizione della merce
     * @param quantita Quantita della merce
     * @param ordine   Ordine in cui registrare la merce
     */
    public void registraMerce(int ID, int quantita, Ordine ordine) {

        gestoreOrdine.registraMerce(ID, quantita, ordine);
    }

    /**
     * @param IDCliente ID del Cliente
     * @param Nome      Nome del Cliente
     * @param Cognome   Cognome del Cliente
     */
    void registraOrdine(int IDCliente, String Nome, String Cognome) {
        gestoreOrdine.registraOrdine(IDCliente, Nome, Cognome);
    }

    public void riceviPagamento() {
        //todo
    }

    public void sceltaCorriere() {
        //todo
    }

    public Corriere selezionaCorriere() {
        //todo
        return null;
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
    public void setStatoOrdine(Ordine ordine, StatoOrdine statoOrdine) {
        try {
            ordine.setStato(statoOrdine);
            updateData("UPDATE `sys`.`ordini` SET `stato` = '" + statoOrdine + "' WHERE (`ID` = '" + ordine.getID() + "');");
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

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

    private void creazioneCodice(Cliente cliente, Ordine ordine) {
        try {
            updateData("INSERT INTO `sys`.`codici_ritiro` (`codice`,`IDCliente`,`IDOrdine`,`dataCreazione`) \n" +
                    "VALUES ('" + cliente.getCodiceRitiro() + "', '" + cliente.getID() + "', '" + ordine.getID() + "', '" + cliente.getDataCreazioneCodice() + "');");
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

}