package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class ICommesso {

    void compilazioneOrdine() {
    }

    /**
     * @param IDCliente
     * @param Nome
     * @param Cognome
     */
    void registraOrdine(String IDCliente, String Nome, String Cognome) {
    }

    /**
     * @param descrizione
     * @param quantita
     * @param prezzo
     */
    void registraMerce(String descrizione, int quantita, double prezzo) {
    }

    void riceviPagamento() {
    }

    void sceltaCorriere() {
    }

    Corriere selezionaCorriere() {
        return null;
    }

    /**
     * @param via
     * @param NCivico
     */
    void addResidenza(String via, int NCivico) {
    }

    void getMagazziniDisponibili() {
    }

    /**
     * @param magazzino
     */
    void setPuntodiPrelievo(PuntoPrelievo magazzino) {
    }

    /**
     * @param RITIRATO
     */
    void setStatoOrdine(StatoOrdine RITIRATO) {
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

    /**
     * @return
     */
    String generaCodiceRitiro() {
        Random rand = new Random();
        String tmp = "";
        for (int i = 0; i < 8; i++)
            tmp = tmp.concat(String.valueOf(rand.nextInt(10)));
        return tmp;
    }

}