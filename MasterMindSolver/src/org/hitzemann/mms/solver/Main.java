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
	 * Hauptschleife.
	 */
	public static void main() {
		IUserInteraktion userInterface = new TextUserInteraktion();
		IErgebnisBerechnung berechner = new DefaultErgebnisBerechner();
		ISolver spielSolver = new KnuthSolver(berechner);
		while (true) {
			SpielKombination ratekombi = spielSolver.getNeuerZug();
			ErgebnisKombination ergebnis = userInterface
					.frageantwort(ratekombi);
			if (ergebnis.getSchwarz() == PINS) {
				userInterface.gewonnen();
				break;
			}
			spielSolver.setLetzterZug(ratekombi, ergebnis);
		}

	}

}
