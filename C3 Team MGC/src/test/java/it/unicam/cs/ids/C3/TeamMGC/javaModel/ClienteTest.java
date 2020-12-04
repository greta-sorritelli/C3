package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNull;

class ClienteTest {

    @Test
    void setCodiceRitiro() {
        Cliente cliente = new Cliente("Test", "Test");
        assertNull(cliente.getDataCreazioneCodice());
        cliente.setCodiceRitiro("Test Prova");
        Date after = cliente.getDataCreazioneCodice();
        //todo finire il test
    }
}