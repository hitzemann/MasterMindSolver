package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

/**
 * Interface für die Kommunikation mit dem User.
 * @author simon
 *
 */
public interface IUserInteraktion {

	/**
	 * Diese Methode soll dem User anzeigen, welcher Rateversuch getätigt wurde.
	 * Ausserdem muss sie von dem User das Ergebnis abfragen.
	 * @param geraten SpielKombination die geraten werden soll
	 * @return ErgebnisKombination welche den Rateversuch auswertet
	 */
	ErgebnisKombination frageantwort(SpielKombination geraten);
	/**
	 * Diese Methode sollte aufgerufen werden, wenn das Rätsel gelöst ist.
	 */
	void gewonnen();
}
