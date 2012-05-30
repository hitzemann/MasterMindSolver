package org.hitzemann.mms.solver.rule;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.hitzemann.mms.solver.IErgebnisBerechnung;
import org.hitzemann.mms.solver.ISolver;

/**
 * <p>
 * Ein Solver, der eine {@link IRule} zum Lösen verwendet.
 * </p>
 * 
 * @author schusterc
 */
public class RuleSolver implements ISolver {

	/**
	 * Die zu verwendende Implementierung von {@link IErgebnisBerechnung}.
	 */
	private final IErgebnisBerechnung calculator;

	/**
	 * Die Regel.
	 */
	private IRule rule;

	/**
	 * Die Kandidaten für die geheime Kombination.
	 */
	private final Collection<SpielKombination> candidates;

	/**
	 * Erzeugt eine Instanz.
	 * 
	 * @param theCalculator
	 *            Die zu verwendende Implementierung von
	 *            {@link IErgebnisBerechnung}.
	 * @param pins
	 *            Die Regel.
	 * @param theRule
	 *            Die Kandidaten für die geheime Kombination.
	 */
	public RuleSolver(final IErgebnisBerechnung theCalculator, final int pins,
			final IRule theRule) {
		calculator = theCalculator;
		rule = theRule;
		candidates = generateAll(pins);
	}

	@Override
	public final SpielKombination getNeuerZug() {
		// von der Regel gelieferte Kombination raten
		final SpielKombination guess = rule.getGuess(candidates);
		return guess;
	}

	@Override
	public final void setLetzterZug(final SpielKombination zug,
			final ErgebnisKombination antwort) {
		// unmögliche Kandidaten elimineren
		eliminate(zug, antwort);

		// weiter zur Folge-Regel
		rule = rule.getRuleForResponse(antwort);
	}

	/**
	 * Erzeugt alle {@link SpielKombination}en.
	 * 
	 * @param pins
	 *            Die Länge der {@link SpielKombination}en.
	 * @return Alle {@link SpielKombination}en der angegebenen Länge.
	 */
	private static Collection<SpielKombination> generateAll(final int pins) {
		final int valueCount = SpielStein.values().length;

		final Collection<SpielKombination> result = new TreeSet<SpielKombination>();

		// Array mit Ordinalzahlen (1-basiert) der aktuellen Kombination
		final int[] ord = new int[pins];

		// mit (1,1,...,1) initialisieren
		for (int i = 0; i < pins; i++) {
			ord[i] = 1;
		}

		// Schleife abbrechen wenn die letzte Stelle überläuft
		while (ord[pins - 1] <= valueCount) {
			result.add(new SpielKombination(ord));

			// erste Stelle erhöhen
			ord[0]++;

			// Überlauf weitergeben (außer letzte Stelle)
			for (int i = 0; i < pins - 1 && ord[i] > valueCount; i++) {
				ord[i] = 1;
				ord[i + 1]++;
			}
		}

		return result;
	}

	/**
	 * Eliminiert Kandidaten anhand einer geratenen Kombination und der
	 * daraufhin erhaltenen Antwort. Modifiziert den Zustand des Solvers.
	 * 
	 * @param guess
	 *            Die geratene Kombination.
	 * @param response
	 *            Die Antwort.
	 */
	private void eliminate(final SpielKombination guess,
			final ErgebnisKombination response) {
		for (final Iterator<SpielKombination> i = candidates.iterator(); i
				.hasNext();) {
			final SpielKombination candidate = i.next();
			final ErgebnisKombination actualResponse = calculator
					.berechneErgebnis(candidate, guess);
			if (!actualResponse.equals(response)) {
				i.remove();
			}
		}
	}
}
