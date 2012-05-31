package org.hitzemann.mms.solver.rule.mostparts;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.hitzemann.mms.solver.IErgebnisBerechnung;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * <p>
 * Eine Regel, die immer die Kombination rät, für die die meisten verschiedenen
 * Antworten möglich sind.
 * <p>
 * <p>
 * Zunächst werden alle Kombinationen mit der maximalen Anzahl verschiedener
 * Antworten bei gegebener Kandidatenmenge ermittelt.
 * </p>
 * <p>
 * Anschließend wird geprüft, ob unter diesen Kombinationen Elemente der
 * Kandidatenmenge sind. Ist das der Fall, werden nur diese weiter
 * berücksichtigt.
 * </p>
 * <p>
 * Von den verbleibenden Kombinationen wird die lexikographisch kleinste als zu
 * ratende Kombination geliefert.
 * </p>
 * 
 * @author schusterc
 */
public final class MostPartsRule implements IRule {

	/**
	 * Die zu verwendende Implementierung von {@link IErgebnisBerechnung}.
	 */
	private final IErgebnisBerechnung calculator;

	/**
	 * Alle möglichen {@link SpielKombination}en.
	 */
	private final SortedSet<SpielKombination> allGuesses;

	/**
	 * Erzeugt eine Instanz.
	 * 
	 * @param theCalculator
	 *            Die zu verwendende Implementierung von
	 *            {@link IErgebnisBerechnung}.
	 * @param pins
	 *            Die Kombinationsgröße.
	 */
	public MostPartsRule(final IErgebnisBerechnung theCalculator, final int pins) {
		calculator = theCalculator;
		allGuesses = generateAll(pins);
	}

	@Override
	public SpielKombination getGuess(
			final Collection<SpielKombination> candidates) {
		// ermittle Kombinationen, die die Anzahl möglicher Antworten maximieren
		int maxResponseCount = 0;
		final SortedSet<SpielKombination> maxResponseCountGuesses = new TreeSet<SpielKombination>();
		for (SpielKombination guess : allGuesses) {
			// Anzahl möglicher Antworten für geratene Kombination ermitteln
			final Set<ErgebnisKombination> results = new HashSet<ErgebnisKombination>();
			for (SpielKombination candidate : candidates) {
				results.add(calculator.berechneErgebnis(candidate, guess));
			}
			final int responseCount = results.size();
			if (responseCount > maxResponseCount) {
				maxResponseCountGuesses.clear();
				maxResponseCountGuesses.add(guess);
				maxResponseCount = responseCount;
			} else if (responseCount == maxResponseCount) {
				maxResponseCountGuesses.add(guess);
			}
		}

		// ermittle alle Kandidaten unter optimalen Kombinationen
		final SortedSet<SpielKombination> maxResponseCountCandidates = new TreeSet<SpielKombination>(
				maxResponseCountGuesses);
		maxResponseCountCandidates.retainAll(candidates);

		// Kandidaten bevorzugen, und lexikographisch kleinsten zurückgeben
		final SpielKombination result;
		if (!maxResponseCountCandidates.isEmpty()) {
			result = maxResponseCountCandidates.first();
		} else {
			result = maxResponseCountGuesses.first();
		}

		return result;
	}

	@Override
	public IRule getRuleForResponse(final ErgebnisKombination response) {
		return this;
	}

	/**
	 * Erzeugt eine Liste mit allen möglichen Kombinationen einer bestimten
	 * Größe.
	 * 
	 * @param pins
	 *            Die Kombinationsgröße
	 * @return Ein {@link SortedSet} mit allen möglichen Kombinationen.
	 */
	private static SortedSet<SpielKombination> generateAll(final int pins) {
		final SpielStein[] colors = SpielStein.values();
		final int colorCount = colors.length;

		final SortedSet<SpielKombination> result = new TreeSet<SpielKombination>();

		// Array mit Ordinalwerten der SpielStein-Farben
		final int[] ordinalArray = new int[pins];

		// Schleife bei Überlauf an letzter Stelle beenden
		while (ordinalArray[pins - 1] < colorCount) {
			final SpielStein[] colorArray = new SpielStein[pins];
			for (int i = 0; i < pins; i++) {
				colorArray[i] = colors[ordinalArray[i]];
			}
			result.add(new SpielKombination(colorArray));

			// erste Stelle hochzählen
			ordinalArray[0]++;

			// Überlauf weitergeben (nur bei letzter Stelle nicht)
			for (int i = 0; i < pins - 1 && ordinalArray[i] == colorCount; i++) {
				ordinalArray[i] = 0;
				ordinalArray[i + 1]++;
			}
		}

		return result;
	}
}
