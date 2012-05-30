package org.hitzemann.mms.solver.rule.knuth;

import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * Die Standard-Implementierung der {@link IKnuthRuleFactory}.
 * 
 * @author schusterc
 */
public final class KnuthRuleFactory implements IKnuthRuleFactory {

	@Override
	public IRule createGuessFirstRule(final int minimumCandidateCount,
			final int maximumCandidateCount) {
		return new GuessFirstRule(minimumCandidateCount, maximumCandidateCount,
				this);
	}

	@Override
	public IRule createGuessFixedSimpleRule(final int expectedCandidateCount,
			final SpielKombination guess, final IRule nextRule) {
		return new GuessFixedSimpleRule(expectedCandidateCount, guess, nextRule);
	}

	@Override
	public IRule createGuessFixedComplexRule(final int expectedCandidateCount,
			final SpielKombination guess, final IRule[][] nextRules) {
		return new GuessFixedComplexRule(expectedCandidateCount, guess,
				nextRules);
	}
}
