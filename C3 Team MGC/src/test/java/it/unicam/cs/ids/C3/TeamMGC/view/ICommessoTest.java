package it.unicam.cs.ids.C3.TeamMGC.view;

import org.junit.jupiter.api.BeforeAll;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

class ICommessoTest {

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
    }

//    @Test
//    void verificaEsistenzaCodice() {
//        Cliente test = new Cliente("Mario", "Rossi");
//        Ordine ordine = new Ordine(1, "Mario", "Rossi");
//        ICommesso iCommesso = new ICommesso();
//        assertNull(test.getCodiceRitiro());
//        assertEquals(iCommesso.verificaEsistenzaCodice(test, ordine), test.getCodiceRitiro());
//    }

}