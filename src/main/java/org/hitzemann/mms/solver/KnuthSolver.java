/**
 * 
 */
package org.hitzemann.mms.solver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;

/**
 * @author simon
 * 
 */
public final class KnuthSolver implements ISolver {

	/**
	 * Anzahl der Pins in diesem Spiel.
	 */
	private static final int PINS = 4;

	/**
	 * Aktueller firstguess für den Cache.
	 */
	private static SpielKombination currentfirst = null;

	/**
	 * Maximumscore einer Lösung, sollte größer sein als Farben^Pins.
	 */
	private static final long MAXSCORE = Math.round(Math.pow(
			SpielStein.values().length, PINS)) + 1;

	/**
	 * Statisches Set mit allen Möglichkeiten, sollte für mehr Geschwindigkeit
	 * sorgen.
	 */
	private static SortedSet<SpielKombination> alleMoeglichkeiten;

	/**
	 * Cache für die gewählte Ratekombination in Abhängigkeit von der Menge der
	 * verfügbaren Kandidaten für die Geheimkombination.
	 */
	private static final Map<Set<SpielKombination>, SpielKombination> CACHE = new HashMap<Set<SpielKombination>, SpielKombination>();

	/**
	 * Objekt zur Ergebnisberechnung.
	 */
	private IErgebnisBerechnung ergebnisBerechner;

	/**
	 * Set mit noch allen möglichen Lösungen für das aktuelle Spiel.
	 */
	private SortedSet<SpielKombination> geheimMoeglichkeiten;

	/**
	 * Set mit allen gültigen ErgebnisMöglichkeiten (Modulo dem Ergebnis, dass
	 * alle Pins richtig sind).
	 */
	private SortedSet<ErgebnisKombination> ergebnisMoeglichkeiten;

	/**
	 * Map aus SpielKombination und Score für den jeweiligen Zug. Die Score ist
	 * die Anzahl an Lösungen, die mindestens eliminiert werden.
	 */
	private Map<SpielKombination, Long> scoreMap;

	/**
	 * Set mit allen noch gültigen SpielKombinationen, die noch geraten werden
	 * können. Verhindert, dass Kombinationen mehrfach benutzt werden.
	 */
	// private SortedSet<SpielKombination> rateSet;

	static {
		initialisiereAlleMoeglichkeiten();

		// Cache mit festem ersten Zug vorbelegen
		//CACHE.put(alleMoeglichkeiten, new SpielKombination(1, 1, 2, 2));
                // Diese Möglichkeit wurde durch SolverTheoryIT als beste Vorbelegung
                // ermittelt. Sie schafft alle Kombinationen in unter 6 Zügen zu lösen
                // und brauch im Schnitt 4.742 anstatt 4.760 Versuche.
		CACHE.put(alleMoeglichkeiten, new SpielKombination(1, 4, 1, 4));
	}

	/**
	 * Standardkonstruktor für den Solver.
	 * 
	 * @param berechner
	 *            Objekt, welches IErgebnisBerechnung implementiert
	 */
	public KnuthSolver(final IErgebnisBerechnung berechner) {
		this.ergebnisBerechner = berechner;
		scoreMap = new TreeMap<SpielKombination, Long>();
		initialisiereErgebnisMoeglichkeiten();
		geheimMoeglichkeiten = new TreeSet<SpielKombination>(alleMoeglichkeiten);
		// rateSet = new TreeSet<SpielKombination>(alleMoeglichkeiten);
	}

	/**
	 * Nichtstandardkonstruktor für den Solver.
	 * 
	 * @param berechner
	 *            Objekt, welches IErgebnisBerechnung implementiert
	 * 
	 * @param firstguess
	 *            Objekt, welches die erste zu ratende Kombination ist
	 */
	public KnuthSolver(final IErgebnisBerechnung berechner,
			final SpielKombination firstguess) {
		this(berechner);
		if (!firstguess.equals(currentfirst)) {
			currentfirst = firstguess;
			CACHE.clear();
			CACHE.put(alleMoeglichkeiten, firstguess);
		}
	}

	/**
	 * Alle Farbmöglichkeiten der Spielsteine vorberechnen.
	 */
	private static void initialisiereAlleMoeglichkeiten() {
		alleMoeglichkeiten = new TreeSet<SpielKombination>();
		for (SpielStein i1 : SpielStein.values()) {
			for (SpielStein i2 : SpielStein.values()) {
				for (SpielStein i3 : SpielStein.values()) {
					for (SpielStein i4 : SpielStein.values()) {
						final SpielKombination kombi = new SpielKombination(i1,
								i2, i3, i4);
						alleMoeglichkeiten.add(kombi);
					}
				}
			}
		}
	}

	/**
	 * Für's Iterieren ein Set mit allen gültigen Ergebnissen für 4 Steine
	 * anlegen.
	 */
	private void initialisiereErgebnisMoeglichkeiten() {
		ergebnisMoeglichkeiten = new TreeSet<ErgebnisKombination>();
		for (int schwarz = 0; schwarz <= PINS; schwarz++) {
			for (int weiss = 0; weiss <= PINS; weiss++) {
				if ((schwarz + weiss) <= PINS
						&& !(schwarz == PINS - 1 && weiss == 1)) {
					ergebnisMoeglichkeiten.add(new ErgebnisKombination(schwarz,
							weiss));
				}
			}
		}
	}

