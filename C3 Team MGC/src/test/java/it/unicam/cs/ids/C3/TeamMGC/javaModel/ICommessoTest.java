package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class ICommessoTest {

    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("TRUNCATE `sys`.`clienti`;");
    }

    @Test
    void verificaValiditaCodice() {
        Cliente test = new Cliente("Mario", "Rossi");
        ICommesso iCommesso = new ICommesso();
        assertFalse(iCommesso.verificaValiditaCodice(test));
        assertTrue(iCommesso.verificaValiditaCodice(test));
    }

}