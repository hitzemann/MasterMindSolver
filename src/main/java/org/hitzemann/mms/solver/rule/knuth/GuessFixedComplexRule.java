package org.hitzemann.mms.solver.rule.knuth;


import java.util.List;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * <p>
 * Eine Regel, die eine feste {@link SpielKombination} rät und abhängig vom
 * damit erzielten Ergebnis verschiedene Folge-Regeln liefert.
 * </p>
 * 
 * <p>
 * Diese Regel wird in Knuth's Algorithmus genau dann verwendet, wenn mehr als 2
 * Kandidaten existieren und die Kandidatenmenge mit einem Rateversuch nicht in
 * jedem Fall auf maximal 2 Kandidaten reduziert werden kann.
 * </p>
 * 
 * @author chschu
 */
public final class GuessFixedComplexRule implements IRule {

	/**
	 * Die Anzahl erwarteter Kandidaten.
	 */
	private final int expectedCandidateCount;

	/**
	 * Die zu ratende {@link SpielKombination}.
	 */
	private final SpielKombination guess;

	/**
	 * Das dreieckige zweidimensionale Array der Folge-Regeln. Bei einer
	 * Kombinationsgröße von 4 hat das Array die Struktur
	 * [[04,03,02,01,00],[13,12,11,10],[22,21,20],[31,30],[40]].
	 */
	private final IRule[][] nextRules;

	/**
	 * Erzeugt eine Instanz.
	 * 
	 * @param theExpectedCandidateCount
	 *            Die Anzahl erwarteter Kandidaten.
	 * @param theGuess
	 *            Die zu ratende {@link SpielKombination}.
	 * @param theNextRules
	 *            Das dreieckige zweidimensionale Array der Folge-Regeln. Bei
	 *            einer Kombinationsgröße von 4 hat das Array die Struktur
	 *            [[04,03,02,01,00],[13,12,11,10],[22,21,20],[31,30],[40]].
	 */
	public GuessFixedComplexRule(final int theExpectedCandidateCount,
			final SpielKombination theGuess, final IRule[][] theNextRules) {
		if (theGuess == null) {
			throw new IllegalArgumentException("guess must not be null");
		}
		if (theNextRules == null) {
			throw new IllegalArgumentException("next rules must not be null");
		}
		expectedCandidateCount = theExpectedCandidateCount;
		guess = theGuess;
		nextRules = theNextRules;

		final int size = theGuess.getSpielSteineCount();

		if (nextRules.length != size + 1) {
			throw new IllegalArgumentException("expected array of length "
					+ (size + 1) + ", got " + nextRules.length);
		}

		for (int i = 0; i < size; i++) {
			if (nextRules[i] == null) {
				throw new IllegalArgumentException("next rules at index " + i
						+ " must not be null");
			}
			if (nextRules[i].length != size + 1 - i) {
				throw new IllegalArgumentException(
						"expected sub-array of length " + (size + 1 - i)
								+ " at index " + i + ", got "
								+ nextRules[i].length);
			}
		}
	}

	@Override
	public SpielKombination getGuess(
			final List<SpielKombination> candidates) {
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
		final int j = response.getSchwarz();
		final int k = response.getWeiss();
		// k-ter Eintrag von rechts in der j-ten Zeile (beide 0-basiert)
		return nextRules[j][nextRules[j].length - 1 - k];
	}
}
