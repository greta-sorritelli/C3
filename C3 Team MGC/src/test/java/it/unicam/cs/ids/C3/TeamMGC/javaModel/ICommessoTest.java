package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ICommessoTest {

    @Test
    void generaCodiceRitiro() {
        ICommesso intCommesso = new ICommesso();
        String prova = intCommesso.generaCodiceRitiro();
        assertNotEquals(prova, intCommesso.generaCodiceRitiro());
    }
}