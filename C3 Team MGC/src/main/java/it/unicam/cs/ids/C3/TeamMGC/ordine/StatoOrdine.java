package it.unicam.cs.ids.C3.TeamMGC.ordine;

/**
 * Enumerazione che definisce lo {@code Stato} di un {@link Ordine} o di una {@link MerceOrdine}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public enum StatoOrdine {
    DA_PAGARE,
    PAGATO,
    CORRIERE_SCELTO,
    AFFIDATO_AL_CORRIERE,
    IN_TRANSITO,
    IN_DEPOSITO,
    RITIRATO
}