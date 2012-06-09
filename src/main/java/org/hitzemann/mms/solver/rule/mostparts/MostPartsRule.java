package org.hitzemann.mms.solver.rule.mostparts;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.ISpielKombinationFactory;
import org.hitzemann.mms.model.SpielKombination;
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
	private final List<SpielKombination> allGuesses;

	/**
	 * Erzeugt eine Instanz.
	 * 
	 * @param theCalculator
	 *            Die zu verwendende Implementierung von
	 *            {@link IErgebnisBerechnung}.
	 * @param factory
	 *            Die zu verwendende Implementierung von
	 *            {@link ISpielKombinationFactory}.
	 * @param pins
	 *            Die Kombinationsgröße.
	 */
	public MostPartsRule(final IErgebnisBerechnung theCalculator,
			final ISpielKombinationFactory factory, final int pins) {
		calculator = theCalculator;
		allGuesses = factory.erzeugeAlle(pins);
	}

	@Override
	public SpielKombination getGuess(
			final Collection<SpielKombination> candidates) {
		// ermittle Kombinationen, die die Anzahl möglicher Antworten maximieren
		int maxResponseCount = 0;
		final List<SpielKombination> maxResponseCountGuesses = new LinkedList<SpielKombination>();
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
		final List<SpielKombination> maxResponseCountCandidates = new LinkedList<SpielKombination>(
				maxResponseCountGuesses);
		maxResponseCountCandidates.retainAll(candidates);

		// Kandidaten bevorzugen, und lexikographisch kleinsten zurückgeben
		final SpielKombination result;
		if (!maxResponseCountCandidates.isEmpty()) {
			result = maxResponseCountCandidates.get(0);
		} else {
			result = maxResponseCountGuesses.get(0);
		}

		return result;
	}

	@Override
	public IRule getRuleForResponse(final ErgebnisKombination response) {
		return this;
	}
}
