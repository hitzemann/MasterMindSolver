package org.hitzemann.mms.solver.rule;

import java.util.Collection;
import java.util.Iterator;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.ISpielKombinationFactory;
import org.hitzemann.mms.model.SpielKombination;
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
	 * @param factory
	 *            Die zu verwendende Implementierung von
	 *            {@link ISpielKombinationFactory}.
	 * @param pins
	 *            Die Kombinationsgröße.
	 * @param theRule
	 *            Die Regel.
	 */
	public RuleSolver(final IErgebnisBerechnung theCalculator,
			final ISpielKombinationFactory factory, final int pins,
			final IRule theRule) {
		calculator = theCalculator;
		rule = theRule;
		candidates = factory.erzeugeAlle(pins);
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
