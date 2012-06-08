package org.hitzemann.mms.solver;

import static org.junit.Assume.assumeTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.junit.AfterClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Integrations-Testklasse für einen {@link ISolver}. Diese Testklasse benutzt
 * zur Abwechslung Theories.
 * 
 * @author simon
 * 
 */
@RunWith(Theories.class)
public final class SolverTheoryIT {

	/**
	 * Anzahl der Pins (Länge der Kombinationen).
	 */
	private static final int PINS = 4;

	private static int DURCHGANG = 0;

        private static SpielKombination currentfirst;

	/**
	 * Die gemeinsam benutzte {@link IErgebnisBerechnung}-Instanz.
	 */
	private static final IErgebnisBerechnung BERECHNER = new LinearerErgebnisBerechner();

	/**
	 * Das Histogramm mit den Häufigkeiten der Anzahl der benötigten
	 * Rateversuche.
	 */
	private static final Map<SpielKombination, SortedMap<Integer, Integer>> HISTOGRAMM = new HashMap<SpielKombination, SortedMap<Integer, Integer>>();

	/**
	 * Die aktuell getestete geheime Kombination.
	 */
	private final SpielKombination geheim;

	/**
	 * Erzeugt einen Test mit der angegebenen geheimen Kombination.
	 * 
	 * @param theGeheim
	 *            Die geheime Kombination.
	 */
	public SolverTheoryIT(final SpielKombination theGeheim) {
		geheim = theGeheim;
	}

	/**
	 * Factory-Methode zum Erzeugen einer neuen {@link ISolver}-Instanz.
	 * 
	 * @return Eine neue Instanz einer {@link ISolver}-Implementierung.
	 */
	private ISolver createSolver(final SpielKombination firstguess) {
		return new KnuthSolver(BERECHNER, firstguess);
	}

	@DataPoints
	public static SpielKombination[] kombis = erzeugeAlleKombinationen(PINS)
			.toArray(new SpielKombination[0]);

	@Theory
	public void versucheTest(SpielKombination first, SpielKombination geheim) {

                assumeTrue(true);
                if (!first.equals(currentfirst)) {
                    currentfirst = first;
                    System.out.println(first.toString());
                }

		final ISolver solver = createSolver(first);

		if (!first.equals(currentfirst)) {
			if (currentfirst != null) {
				printStats(currentfirst);
			}
			currentfirst = first;
			//System.out.println("Current first guess: " + currentfirst.toString());
		}

		// Spiel starten
		int versuche = 0;
		while (true) {
			final SpielKombination geraten = solver.getNeuerZug();
			final ErgebnisKombination ergebnis = BERECHNER.berechneErgebnis(
					geheim, geraten);
			versuche++;
			if (ergebnis.getSchwarz() == PINS) {
				// gewonnen
				break;
			}
			solver.setLetzterZug(geraten, ergebnis);
		}

		if (HISTOGRAMM.get(first) == null) {
			final SortedMap<Integer, Integer> histomap = new TreeMap<Integer, Integer>();
			HISTOGRAMM.put(first, histomap);
		}
		Integer freq = HISTOGRAMM.get(first).get(versuche);
		if (freq == null) {
			freq = 0;
		}
		HISTOGRAMM.get(first).put(versuche, freq + 1);
		// System.out.println("Möp!");
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
	 * @param ergebnisSet
	 *            Das {@link Set} zum Einsammeln der erzeugten
	 *            {@link SpielKombination}en.
	 */
	private static void erzeugeAlleKombinationenRekursiv(
			final SpielStein[] spielSteine, final int iterierOffset,
			final Set<SpielKombination> ergebnisSet) {
		if (iterierOffset < spielSteine.length) {
			for (SpielStein spielStein : SpielStein.values()) {
				spielSteine[iterierOffset] = spielStein;
				erzeugeAlleKombinationenRekursiv(spielSteine,
						iterierOffset + 1, ergebnisSet);
			}
		} else {
			// Array klonen, damit nicht alle erzeugten Kombinationen das
			// gleiche benutzen
			ergebnisSet.add(new SpielKombination(spielSteine.clone()));
		}
	}

	/**
	 * Erzeugt eine Menge mit allen möglichen Kombinationen einer bestimten
	 * Größe.
	 * 
	 * @param groesse
	 *            Die Kombinationsgröße
	 * @return Ein {@link Set} mit allen möglichen Kombinationen.
	 */
	private static Set<SpielKombination> erzeugeAlleKombinationen(
			final int groesse) {
		final Set<SpielKombination> ergebnisSet = new HashSet<SpielKombination>();
		erzeugeAlleKombinationenRekursiv(new SpielStein[groesse], 0,
				ergebnisSet);
		return ergebnisSet;
	}

	/**
	 * Gibt die Statistik des Lösungsvorgangs aus.
	 */
	@AfterClass
	public static void printStats() {
		for (SpielKombination first : kombis) {

			int sum = 0;
			int count = 0;

			// Histogramm ausgeben
			System.out.println("#guesses;frequency");
			System.out.println("First guess: " + first.toString());
			for (Map.Entry<Integer, Integer> e : HISTOGRAMM.get(first)
					.entrySet()) {
				System.out.println(e.getKey() + ";" + e.getValue());
				sum += e.getKey() * e.getValue();
				count += e.getValue();
			}
			System.out.println("---");
			System.out.println("average = " + 1.0 * sum / count);
		}
	}
	
	public static void printStats(final SpielKombination first) {
		int sum = 0;
		int count = 0;

		// Histogramm ausgeben
		System.out.println("#guesses;frequency");
		System.out.println("First guess: " + first.toString());
		for (Map.Entry<Integer, Integer> e : HISTOGRAMM.get(first)
				.entrySet()) {
			System.out.println(e.getKey() + ";" + e.getValue());
			sum += e.getKey() * e.getValue();
			count += e.getValue();
		}
		System.out.println("---");
		System.out.println("average = " + 1.0 * sum / count);
	}
}
