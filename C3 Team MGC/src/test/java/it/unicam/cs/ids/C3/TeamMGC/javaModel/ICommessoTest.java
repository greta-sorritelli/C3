package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class ICommessoTest {

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
    }

    @Test
    void verificaValiditaCodice() {
        Cliente test = new Cliente("Mario", "Rossi");
        ICommesso iCommesso = new ICommesso();
        assertFalse(iCommesso.verificaValiditaCodice(test));
        assertTrue(iCommesso.verificaValiditaCodice(test));
    }

}