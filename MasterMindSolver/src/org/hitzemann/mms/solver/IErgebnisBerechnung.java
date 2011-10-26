package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

/**
 * Interface für die ErgebnisBerechnung.
 * 
 * @author simon
 * 
 */
public interface IErgebnisBerechnung {
/**
 * Diese Methose sollte so implementiert werden, dass zu zwei
 * eingegebenen SpielKombinationen eine ErgebnisKombination
 * zurückgegeben wird.
 * @param geheim ReferenzSpielKombination
 * @param geraten Rateversuch
 * @return ErgebnisKombination für den Rateversuch
 */
	ErgebnisKombination berechneErgebnis(SpielKombination geheim,
			SpielKombination geraten);

}