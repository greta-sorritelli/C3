package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class GestoreOrdine {

    private final Negozio negozio;

    public GestoreOrdine(Negozio negozio) {
        this.negozio = negozio;
    }

    /**
     * Registra l'{@link Ordine} con i dati del {@link Cliente} e cambia il suo {@code stato} in "PAGATO.
     *
     * @param IDCliente ID del cliente a cui appartiene l'ordine
     * @param nome      Nome del cliente a cui appartiene l'ordine
     * @param cognome   Cognome del cliente a cui appartiene l'ordine
     * @return L'ordine registrato
     */
    public Ordine registraOrdine(int IDCliente, String nome, String cognome) {
        controllaCliente(IDCliente, nome, cognome);
        return new Ordine(IDCliente, nome, cognome);
    }

    /**
     * Controlla se il {@link Cliente} è già presente nel database.
     *
     * @param IDCliente ID del cliente
     * @param nome      Nome del cliente
     * @param cognome   Cognome del cliente
     * @throws IllegalArgumentException Se il cliente non è presente nel database
     */
    private void controllaCliente(int IDCliente, String nome, String cognome) {
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.clienti where ID = '" + IDCliente + "' and nome = '" + nome + "' and cognome = '" + cognome + "';");
            if (!rs.next())
                //todo eccezione
                throw new IllegalArgumentException();
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    /**
     * Registra la {@link Merce} nell'{@link Ordine} creato.
     *
     * @param IDMerce  ID della merce
     * @param quantita Quantita della merce
     * @param ordine   Ordine in cui registrare la merce
     */
    public void registraMerce(int IDMerce, int quantita, Ordine ordine) {
        Merce merce = negozio.getMerce(IDMerce);
        if(merce.getQuantita() == 0 || merce.getQuantita() < quantita)
            throw new IllegalArgumentException();

        merce.setQuantita(merce.getQuantita() - quantita);
        MerceOrdine merceOrdine = new MerceOrdine(merce.getPrezzo(), merce.getDescrizione(), StatoOrdine.PAGATO);
        ordine.aggiungiMerce(merceOrdine, quantita);
        merceOrdine.setIDOrdine(ordine.getID());
    }

    /**
     * Imposta lo stato dell'{@link Ordine} come pagato se tutta la {@link MerceOrdine} in esso è pagata.
     * @param ordine    Ordine da controllare
     */
    public void terminaOrdine(Ordine ordine) {
        ArrayList<MerceOrdine> mercePagata = new ArrayList<>();
        for (MerceOrdine m : ordine.getMerci()) {
            if (m.getStato() == StatoOrdine.PAGATO)
                mercePagata.add(m);
        }
        if (mercePagata.equals(ordine.getMerci()))
            ordine.setStato(StatoOrdine.PAGATO);
        else
            //todo eccezione
            throw new IllegalArgumentException();
    }

}