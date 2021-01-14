package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;

import java.sql.SQLException;

public class ICliente implements JavaFXController {
    private int IDCliente;

    public ICliente(int ID) throws SQLException {
        this.IDCliente = ID;
    }
}
