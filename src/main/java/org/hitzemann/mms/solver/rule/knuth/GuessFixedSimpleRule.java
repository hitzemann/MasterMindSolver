package org.hitzemann.mms.solver.rule.knuth;

import java.util.Collection;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * <p>
 * Eine Regel, die eine feste {@link SpielKombination} rät und unabhängig vom
 * damit erzielten Ergebnis immer die gleiche Folge-Regel liefert.
 * </p>
 * 
 * <p>
 * Diese Regel wird in Knuth's Algorithmus genau dann verwendet, wenn mehr als 2
 * Kandidaten existieren und die Kandidatenmenge mit einem Rateversuch in jedem
 * Fall auf maximal 2 Kandidaten reduziert werden kann.
 * </p>
 * 
 * @author schusterc
 */
public final class GuessFixedSimpleRule implements IRule {

	/**
	 * Die Anzahl erwarteter Kandidaten.
	 */
	private final int expectedCandidateCount;

	/**
	 * Die zu ratende {@link SpielKombination}.
	 */
	private final SpielKombination guess;

	/**
	 * Die Folge-Regel.
	 */
	private final IRule nextRule;

	/**
	 * Erzeugt eine Instanz.
	 * 
	 * @param theExpectedCandidateCount
	 *            Die Anzahl erwarteter Kandidaten.
	 * @param theGuess
	 *            Die zu ratende {@link SpielKombination}.
	 * @param theNextRule
	 *            Die Folge-Regel.
	 */
	public GuessFixedSimpleRule(final int theExpectedCandidateCount,
			final SpielKombination theGuess, final IRule theNextRule) {
		if (theGuess == null) {
			throw new IllegalArgumentException("guess must not be null");
		}
		if (theNextRule == null) {
			throw new IllegalArgumentException("next rule must not be null");
		}
		expectedCandidateCount = theExpectedCandidateCount;
		guess = theGuess;
		nextRule = theNextRule;
	}

	@Override
	public SpielKombination getGuess(
			final Collection<SpielKombination> candidates) {
		final int candidateCount = candidates.size();

		if (candidateCount != expectedCandidateCount) {
			throw new IllegalArgumentException("expected exactly "
					+ expectedCandidateCount + " candidates, got "
					+ candidateCount);
		}

		return guess;
	}

	@Override
	public IRule getRuleForResponse(final ErgebnisKombination response) {
		return nextRule;
	}
}
