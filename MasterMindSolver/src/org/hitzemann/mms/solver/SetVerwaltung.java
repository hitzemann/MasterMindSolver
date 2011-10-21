/**
 * 
 */
package org.hitzemann.mms.solver;

import java.util.HashSet;
import java.util.Set;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;

/**
 * @author simon
 * 
 */
public class SetVerwaltung {
	private Set<SpielKombination> ratemoeglichkeiten;
	private Set<SpielKombination> eleminierungs_moeglichkeiten;
	private SpielKombination beste_moeglichkeit;
	private int score_beste_moeglichkeit;

	public SetVerwaltung() {
		// TODO: Map aus Pair<SpielKombination, SpielKombination> und
		// ErgebnisKombination bauen
		int x = 1;
		if (x > 0) {
			throw new UnsupportedOperationException("Not yet implemented.");
		}
		eleminierungs_moeglichkeiten = new HashSet<SpielKombination>();
		for (SpielStein erster : SpielStein.values()) {
			for (SpielStein zweiter : SpielStein.values()) {
				for (SpielStein dritter : SpielStein.values()) {
					for (SpielStein vierter : SpielStein.values()) {
						SpielKombination kombi = new SpielKombination(erster,
								zweiter, dritter, vierter);
						eleminierungs_moeglichkeiten.add(kombi);
						ratemoeglichkeiten.add(kombi);
					}
				}
			}
		}
		beste_moeglichkeit = new SpielKombination(SpielStein.BLUE,
				SpielStein.BLUE, SpielStein.GREEN, SpielStein.GREEN);
		score_beste_moeglichkeit = 256;
	}

	public SpielKombination zeigeBesteMoeglichkeit() {
		return beste_moeglichkeit;
	}

	public void findeBesteMoeglichkeit() {
		// TODO: iteriere über alle eliminierungs_möglichkeiten
		// TODO: für jede eleminierungsmöglichkeit, iteriere über alle
		// ergebnismöglichkeiten
		// TODO: errechne anzahl der eleminierten ratemöglichkeiten für jede
		// dieser kombinationen, behalte die minimale
		// TODO: behalte die ratemöglichkeit mit der höchsten wertung
	}

	private int errechneScore(SpielKombination geraten,
			ErgebnisKombination ergebnis) {
		int score = 0;
		// TODO: irgendwie eine Map aus Pair<SpielKombination,
		// ErgebnisKombination>
		return score;
	}

}
