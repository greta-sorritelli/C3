package it.unicam.cs.ids.C3.TeamMGC.corriere;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class GestoreCorrieri {
    ArrayList<Corriere> corrieri = new ArrayList<>();

    /**
     * Controlla se il {@link Corriere} che si vuole creare e' gia' presente nella lista dei corrieri. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return Il corriere
     * @throws SQLException
     */
    private Corriere addCorriere(ResultSet rs) throws SQLException {
        for (Corriere corriere : corrieri)
            if (corriere.getID() == rs.getInt("ID"))
                return corriere;
        Corriere toReturn = new Corriere(rs.getInt("ID"), rs.getString("nome"),
                rs.getString("cognome"), rs.getBoolean("stato"),
                rs.getInt("capienza"));
        addCorriereToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link Corriere} alla lista di corrieri.
     *
     * @param corriere Corriere da aggiungere
     * @return {@code true} se il Corriere viene inserito correttamente, {@code false} altrimenti
     */
    private boolean addCorriereToList(Corriere corriere) {
        if (!corrieri.contains(corriere))
            return corrieri.add(corriere);
        else
            return false;
    }

    /**
     * Ritorna il {@link Corriere} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Corriere
     * @return Il Corriere desiderato
     */
    public Corriere getCorriere(int ID) {
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.corrieri where ID='" + ID + "' ;");
            if (rs.next())
                return addCorriere(rs);
            else
                //todo eccezione
                throw new IllegalArgumentException();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * @return l'elenco dei Corrieri Disponibili
     */
    //TODO controllare se va bene il test
    public ArrayList<Corriere> getCorrieriDisponibili() {
        try {
            ArrayList<Corriere> corrieriDisponibili = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * FROM sys.corrieri WHERE (`stato` = 'true' );");
            if (rs.next())
                do {
                    Corriere tmp = new Corriere(rs.getInt("ID"), rs.getString("nome"),
                            rs.getString("cognome"), true, rs.getInt("capienza"));
                    corrieriDisponibili.add(tmp);
                } while (rs.next());

            else {
                ResultSet rs1 = executeQuery("SELECT * FROM sys.corrieri WHERE (`stato` = 'true' );");
                while (!rs1.next())
                    rs1 = executeQuery("SELECT * FROM sys.corrieri WHERE (`stato` = 'true' );");

                Corriere tmp = new Corriere(rs.getInt("ID"), rs.getString("nome"),
                        rs.getString("cognome"), true, rs.getInt("capienza"));
                corrieriDisponibili.add(tmp);
            }
            return corrieriDisponibili;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * @return ArrayList<ArrayList<String>> dei dettagli dei corrieri disponibili.
     */
    public ArrayList<ArrayList<String>> getDettagliCorrieriDisponibili() {
        try {
            ArrayList<Corriere> corrieriDisponibili = new ArrayList<>();
            ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * FROM sys.corrieri WHERE (`stato` = 'true' );");
            while (rs.next()) {
                Corriere tmp = new Corriere(rs.getInt("ID"), rs.getString("nome"),
                        rs.getString("cognome"), true, rs.getInt("capienza"));
                corrieriDisponibili.add(tmp);
            }
            for (Corriere corriere : corrieriDisponibili) {
                dettagli.add(corriere.getDettagli());
            }
            return dettagli;
        } catch (SQLException exception) {
            //todo eccezione
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Seleziona il {@link Corriere} desiderato.
     *
     * @param ID Codice Identificativo del Corriere
     * @return Le informazioni del Corriere
     */
    public ArrayList<String> selezionaCorriere(int ID) {

        return getCorriere(ID).getDettagli();
    }

    public void setCapienza(int IDCorriere, int capienza) {

        getCorriere(IDCorriere).setCapienza(capienza);
    }

    public void setDisponibilita(int IDCorriere, boolean disponibilita) {
        getCorriere(IDCorriere).setDisponibilita(disponibilita);
    }

    public boolean getDisponibilita(int IDCorriere){
        return getCorriere(IDCorriere).getDisponibilita();
    }

    //todo controllare
    public ArrayList<String> inserisciDati(String nome, String cognome, int capienza){
        Corriere corriere = new Corriere(nome,cognome,true,capienza);
        return corriere.getDettagli();
    }
}