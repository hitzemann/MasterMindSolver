package org.hitzemann.mms.solver.rule.knuth;


import java.util.List;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * <p>
 * Eine Regel die immer den ersten Kandidaten rät, und als Folge-Regel immer
 * eine {@link GuessFirstRule} liefert.
 * </p>
 * 
 * <p>
 * Ein Solver, der diese Regel ausführt, wird also wiederholt den ersten
 * möglichen Kandidaten raten und anschließend unmögliche Kandidaten elimieren,
 * bis die geheime Kombination erraten wurde.
 * </p>
 * 
 * <p>
 * Diese Regel wird in Knuth's Algorithmus genau dann verwendet, wenn maximal 2
 * Kandidaten existieren.
 * </p>
 * 
 * @author chschu
 */
public final class GuessFirstRule implements IRule {

	/**
	 * Die minimale Anzahl erwarteter Kandidaten.
	 */
	private final int minimumCandidateCount;

	/**
	 * Die maximale Anzahl erwarteter Kandidaten.
	 */
	private final int maximumCandidateCount;

	/**
	 * Die Factory zur Erzeugung der Folge-Regeln.
	 */
	private final IKnuthRuleFactory ruleFactory;

	/**
	 * Erzeugt eine Instanz.
	 * 
	 * @param theMinimumCandidateCount
	 *            Die minimale Anzahl erwarteter Kandidaten.
	 * @param theMaximumCandidateCount
	 *            Die maximale Anzahl erwarteter Kandidaten.
	 * @param theRuleFactory
	 *            Die Factory zur Erzeugung der Folge-Regeln.
	 */
	public GuessFirstRule(final int theMinimumCandidateCount,
			final int theMaximumCandidateCount,
			final IKnuthRuleFactory theRuleFactory) {
		if (theMinimumCandidateCount > theMaximumCandidateCount) {
			throw new IllegalArgumentException("minimum candidate count is "
					+ theMinimumCandidateCount
					+ ", which is larger than maximum candidate count "
					+ theMaximumCandidateCount);
		}
		if (theMinimumCandidateCount < 0) {
			throw new IllegalArgumentException(
					"minimum candidate count must be at least 0, got "
							+ theMinimumCandidateCount);
		}
		minimumCandidateCount = theMinimumCandidateCount;
		maximumCandidateCount = theMaximumCandidateCount;
		ruleFactory = theRuleFactory;
	}

	@Override
	public SpielKombination getGuess(
			final List<SpielKombination> candidates) {
		final int candidateCount = candidates.size();

		if (candidateCount < minimumCandidateCount) {
			throw new IllegalArgumentException("expected at least "
					+ minimumCandidateCount + " candidates, got "
					+ candidateCount);
		}
		if (candidateCount > maximumCandidateCount) {
			throw new IllegalArgumentException("expected at most "
					+ maximumCandidateCount + " candidates, got "
					+ candidateCount);
		}

		// wir brauchen mindestens einen Kandidaten
		if (candidateCount < 1) {
			throw new IllegalArgumentException(
					"cannot determine a guess if there are no candidates");
		}

		// ersten zulässigen Kandidaten raten
		return candidates.iterator().next();
	}

	@Override
	public IRule getRuleForResponse(final ErgebnisKombination response) {
		// Folge-Regel ist immer eine GuessFirstRule(0,max)
		return ruleFactory.createGuessFirstRule(0, maximumCandidateCount);
	}
}
