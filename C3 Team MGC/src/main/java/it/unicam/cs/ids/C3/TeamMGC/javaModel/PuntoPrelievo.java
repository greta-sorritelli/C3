package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class PuntoPrelievo {

    private String indirizzo;
    private String nome;
    private IMagazziniere magazziniere = null;

    public PuntoPrelievo(String indirizzo, String nome) {
        this.indirizzo = indirizzo;
        this.nome = nome;
    }


    public void setMagazziniere(IMagazziniere magazziniere) {
        this.magazziniere = magazziniere;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getNome() {
        return nome;
    }

    public IMagazziniere getMagazziniere() {
        return magazziniere;
    }

    /**
     * @param IDCliente
     * @param codiceRitiro
     */
    public ArrayList<Ordine> getOrdini(int IDCliente, int codiceRitiro) {
        // TODO - implement PuntoPrelievo.getOrdini
        ArrayList<Ordine> lista = new ArrayList<>();
        if (magazziniere.verificaCodice(codiceRitiro)) {
            try {
                ResultSet rs = executeQuery("SELECT * from ordini\n" +
                        "WHERE IDCliente = " + IDCliente + " AND puntoPrelievo = " + this.nome + ";");
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
     * @param IDOrdine
     */
    public void getMerceMagazzino(int IDOrdine) {
        // TODO - implement PuntoPrelievo.getMerceMagazzino
        throw new UnsupportedOperationException();
    }

    /**
     * @param IDOrdine
     */
    public void getMerceTotale(int IDOrdine) {
        // TODO - implement PuntoPrelievo.getMerceTotale
        throw new UnsupportedOperationException();
    }

    //TODO implementare Dati Magazzino o vedere se è una cosa prettamente teorica
//	public Collection<DatiMagazzino> getDettagli() {
//		// TODO - implement PuntoPrelievo.getDettagli
//		throw new UnsupportedOperationException();
//	}

}