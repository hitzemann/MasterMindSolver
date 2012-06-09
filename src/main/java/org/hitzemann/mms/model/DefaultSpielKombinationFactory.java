package org.hitzemann.mms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard-Implementierung der {@link ISpielKombinationFactory}.
 * 
 * @author chschu
 */
public final class DefaultSpielKombinationFactory implements
		ISpielKombinationFactory {

	@Override
	public List<SpielKombination> erzeugeAlle(final int pins) {
		if (0 > pins) {
			throw new IllegalArgumentException("Übergebene Länge ist negativ");
		}
		final List<SpielKombination> alleErzeugten = new ArrayList<SpielKombination>();
		erzeugeAlleKombinationenRekursiv(new SpielStein[pins], 0, alleErzeugten);
		return alleErzeugten;
	}
	
	/**
	 * Rekursiver Algorithmus zur Erzeugung aller {@link SpielKombination}en.
	 * 
	 * @param spielSteine
	 *            Das rekursiv zu füllende Array mit {@link SpielStein}en. Die
	 *            Länge dieses Arrays bestimmt die Länge der erzeugten
	 *            Kombinationen.
	 * @param iterierOffset
	 *            Das in diesem Aufruf zu iterierende Offset in das
	 *            spielSteine-Array. Alle höheren Indizes werden durch rekursive
	 *            Aufrufe dieser Methode befüllt.
	 * @param ergebnisList
	 *            Die {@link List} zum Einsammeln der erzeugten
	 *            {@link SpielKombination}en.
	 */
	private void erzeugeAlleKombinationenRekursiv(
			final SpielStein[] spielSteine, final int iterierOffset,
			final List<SpielKombination> ergebnisList) {
		if (iterierOffset < spielSteine.length) {
			for (SpielStein spielStein : SpielStein.values()) {
				spielSteine[iterierOffset] = spielStein;
				erzeugeAlleKombinationenRekursiv(spielSteine,
						iterierOffset + 1, ergebnisList);
			}
		} else {
			// Array klonen, damit nicht alle erzeugten Kombinationen das
			// gleiche benutzen
			ergebnisList.add(new SpielKombination(spielSteine.clone()));
		}
	}
}
