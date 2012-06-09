package org.hitzemann.mms.solver.rule;


import java.util.List;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

/**
 * <p>
 * Schnittstelle für eine Regel.
 * </p>
 * 
 * <p>
 * Eine Regel liefert einen zu ratenden Kandidaten (
 * {@link #getGuess(List<SpielKombination>)}) und eine Folge-Regel für den Fall, dass dieser
 * Kandidat eine bestimmte Antwort ergeben hat.
 * </p>
 * 
 * @author schusterc
 */
public interface IRule {

	/**
	 * Liefert die zu ratende {@link SpielKombination}.
	 * 
	 * @param candidates
	 *            Die zulässigen Kandidaten.
	 * @return Die zu ratende {@link SpielKombination}, muss nicht zwingend
	 *         einer der zulässigen Kandidaten sein.
	 */
	SpielKombination getGuess(List<SpielKombination> candidates);

	/**
	 * Liefert die Folge-Regel, falls das Raten von der von
	 * {@link #getGuess(List<SpielKombination>)} zurückgegebenen {@link SpielKombination} in
	 * Verbindung mit der geheimen Kombination das angegebene Ergebnis liefert.
	 * 
	 * @param response
	 *            Die Antwort auf die von {@link #getGuess(List<SpielKombination>)} geratene
	 *            {@link SpielKombination}.
	 * @return Die Folge-Regel.
	 */
	IRule getRuleForResponse(ErgebnisKombination response);
}
