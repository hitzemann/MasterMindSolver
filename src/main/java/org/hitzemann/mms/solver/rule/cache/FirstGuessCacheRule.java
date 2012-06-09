package org.hitzemann.mms.solver.rule.cache;


import java.util.List;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * <p>
 * Eine Regel, die den von einer anderen Regel zurückgegebenen Zug speichert.
 * </p>
 * 
 * <p>
 * Damit sich der Cache für den Aufrufer neutral verhält, muss die von der
 * gekapselten Regel bei {@link IRule#getGuess(List<SpielKombination>)} zurückgegebene
 * {@link SpielKombination} konstant sein.
 * </p>
 * 
 * @author chschu
 */
public final class FirstGuessCacheRule implements IRule {

	/**
	 * Die gekapselte Regel.
	 */
	private final IRule delegate;

	/**
	 * Der zu verwendende Zug.
	 */
	private SpielKombination guess;

	/**
	 * Erzeugt eine Instanz ohne vorgegebenen Zug.
	 * 
	 * @param theDelegate
	 *            Die gekapselte Regel.
	 */
	public FirstGuessCacheRule(final IRule theDelegate) {
		this(theDelegate, null);
	}

	/**
	 * Erzeugt eine Instanz mit vorgegebenem Zug.
	 * 
	 * @param theDelegate
	 *            Die gekapselte Regel.
	 * @param theGuess
	 *            Der zu verwendende Zug, oder <code>null</code> falls er
	 *            automatisch ermittelt werden soll.
	 */
	public FirstGuessCacheRule(final IRule theDelegate,
			final SpielKombination theGuess) {
		delegate = theDelegate;
		guess = theGuess;
	}

	@Override
	public SpielKombination getGuess(
			final List<SpielKombination> candidates) {
		if (guess == null) {
			guess = delegate.getGuess(candidates);
		}
		return guess;
	}

	@Override
	public IRule getRuleForResponse(final ErgebnisKombination response) {
		return delegate.getRuleForResponse(response);
	}
}
