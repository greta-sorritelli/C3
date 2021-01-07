<<<<<<< Updated upstream
package it.unicam.cs.ids.C3.TeamMGC.javaFX;public class JavaFXTrasportareMerce {
=======
package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;

public class JavaFXTrasportareMerce {
    private final GestoreOrdini gestoreOrdini;

    public JavaFXTrasportareMerce(GestoreOrdini gestoreOrdini) {
        this.gestoreOrdini = gestoreOrdini;
    }

    public void getDettagliMerce(StatoOrdine statoOrdine){
        // todo stato affidato al corriere
    }

    public void setStatoMerce(int IDMerce, StatoOrdine statoOrdine){
        //todo stato in transito
    }


>>>>>>> Stashed changes
}
