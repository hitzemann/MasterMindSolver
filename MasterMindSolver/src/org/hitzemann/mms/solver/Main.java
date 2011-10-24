package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IUserInteraktion userInterface = new TextUserInteraktion();
		IErgebnisBerechnung berechner = new DefaultErgebnisBerechner();
		ISolver spielSolver = new KnuthSolver(berechner);
		while (true) {
			SpielKombination ratekombi = spielSolver.getNeuerZug();
			ErgebnisKombination ergebnis = userInterface.frageantwort(ratekombi);
			if (ergebnis.getSchwarz() == 4) {
				userInterface.gewonnen();
				break;
			}
			spielSolver.setLetzterZug(ratekombi, ergebnis);
		}

	}

}
