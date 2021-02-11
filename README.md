<h1>C3: Centro Commerciale in Centro</h1>
<h2> Authors: Matteo Rondini, Greta Sorritelli, Clarissa Albanese</h2>


Progetto per la realizzazione di un' applicazione rivolta ai centri abitati medi della provincia italiana
dove le attività commerciali del centro soffrono la concorrenza di grossi centri commerciali 
situati nelle periferie.


L’idea di fondo è considerare il trasporto della merce, una volta acquistata, 
una delle scomodità principali degli acquisti in centro,
oltre alla più difficile collocazione dei punti vendita in relazione a specifiche categorie merceologiche.

Il progetto si pone dunque come obiettivo quello di fornire un supporto tramite punti di prelievo
per rendere l’esperienza degli acquisti in centro più facile e interessante.

<h3>Iterazione 1</h3>

- _Identificazione degli attori principali_: Commerciante, Commesso, Corriere, Magazziniere, Addetto magazzino
  del negozio e Cliente.
- _Identificazione casi d' uso_:
  <p>&nbsp;</p>
  <ul> <li>Registrazione sulla piattaforma.</li>
  <li> Ricezione pagamento ordine.</li> 
  <li> Seleziona punto di prelievo.</li> 
  <li> Seleziona corriere.</li>
  <li> Comunicare codice di ritiro ordine.</li>
  <li> Comunicare con il corriere.</li>
  <li> Assegnare al corriere la merce.</li>
  <li> Trasportare la merce.</li>
  <li> Consegnare la merce al cliente.</li>
  <li> Consegna la merce al punto di prelievo.</li>
  <li> Modificare lo stato di disponibilità.</li> </ul>
  <p>&nbsp;</p>

- _Assegnamento priorità dei casi d' uso_;
- _Specifica dei casi d' uso principali_ di corriere, commesso, addetto e magazziniere;
- _Realizzazione classi di analisi_;
- _Realizzazione diagrammi di sequenza_:
  <p>&nbsp;</p>
  <ul> <li>Comunicare codice di ritiro ordine.</li>
  <li> Consegnare la merce al cliente.</li> 
  <li> Ricezione pagamento ordine.</li> 
  <li> Seleziona corriere.</li> 
  <li> Seleziona punto di prelievo.</li> </ul>
  <p>&nbsp;</p>

- _Realizzazione modello di progetto_;
- _Inizio implementazione componenti base_.
************************************************************************************************************

<h3>Iterazione 2</h3>

- _Scrittura scenari nuovi casi d' uso_:
  <p>&nbsp;</p>
  <ul> <li>Gestire vendite promozionali.</li>
  <li> Filtrare i punti vendita.</li> 
  <li> Filtrare le promozioni.</li> 
  <li> Gestione inventario.</li> </ul>
  <p>&nbsp;</p>

- _Revisione e correzione casi d' uso precedenti_;
- _Modifica modello di analisi_: aggiunta di nuovi gestori e modifica struttura di personale;
- _Realizzazione nuovi diagrammi di sequenza_:
  <p>&nbsp;</p>
  <ul> <li>Assegnare al corriere la merce.</li>
  <li> Comunicare con il corriere.</li> 
  <li> Consegna la merce a destinazione.</li> 
  <li> Gestione inventario.</li> 
  <li> Registrazione sulla piattaforma.</li> 
  <li> Trasportare la merce.</li> 
  <li> Modificare lo stato di disponibilità.</li> </ul>
  <p>&nbsp;</p>

- _Revisione modello di progetto_: aggiunta packages e gestori;
- _Realizzazione modello persistenza_;
- _Proseguimento implementazione_.
************************************************************************************************************

<h3>Iterazione 3</h3>

- _Aggiunta dell' attore astratto utente e correzione associazione casi d' uso/attori_;
- _Scrittura scenari casi d' uso finali_:
  <p>&nbsp;</p>
  <ul> <li>Effettuare pagamenti.</li>
  <li> Occuparsi delle statistiche.</li>
  <li> SignIn.</li> 
  <li> Login.</li> 
  <li> Controllare alert.</li> 
  <li> Gestione negozi.</li> 
  <li> Gestione punti prelievo.</li> 
  <li> Lanciare vendita combinata.</li> </ul>
  <p>&nbsp;</p>

- _Revisione e correzione casi d' uso precedenti_;
- _Modifica modello di analisi_: aggiunta nuovi gestori, metodi, attributi e rifinizione relazioni e classi precedenti;
- _Realizzazione nuovi diagrammi di sequenza_:
  <p>&nbsp;</p>
  <ul> <li>Gestione negozi.</li>
  <li> Gestione punti prelievo.</li> 
  <li> Controllare alert.</li> 
  <li> Filtrare i punti vendita.</li> 
  <li> Filtrare le promozioni.</li> 
  <li> Gestire vendite promozionali.</li> 
  <li> Login.</li> 
  <li> SignIn.</li> 
  <li> Occuparsi delle statistiche.</li> </ul>
  <p>&nbsp;</p>

- _Revisione modello di progetto_: aggiunta interfacce, gestori e rifinizione finale delle classi;
- _Realizzazione modello persistenza_;
- _Terminazione implementazione_.


Realizzato per il corso di 
<a href="http://didattica.cs.unicam.it/doku.php?id=didattica:triennale:ids:ay_2021:main"> Ingegneria del Software</a>,
del <a href="https://www.cs.unicam.it/">Corso di Laurea in Informatica</a> di <a href="http://www.unicam.it/">Unicam</a>.
