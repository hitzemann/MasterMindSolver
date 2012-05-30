package org.hitzemann.mms.solver.rule.knuth;

import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * Factory-Schnittstelle zur Erzeugung der benötigten Regel-Typen.
 * 
 * @author schusterc
 */
public interface IKnuthRuleFactory {

	/**
	 * Erzeugt eine {@link GuessFirstRule}.
	 * 
	 * @param minimumCandidateCount
	 *            Die minimale Anzahl erwarteter Kandidaten.
	 * @param maximumCandidateCount
	 *            Die maximale Anzahl erwarteter Kandidaten.
	 * @return Die neu erzeugte Regel-Instanz.
	 */
	IRule createGuessFirstRule(int minimumCandidateCount,
			int maximumCandidateCount);

	/**
	 * Erzeugt eine {@link GuessFixedSimpleRule}.
	 * 
	 * @param expectedCandidateCount
	 *            Die Anzahl erwarteter Kandidaten.
	 * @param guess
	 *            Die zu ratende {@link SpielKombination}.
	 * @param nextRule
	 *            Die Folge-Regel.
	 * @return Die neu erzeugte Regel-Instanz.
	 */
	IRule createGuessFixedSimpleRule(int expectedCandidateCount,
			SpielKombination guess, IRule nextRule);

	/**
	 * Erzeugt eine {@link GuessFixedComplexRule}.
	 * 
	 * @param expectedCandidateCount
	 *            Die Anzahl erwarteter Kandidaten.
	 * @param guess
	 *            Die zu ratende {@link SpielKombination}.
	 * @param nextRules
	 *            Das dreieckige zweidimensionale Array der Folge-Regeln. Bei
	 *            einer Kombinationsgröße von 4 hat das Array die Struktur
	 *            [[04,03,02,01,00],[13,12,11,10],[22,21,20],[31,30],[40]].
	 * @return Die neu erzeugte Regel-Instanz.
	 */
	IRule createGuessFixedComplexRule(int expectedCandidateCount,
			SpielKombination guess, IRule[][] nextRules);
}
