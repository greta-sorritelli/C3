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
    void addResidenza(String via, int NCivico) {
    }

    /**
     * Ritorna la {@link Merce} all' interno dell' inventario del {@link Negozio}.
     *
     * @return l'elenco della Merce
     */
    public ArrayList<Merce> compilazioneOrdine() {
        return negozio.getMerceDisponibile();
    }

    /**
     * Genera il nuovo {@code Codice di Ritiro} del {@link Cliente}.
     *
     * @return il Codice generato
     */
    private String generaCodiceRitiro() {
        Random rand = new Random();
        String tmp = "";
        for (int i = 0; i < 8; i++)
            tmp = tmp.concat(String.valueOf(rand.nextInt(10)));
        return tmp;
    }

    void getMagazziniDisponibili() {

    }

    /**
     * @param descrizione    Descrizione della merce
     * @param quantita       Quantita della merce
     */
    void registraMerce(String descrizione, int quantita) {
        gestoreOrdine.registraMerce(descrizione,quantita);
    }

    /**
     * @param IDCliente ID del Cliente
     * @param Nome      Nome del Cliente
     * @param Cognome   Cognome del Cliente
     */
    void registraOrdine(Ordine ordine, int IDCliente, String Nome, String Cognome) {
        gestoreOrdine.registraOrdine(IDCliente, Nome, Cognome);
    }

    void riceviPagamento() {
    }

    void sceltaCorriere() {
    }

    Corriere selezionaCorriere() {
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
    void setPuntodiPrelievo(Ordine ordine,PuntoPrelievo magazzino) {
        try {
        ordine.setPuntoPrelievo(magazzino);
        updateData("UPDATE `sys`.`ordini` SET `puntoPrelievo` = '" + magazzino.getNome() + "' WHERE (`ID` = '" + ordine.getID()+ "');");
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    /**
     * @param ordine
     * @param statoOrdine
     */
    void setStatoOrdine(Ordine ordine,StatoOrdine statoOrdine) {
        try {
            ordine.setStato(statoOrdine); ;
            updateData("UPDATE `sys`.`ordini` SET `stato` = '" + statoOrdine + "' WHERE (`ID` = '" + ordine.getID() + "');");
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    boolean verificaValiditaCodice(Cliente cliente) {
        try {
            ResultSet rs = executeQuery("select dataCreazione from sys.clienti where ID = " + cliente.getID() + ";");
            if (rs.next()) {
                String date = rs.getString("dataCreazione");
                String dataOdierna = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
                if (Objects.isNull(date) || !date.equals(dataOdierna)) {
                    cliente.setCodiceRitiro(generaCodiceRitiro());
                    return false;
                }
                return true;
            }
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return false;
    }

}