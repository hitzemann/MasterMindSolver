package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IUserInteraktion userInterface = new TextUserInteraktion();
		Solver spielSolver = new Solver();
		while (spielSolver.getNumLoesungen() > 1) {
			SpielKombination ratekombi = spielSolver.getNeuerZug();
			ErgebnisKombination ergebnis = userInterface.frageantwort(ratekombi);
			if (ergebnis.getSchwarz() == 4) {
				userInterface.gewonnen();
			}
			spielSolver.setLetzterZug(ratekombi, ergebnis);
		}

	}

}
