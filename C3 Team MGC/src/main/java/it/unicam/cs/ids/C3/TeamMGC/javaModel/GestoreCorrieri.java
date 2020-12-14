package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class GestoreCorrieri {
	/**
	 * @return l'elenco dei Corrieri Disponibili
	 */
	public ArrayList<Corriere> getCorrieriDisponibili() {
		try {
			ArrayList<Corriere> corrieri = new ArrayList<>();
			ResultSet rs = executeQuery("SELECT * FROM sys.corrieri WHERE (`stato` = 'DISPONIBILE' );");
			while (rs.next()) {
				Corriere tmp = new Corriere(rs.getInt("ID"), true, rs.getInt("capienza"));
				corrieri.add(tmp);
			}
			return corrieri;
		} catch (SQLException exception) {
			//todo
			exception.printStackTrace();
		}
		return null;
	}

	public Corriere selezionaCorriere() {
		// TODO - implement GestoreCorrieri.selezionaCorriere
		throw new UnsupportedOperationException();
	}

}