package org.hitzemann.mms.model;

import java.util.List;

/**
 * Factory-Schnittstelle für die Erzeugung von {@link SpielKombination}en.
 * 
 * @author chschu
 */
public interface ISpielKombinationFactory {

	/**
	 * Erzeugt alle {@link SpielKombination}en einer bestimmten Länge.
	 * 
	 * @param pins
	 *            Die Länge der {@link SpielKombination}en. Darf nicht negativ
	 *            sein.
	 * @return Eine gemäß {@link SpielKombination#compareTo(SpielKombination)}
	 *         aufsteigend sortierte {@link List} mit allen
	 *         {@link SpielKombination}en der angegebenen Länge.
	 * @throws IllegalArgumentException
	 *             Übergebene Länge ist negativ.
	 */
	List<SpielKombination> erzeugeAlle(int pins);
}
