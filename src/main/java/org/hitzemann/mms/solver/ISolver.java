package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

/**
 * Interface für die Implementierung eines Solvers.
 * 
 * @author simon
 * 
 */
public interface ISolver {

    /**
     * Diese Methode sollte den nächsten Rateversuch zurückgeben.
     * 
     * @return Rateversuch als SpielKombination Objekt
     */
    SpielKombination getNeuerZug();

    /**
     * Diese Methode sollte den letzten Zug auswerten, nachdem eine ErgebnisKombination zurückgekommen ist.
     * 
     * @param zug
     *            Der zuletzt getätigte Zug
     * @param antwort
     *            Das Ergebnis für den zuletzt getätigten Zug
     */
    void setLetzterZug(SpielKombination zug, ErgebnisKombination antwort);

}