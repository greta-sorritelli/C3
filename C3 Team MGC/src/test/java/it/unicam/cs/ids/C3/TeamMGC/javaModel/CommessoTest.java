package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CommessoTest {

    @Test
    void generaCodiceRitiro() {
        Commesso commesso = new Commesso();
        String prova = commesso.generaCodiceRitiro();
        assertNotEquals(prova, commesso.generaCodiceRitiro());
    }
}