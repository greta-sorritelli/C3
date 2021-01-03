package it.unicam.cs.ids.C3.TeamMGC.ordine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Ordine {

    private int ID;
    private int IDCliente;
    private String nomeCliente;
    private String cognomeCliente;
    private double totalePrezzo;
    private StatoOrdine stato;
    private int IDPuntoPrelievo = -1;
    private String residenza = null;
    private ArrayList<MerceOrdine> merci = new ArrayList<>();

    public Ordine(int ID, int IDCliente, String nomeCliente, String cognomeCliente, double totalePrezzo, StatoOrdine stato, int IDPuntoPrelievo) {
        this.ID = ID;
        this.IDCliente = IDCliente;
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        this.totalePrezzo = totalePrezzo;
        this.stato = stato;
        this.IDPuntoPrelievo = IDPuntoPrelievo;
    }

    //Todo forse levare
//    public Ordine(int IDCliente, String nomeCliente, String cognomeCliente, double totalePrezzo, StatoOrdine stato, int IDPuntoPrelievo) {
//        try {
//            if (IDPuntoPrelievo != -1) {
//                updateData("INSERT INTO `sys`.`ordini` (`IDCliente`, `nomeCliente`,`cognomeCliente`,`totalePrezzo`,`stato`,`IDPuntoPrelievo`,`residenza`) " +
//                        "VALUES ('" + IDCliente + "', '" + nomeCliente + "', '" + cognomeCliente + "', '" + totalePrezzo + "', '" + stato + "'," +
//                        "'" + IDPuntoPrelievo + "', \"null\");");
//                this.IDPuntoPrelievo = IDPuntoPrelievo;
//            } else {
//                updateData("INSERT INTO `sys`.`ordini` (`IDCliente`, `nomeCliente`,`cognomeCliente`,`totalePrezzo`,`stato`,`IDPuntoPrelievo`,`residenza`) " +
//                        "VALUES ('" + IDCliente + "', '" + nomeCliente + "', '" + cognomeCliente + "', '" + totalePrezzo + "', '" + stato + "', -1, \"null\");");
//                this.IDPuntoPrelievo = -1;
//            }
//            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from ordini;");
//            rs.next();
//            ID = rs.getInt("ID");
//            this.IDCliente = IDCliente;
//            this.nomeCliente = nomeCliente;
//            this.cognomeCliente = cognomeCliente;
//            this.totalePrezzo = totalePrezzo;
//            this.residenza = null;
//        } catch (SQLException exception) {
//            //todo
//            exception.printStackTrace();
//        }
//    }

    public Ordine(int IDCliente, String nomeCliente, String cognomeCliente) {
        try {
            updateData("INSERT INTO `sys`.`ordini` (`IDCliente`, `nomeCliente`,`cognomeCliente`) " +
                    "VALUES ('" + IDCliente + "', '" + nomeCliente + "', '" + cognomeCliente + "');");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from ordini;");
            rs.next();
            ID = rs.getInt("ID");
            this.IDCliente = IDCliente;
            this.nomeCliente = nomeCliente;
            this.cognomeCliente = cognomeCliente;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    /**
     * Aggiunge l'{@code indirizzo} della residenza all'{@link Ordine}.
     *
     * @param indirizzo Indirizzo residenza del cliente
     */
    public void addResidenza(String indirizzo) {
        try {
            updateData("UPDATE `sys`.`ordini` SET `puntoPrelievo` =  \"null\" WHERE (`ID` = '" + this.ID + "');");
            updateData("UPDATE `sys`.`ordini` SET `residenza` = '" + indirizzo + "' WHERE (`ID` = '" + this.ID + "');");
            IDPuntoPrelievo = -1;
            residenza = indirizzo;
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    /**
     * Aggiunge la {@link MerceOrdine} all'{@link Ordine} del cliente.
     *
     * @param merce    Merce da aggiungere
     * @param quantita Quantita della merce da aggiungere
     */
    //todo
    public void aggiungiMerce(MerceOrdine merce, int quantita) {
        try {
            merce.setQuantita(quantita);
            merci.add(merce);
            this.totalePrezzo += (merce.getPrezzo() * quantita);
            updateData("UPDATE `sys`.`ordini` SET `totalePrezzo` = '" + this.totalePrezzo + "' WHERE (`ID` = '" + this.ID + "');");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Aggiunge la {@link MerceOrdine} all'{@link Ordine}, prendendo i dati dal Database.
     *
     * @param merce Merce da aggiungere
     */
    public void addMerce(MerceOrdine merce) {
        merci.add(merce);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordine ordine = (Ordine) o;
        return ID == ordine.ID;
    }

    public String getCognomeCliente() {
        return cognomeCliente;
    }

    /**
     * Ritorna un arraylist con i dettagli dell'{@link Ordine} in stringa.
     *
     * @return ArrayList dei dettagli
     */
    public ArrayList<String> getDettagli() {
        ArrayList<String> ordini = new ArrayList<>();
        ordini.add(String.valueOf(getID()));
        ordini.add(String.valueOf(getIDCliente()));
        ordini.add(getNomeCliente());
        ordini.add(getCognomeCliente());
        ordini.add(String.valueOf(getTotalePrezzo()));
        ordini.add(getStato().toString());

        if (IDPuntoPrelievo != -1)
            ordini.add(String.valueOf(getPuntoPrelievo()));
        else
            ordini.add(String.valueOf(getResidenza()));

        ordini.add(String.valueOf(getMerci()));
        return ordini;
    }

    public int getID() {
        return ID;
    }

    public int getIDCliente() {
        return IDCliente;
    }

    public ArrayList<MerceOrdine> getMerci() {
        return merci;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public int getPuntoPrelievo() {
        return IDPuntoPrelievo;
    }

    public void setPuntoPrelievo(int IDPuntoPrelievo) {
        try {
            updateData("UPDATE sys.ordini` SET IDPuntoPrelievo = '" + IDPuntoPrelievo + "' WHERE (`ID` = '" + this.ID + "');");
            this.IDPuntoPrelievo = IDPuntoPrelievo;
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    public String getResidenza() {
        return residenza;
    }

    public StatoOrdine getStato() {
        return stato;
    }

    public void setStato(StatoOrdine statoOrdine) {
        try {
            updateData("UPDATE `sys`.`ordini` SET `stato` = '" + statoOrdine + "' WHERE (`ID` = '" + this.ID + "');");
            this.stato = statoOrdine;
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    public double getTotalePrezzo() {
        return totalePrezzo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }


}

