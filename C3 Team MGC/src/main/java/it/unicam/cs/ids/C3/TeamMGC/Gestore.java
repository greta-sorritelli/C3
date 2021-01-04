package it.unicam.cs.ids.C3.TeamMGC;

import java.util.ArrayList;

public interface Gestore<T> {

    T getItem(int ID);

    ArrayList<T> getItems();

    ArrayList<ArrayList<String>> getDettagliItems();
}
