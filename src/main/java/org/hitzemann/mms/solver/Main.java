package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

/**
 * Die Hauptschleife, welche das Lösen koordiniert.
 * 
 * @author simon
 * 
 */
public class Main {
	/**
	 * Anzahl der Pins in dem Spiel (Länge der SpielKombination).
	 */
	static final int PINS = 4;
	
	/**
	 * Defaultkonstruktor.
	 */
	public Main() {
	}

	/**
	 * Hauptschleife.
	 * 
	 * @param argv Unused
	 */
	public static void main(final String[] argv) {
		final IUserInteraktion userInterface = new TextUserInteraktion();
		final IErgebnisBerechnung berechner = new LinearerErgebnisBerechner();
		final ISolver spielSolver = new KnuthSolver(berechner);
		while (true) {
			final SpielKombination ratekombi = spielSolver.getNeuerZug();
			final ErgebnisKombination ergebnis = userInterface
					.frageantwort(ratekombi);
			if (ergebnis.getSchwarz() == PINS) {
				userInterface.gewonnen();
				break;
			}
			spielSolver.setLetzterZug(ratekombi, ergebnis);
		}

	}

}
