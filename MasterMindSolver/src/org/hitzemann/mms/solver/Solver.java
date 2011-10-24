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

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.Pair;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;

/**
 * @author simon
 * 
 */
public class Solver {

	private Map<Pair<SpielKombination, SpielKombination>, ErgebnisKombination> ergebnisMap;
	private IErgebnisBerechnung ergebnisBerechner;
	private Set<SpielKombination> geheimMoeglichkeiten;
	private Set<ErgebnisKombination> ergebnisMoeglichkeiten;
	private Map<SpielKombination, Integer> scoreMap;
	private Set<SpielKombination> rateSet;

	public Solver() {
		scoreMap = new HashMap<SpielKombination, Integer>();
		initialisiere_geheimMoeglichkeiten();
		initialisiere_ergebnisMoeglichkeiten();
		ergebnisBerechner = new DefaultErgebnisBerechner();
		initialisiere_ergebnisMap();
		rateSet = new HashSet<SpielKombination>(geheimMoeglichkeiten);
	}

	/**
	 * Alle möglichen Kombinationen für 4 Steine in das Set schreiben. Ungültige
	 * Lösungen können dann später einfach entfernt werden.
	 */
	private void initialisiere_geheimMoeglichkeiten() {
		geheimMoeglichkeiten = new HashSet<SpielKombination>();
		for (SpielStein i1 : SpielStein.values()) {
			for (SpielStein i2 : SpielStein.values()) {
				for (SpielStein i3 : SpielStein.values()) {
					for (SpielStein i4 : SpielStein.values()) {
						SpielKombination kombi = new SpielKombination(i1, i2,
								i3, i4);
						geheimMoeglichkeiten.add(kombi);
					}
				}
			}
		}
	}

	/**
	 * Für's Iterieren ein Set mit allen gültigen Ergebnissen für 4 Steine
	 * anlegen
	 */
	private void initialisiere_ergebnisMoeglichkeiten() {
		ergebnisMoeglichkeiten = new HashSet<ErgebnisKombination>();
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
		//ergebnisMoeglichkeiten.add(new ErgebnisKombination(4, 0));
	}

	/**
	 * Statische Map aufbauen, die einem Paar aus Steinkombinationen ein
	 * Ergebnis zuordnet
	 */
	private void initialisiere_ergebnisMap() {
		ergebnisMap = new HashMap<Pair<SpielKombination, SpielKombination>, ErgebnisKombination>();
		for (SpielStein i1 : SpielStein.values()) {
			for (SpielStein i2 : SpielStein.values()) {
				for (SpielStein i3 : SpielStein.values()) {
					for (SpielStein i4 : SpielStein.values()) {
						for (SpielStein o1 : SpielStein.values()) {
							for (SpielStein o2 : SpielStein.values()) {
								for (SpielStein o3 : SpielStein.values()) {
									for (SpielStein o4 : SpielStein.values()) {
										SpielKombination kombi1 = new SpielKombination(
												i1, i2, i3, i4);
										SpielKombination kombi2 = new SpielKombination(
												o1, o2, o3, o4);
										ergebnisMap
												.put(new Pair<SpielKombination, SpielKombination>(
														kombi1, kombi2),
														ergebnisBerechner
																.berechneErgebnis(
																		kombi1,
																		kombi2));
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Entferne alle möglichen Geheimkombinationen, die nicht durch die geratene
	 * Kombination und das Ergebnis dargestellt werden können
	 * 
	 * @param ratekombi
	 * @param ergebnis
	 */
	private void eleminiereMoeglichkeiten(SpielKombination ratekombi,
			ErgebnisKombination ergebnis,
			Set<SpielKombination> moeglichkeitenSet) {
		if (ratekombi.getSpielSteineCount() != 4) {
			throw new IllegalArgumentException(
					"Im Moment können nur 4 Pins gelöst werden");
		}
		for (Iterator<SpielKombination> setIterator = moeglichkeitenSet
				.iterator(); setIterator.hasNext();) {
			SpielKombination geheim = setIterator.next();
			if (!ergebnis.equals(ergebnisMap
					.get(new Pair<SpielKombination, SpielKombination>(geheim,
							ratekombi)))) {
				setIterator.remove();
			}
		}
	}

	/**
	 * Zähle wieviele Möglichkeiten ein Spiel- und ErgebnisKombinationstupel von
	 * den noch übrigen geheimen Möglichkeiten entfernen würde
	 * 
	 * @param ratekombi
	 * @param ergebnis
	 * @return
	 */
	private int zaehleEleminierteMoeglichkeiten(SpielKombination ratekombi,
			ErgebnisKombination ergebnis, Set<SpielKombination> geheimSet) {
		int anzahlEleminierterMoeglichkeiten = 0;
		Set<SpielKombination> tempSet = new HashSet<SpielKombination>(geheimSet);
		eleminiereMoeglichkeiten(ratekombi, ergebnis, tempSet);
		anzahlEleminierterMoeglichkeiten = geheimMoeglichkeiten.size()
				- tempSet.size();
		return anzahlEleminierterMoeglichkeiten;
	}

	/**
	 * Errechne die niedrigste Anzahl an geheimen Kombinationen, die jede
	 * geratene Kombination von den noch übrigen geheimen Möglichkeiten
	 * entfernen würde
	 */
	private void errechneScoring(Set<SpielKombination> geheimSet) {
		int score;
		int tempscore;
		int scoresum;
		scoreMap.clear();
		for (Iterator<SpielKombination> rateIterator = rateSet.iterator(); rateIterator
				.hasNext();) {
			SpielKombination rate = rateIterator.next();
			score = 9999;
			scoresum=0;
			for (ErgebnisKombination ergebnis : ergebnisMoeglichkeiten) {
				tempscore = zaehleEleminierteMoeglichkeiten(rate, ergebnis,
						geheimSet);
				// System.err.println(rate + " " + ergebnis + ": " + tempscore);
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
	 * entfernten Möglichkeiten
	 * 
	 * @return
	 */
	private SpielKombination errechneBesteKombination(
			Set<SpielKombination> geheimSet) {
		SpielKombination tempkomb = null;
		int tempscore = 0;
		errechneScoring(geheimSet);
		for (Entry<SpielKombination, Integer> tempentry : scoreMap.entrySet()) {
			// System.err.println(tempentry.getKey() + ": " +
			// tempentry.getValue());
			if (tempentry.getValue() >= tempscore) {
				tempscore = tempentry.getValue();
				tempkomb = tempentry.getKey();
			}
		}
		rateSet.remove(tempkomb);
		return tempkomb;
	}

	/**
	 * Entferne alle geheimen Kombinationen, die nicht das vom User
	 * zurückgegebene Ergebnis zur geratenen Kombination ergeben
	 * 
	 * @param geraten
	 * @param ergebnis
	 */
	private void entferneMoeglichkeiten(SpielKombination geraten,
			ErgebnisKombination ergebnis) {
		eleminiereMoeglichkeiten(geraten, ergebnis, geheimMoeglichkeiten);
	}

	public int getNumLoesungen() {
		return ergebnisMoeglichkeiten.size();
	}
	
	public SpielKombination getNeuerZug() {
		return errechneBesteKombination(geheimMoeglichkeiten);
	}
	
	public void setLetzterZug(SpielKombination zug, ErgebnisKombination antwort) {
		entferneMoeglichkeiten(zug, antwort);
	}
}