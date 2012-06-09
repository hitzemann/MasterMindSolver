package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;

/**
 * Implementierung von {@link IErgebnisBerechnung} mit Laufzeit in O(Anzahl Farben + Länge der Kombination).
 * 
 * <p>
 * Das Ergebnis wird folgendermaßen bestimmt:
 * </p>
 * 
 * <ul>
 * <li>schwarz := Anzahl der vollständig korrekt geratenen Spielsteine</li>
 * <li>weiss := Summe(Minimum(geheimProFarbe[X], geratenProFarbe[X]), X über alle Farben) - schwarz</li>
 * </ul>
 * 
 * <p>
 * Die verwendeten Werte berechnen sich so:
 * </p>
 * 
 * <ul>
 * <li>geheimProFarbe[X] := Anzahl der Spielsteine mit der Farbe X in der geheimen Kombination</li>
 * <li>geratenProFarbe[X] := Anzahl der Spielsteine mit der Farbe X in der geratenen Kombination</li>
 * </ul>
 * 
 * @author chschu
 */
public final class LinearerErgebnisBerechner implements IErgebnisBerechnung {

	@Override
	public ErgebnisKombination berechneErgebnis(final SpielKombination geheim, final SpielKombination geraten) {
		if (geheim == null) {
			throw new IllegalArgumentException("Geheime Kombination ist null!");
		}
		if (geraten == null) {
			throw new IllegalArgumentException("Geratene Kombination ist null!");
		}

		final int groesse = geheim.getSpielSteineCount();
		if (geraten.getSpielSteineCount() != groesse) {
			throw new IllegalArgumentException("Spielkombinationen haben unterschiedliche Größen!");
		}

		final int anzahlFarben = SpielStein.values().length;

		// "schwarz", "geheimProFarbe[X]" und "geratenProFarbe[X]" berechnen
		int schwarz = 0;
		final int[] geheimProFarbe = new int[anzahlFarben];
		final int[] geratenProFarbe = new int[anzahlFarben];
		for (int i = 0; i < groesse; i++) {
			final int geheimFarbe = geheim.getSpielStein(i).ordinal();
			final int geratenFarbe = geraten.getSpielStein(i).ordinal();
			geheimProFarbe[geheimFarbe]++;
			geratenProFarbe[geratenFarbe]++;
			if (geheimFarbe == geratenFarbe) {
				schwarz++;
			}
		}

		// "weiss" berechnen
		int weiss = -schwarz;
		for (int i = 0; i < anzahlFarben; i++) {
			weiss += Math.min(geheimProFarbe[i], geratenProFarbe[i]);
		}

		return new ErgebnisKombination(schwarz, weiss);
	}
}
