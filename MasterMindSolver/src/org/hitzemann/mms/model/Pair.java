package org.hitzemann.mms.model;

/**
 * Generische Klasse um Paare von beliebigen Objekten zu bilden.
 * @author simon
 *
 * @param <TA> Erstes Objekt des Paares
 * @param <TB> Zweites Objekt des Paares
 */
public class Pair <TA, TB> {
	/**
	 * Standard Konstruktor für ein Paar.
	 * @param paramFirst Erstes Objekt des Paares
	 * @param paramSecond Zweites Objekt des Paares
	 */
	public Pair(final TA paramFirst, final TB paramSecond) {
		this.first = paramFirst;
		this.second = paramSecond;
	}
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pair<?, ?> other = (Pair<?, ?>) obj;
		if (first == null) {
			if (other.first != null) {
				return false;
			}
		} else if (!first.equals(other.first)) {
			return false;
		}
		if (second == null) {
			if (other.second != null) {
				return false;
			}
		} else if (!second.equals(other.second)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Standard Getter für erstes Objekt des Paares.
	 * @return Erstes Objekt des Paares
	 */
	public final TA getFirst() {
		return first;
	}
	/**
	 * Standard Getter für zweites Objekt des Paares.
	 * @return Zweites Objekt des Paares
	 */
	public final TB getSecond() {
		return second;
	}
	/**
	 * Erstes Objekt des Paares.
	 */
	private final TA first;
	/**
	 * Zweites Objekt des Paares.
	 */
	private final TB second;
	
	

}
