/**
 * 
 */
package org.hitzemann.mms.solver;

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
	private SortedSet<SpielKombination> rateSet;
	
	/**
	 * Erster zug?
	 */
	private boolean isFirstTurn = true;

	/**
	 * Standardkonstruktor für den Solver.
	 * 
	 * @param berechner
	 *            Objekt, welches IErgebnisBerechnung implementiert
	 */
	public KnuthSolver(final IErgebnisBerechnung berechner) {
		this.ergebnisBerechner = berechner;
		scoreMap = new TreeMap<SpielKombination, Long>();
		initialisiereAlleMoeglichkeiten();
		initialisiereErgebnisMoeglichkeiten();
		geheimMoeglichkeiten = new TreeSet<SpielKombination>(alleMoeglichkeiten);
		rateSet = new TreeSet<SpielKombination>(alleMoeglichkeiten);
	}

	/**
	 * Alle Farbmöglichkeiten der Spielsteine vorberechnen.
	 */
	private void initialisiereAlleMoeglichkeiten() {
		if (alleMoeglichkeiten == null) {
			alleMoeglichkeiten = new TreeSet<SpielKombination>();
			for (SpielStein i1 : SpielStein.values()) {
				for (SpielStein i2 : SpielStein.values()) {
					for (SpielStein i3 : SpielStein.values()) {
						for (SpielStein i4 : SpielStein.values()) {
							final SpielKombination kombi = new SpielKombination(
									i1, i2, i3, i4);
							alleMoeglichkeiten.add(kombi);
						}
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
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(0, 0));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(0, 1));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(0, 2));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(0, 3));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(0, 4));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(1, 0));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(1, 1));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(1, 2));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(1, 3));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(2, 0));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(2, 1));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(2, 2));
		ergebnisMoeglichkeiten.add(new ErgebnisKombination(3, 0));
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
	 * den noch übrigen geheimen Möglichkeiten entfernen würde.
	 * 
	 * @param ratekombi
	 *            SpielKombination die geraten werden soll
	 * @param ergebnis
	 *            ErgebnisKombination welche herauskommen soll
	 * @param geheimSet
	 *            SpielKombination Set der noch möglichen Lösungen
	 * @return Anzahl der SpielKombinationen die keine Lösung mehr sein können
	 */
	private int zaehleEleminierteMoeglichkeiten(
			final SpielKombination ratekombi,
			final ErgebnisKombination ergebnis,
			final Set<SpielKombination> geheimSet) {
		int anzahlEleminierterMoeglichkeiten = 0;
		if (ratekombi.getSpielSteineCount() != PINS) {
			throw new IllegalArgumentException(
					"Im Moment können nur 4 Pins gelöst werden");
		}
		for (SpielKombination geheim : geheimSet) {
			if (!ergebnis.equals(ergebnisBerechner.berechneErgebnis(geheim,
					ratekombi))) {
				anzahlEleminierterMoeglichkeiten++;
			}
		}
		return anzahlEleminierterMoeglichkeiten;
	}

	/**
	 * Errechne die niedrigste Anzahl an geheimen Kombinationen, die jede
	 * geratene Kombination von den noch übrigen geheimen Möglichkeiten
	 * entfernen würde.
	 * 
	 * @param geheimSet
	 *            SpielKombination Set der noch möglichen Lösungen
	 */
	private void errechneScoring(final Set<SpielKombination> geheimSet) {
		long score;
		int tempscore;
		int scoresum;
		scoreMap.clear();
		for (final Iterator<SpielKombination> rateIterator = rateSet.iterator(); rateIterator
				.hasNext();) {
			final SpielKombination rate = rateIterator.next();
			score = MAXSCORE;
			scoresum = 0;
			for (ErgebnisKombination ergebnis : ergebnisMoeglichkeiten) {
				tempscore = zaehleEleminierteMoeglichkeiten(rate, ergebnis,
						geheimSet);
				scoresum += tempscore;
				if (tempscore < score) {
					score = tempscore;
				}
			}
			if (scoresum > 0) {
				scoreMap.put(rate, score);
			} else {
				rateSet.remove(rate);
			}
		}
		if (scoreMap.size() < 1) {
			throw new RuntimeException(
					"Score Map hat weniger als einen Eintrag. Da stimmt was nicht...");
		}
	}

	/**
	 * Suche die SpielKombination mit der höchsten WorstCase-Anzahl an
	 * entfernten Möglichkeiten.
	 * 
	 * @param geheimSet
	 *            SpielKombination Set der noch möglichen Lösungen
	 * @return SpielKombination welche das beste Ergebnis hervorbringen sollte
	 */
	private SpielKombination errechneBesteKombination(
			final Set<SpielKombination> geheimSet) {
		SpielKombination tempkomb = null;
		long tempscore = 0;
		errechneScoring(geheimSet);
		for (Entry<SpielKombination, Long> tempentry : scoreMap.entrySet()) {
			if (tempentry.getValue() > tempscore) {
				tempscore = tempentry.getValue();
				tempkomb = tempentry.getKey();
			}
		}
		return tempkomb;
	}

	/**
	 * Entferne alle geheimen Kombinationen, die nicht das vom User
	 * zurückgegebene Ergebnis zur geratenen Kombination ergeben.
	 * 
	 * @param geraten
	 *            Geratene SpielKombination
	 * @param ergebnis
	 *            ErgebnisKombination für geratene SpielKombination
	 */
	private void entferneMoeglichkeiten(final SpielKombination geraten,
			final ErgebnisKombination ergebnis) {
		eleminiereMoeglichkeiten(geraten, ergebnis, geheimMoeglichkeiten);
	}

	@Override
	public int getNumLoesungen() {
		return ergebnisMoeglichkeiten.size();
	}

	@Override
	public SpielKombination getNeuerZug() {
		if (isFirstTurn) {
			isFirstTurn = false;
			return new SpielKombination(SpielStein.ROT, SpielStein.ROT, SpielStein.GRUEN, SpielStein.GRUEN);
		}
		return errechneBesteKombination(geheimMoeglichkeiten);
	}

	@Override
	public void setLetzterZug(final SpielKombination zug,
			final ErgebnisKombination antwort) {
		if (((antwort.getSchwarz() + antwort.getWeiss()) > PINS)
				|| (antwort.getSchwarz() == (PINS - 1) && antwort.getWeiss() == 1)) {
			throw new IllegalArgumentException();
		}
		entferneMoeglichkeiten(zug, antwort);
	}
}