	/**
	 * Entferne alle möglichen Geheimkombinationen, die nicht durch die geratene
	 * Kombination und das Ergebnis dargestellt werden können.
	 * 
	 * @param ratekombi
	 *            Geratene SpielKombination
	 * @param ergebnis
	 *            Ergebnis für die geratene Kombination
	 * @param moeglichkeitenSet
	 *            Set aus SpielKombinationen über die iteriert wird
	 */
	private void eleminiereMoeglichkeiten(final SpielKombination ratekombi,
			final ErgebnisKombination ergebnis,
			final Set<SpielKombination> moeglichkeitenSet) {
		if (ratekombi.getSpielSteineCount() != PINS) {
			throw new IllegalArgumentException(
					"Im Moment können nur 4 Pins gelöst werden");
		}
		for (final Iterator<SpielKombination> setIterator = moeglichkeitenSet
				.iterator(); setIterator.hasNext();) {
			final SpielKombination geheim = setIterator.next();
			if (!ergebnis.equals(ergebnisBerechner.berechneErgebnis(geheim,
					ratekombi))) {
				setIterator.remove();
			}
		}
	}

	/**
	 * Zähle wieviele Möglichkeiten ein Spiel- und ErgebnisKombinationstupel von
	 * den noch übrigen geheimen Möglichkeiten übrig lassen würde.
	 * 
	 * @param ratekombi
	 *            SpielKombination die geraten werden soll
	 * @param ergebnis
	 *            ErgebnisKombination welche herauskommen soll
	 * @param geheimSet
	 *            SpielKombination Set der noch möglichen Lösungen
	 * @return Anzahl der SpielKombinationen die noch eine Lösung sein können
	 */
	private int zaehleUebrigeMoeglichkeiten(final SpielKombination ratekombi,
			final ErgebnisKombination ergebnis,
			final Set<SpielKombination> geheimSet) {
		int anzahlUebrigerMoeglichkeiten = 0;
		if (ratekombi.getSpielSteineCount() != PINS) {
			throw new IllegalArgumentException(
					"Im Moment können nur 4 Pins gelöst werden");
		}
		for (SpielKombination geheim : geheimSet) {
			if (ergebnis.equals(ergebnisBerechner.berechneErgebnis(geheim,
					ratekombi))) {
				anzahlUebrigerMoeglichkeiten++;
			}
		}
		return anzahlUebrigerMoeglichkeiten;
	}

	/**
	 * Errechne die höchste Anzahl an geheimen Kombinationen, die jede geratene
	 * Kombination von den noch übrigen geheimen Möglichkeiten übriglassen
	 * würde.
	 * 
	 * @param geheimSet
	 *            SpielKombination Set der noch möglichen Lösungen
	 */
	private void errechneScoring(final Set<SpielKombination> geheimSet) {
		long score;
		long tempscore;
		scoreMap.clear();
		for (SpielKombination rate : alleMoeglichkeiten) {
			score = -1;
			for (ErgebnisKombination ergebnis : ergebnisMoeglichkeiten) {
				tempscore = zaehleUebrigeMoeglichkeiten(rate, ergebnis,
						geheimSet);
				if (tempscore > score) {
					score = tempscore;
				}
			}
			if (score < geheimSet.size() && score > 0) {
				scoreMap.put(rate, score);
			}
		}
		if (scoreMap.size() < 1) {
			throw new RuntimeException(
					"Score Map hat weniger als einen Eintrag. Da stimmt was nicht...");
		}
	}

	/**
	 * Suche die SpielKombination mit der geringsten WorstCase-Anzahl an
	 * übriggebliebenen Möglichkeiten.
	 * 
	 * @param geheimSet
	 *            SpielKombination Set der noch möglichen Lösungen
	 * @return SpielKombination welche das beste Ergebnis hervorbringen sollte
	 */
	private SpielKombination errechneBesteKombination(
			final Set<SpielKombination> geheimSet) {
		SpielKombination tempkomb = null;
		if (geheimSet.size() == 1) {
			return geheimSet.iterator().next();
		}
		long tempscore = MAXSCORE;
		errechneScoring(geheimSet);
		for (Entry<SpielKombination, Long> tempentry : scoreMap.entrySet()) {
			if (tempentry.getValue() < tempscore) {
				/*
				 * Diese zusätzliche Bedingung generiert einen besseren
				 * Durchschnitt, schafft es aber nicht mehr alle Kombinationen
				 * in unter 6 Schritten zu raten :(
				 */
				// || tempentry.getValue() == tempscore &&
				// geheimSet.contains(tempentry.getKey())) {
				tempscore = tempentry.getValue();
				tempkomb = tempentry.getKey();
			}
		}
		if (tempkomb == null) {
			throw new RuntimeException(
					"Ungültige Kombination für nächsten Zug.");
		}
		return tempkomb;
	}

	@Override
	public SpielKombination getNeuerZug() {
		// Kandidatenmenge ist veränderlich und deshalb als Cache-Schlüssel
		// ungeeignet
		// Kopie der Kandidatenmenge als Schlüssel verwenden!
		final Set<SpielKombination> key = new HashSet<SpielKombination>(
				geheimMoeglichkeiten);
		SpielKombination result = CACHE.get(key);
		if (result == null) {
			result = errechneBesteKombination(geheimMoeglichkeiten);
			CACHE.put(key, result);
		}
		return result;
	}

	@Override
	public void setLetzterZug(final SpielKombination zug,
			final ErgebnisKombination antwort) {
		if (((antwort.getSchwarz() + antwort.getWeiss()) > PINS)
				|| (antwort.getSchwarz() == (PINS - 1) && antwort.getWeiss() == 1)) {
			throw new IllegalArgumentException();
		}
		eleminiereMoeglichkeiten(zug, antwort, geheimMoeglichkeiten);
	}
}
