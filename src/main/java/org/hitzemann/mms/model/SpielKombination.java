package org.hitzemann.mms.model;

import java.util.Arrays;

/**
 * @author simon
 *
 */
public class SpielKombination implements Comparable<SpielKombination> {
	/**
	 * Internes Array um die SpielSteine aufzubewahren.
	 */
	private SpielStein[] spielSteine;
	/**
	 * Konstruktor für beliebig viele Steine in der SpielKombination.
	 * @param paramSpielSteine beliebige Anzahl an SpielSteinen
	 */
	public SpielKombination(final SpielStein... paramSpielSteine) {
		this.spielSteine = paramSpielSteine;
	}
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(spielSteine);
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
		final SpielKombination other = (SpielKombination) obj;
		if (!Arrays.equals(spielSteine, other.spielSteine)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Gibt einen SpielStein an einer bestimmten Position zurück.
	 * @param position Position des gewünschten SpielSteines
	 * @return SpielStein an der gewünschen Position
	 */
	public final SpielStein getSpielStein(final int position) {
		return spielSteine[position];
	}
	
	/**
	 * Gibt die Anzahl der SpielSteine zurück, die aufbewahrt werden.
	 * @return Anzahl der SpielSteine in der SpielKombination
	 */
	public final int getSpielSteineCount() {
		return spielSteine.length;
	}

	@Override
	public final String toString() {
		return "SpielKombination [spielSteine=" + Arrays.toString(spielSteine)
				+ "]";
	}
	@Override
	public final int compareTo(final SpielKombination arg0) {
		return this.getValue() - arg0.getValue();
	}
	/**
	 * Gibt den Ordnungswert der Kombination zurück, wird für da Comparable Interface gebraucht.
	 * @return Ordnungswert der SpielKombination
	 */
	public final int getValue() {
		int value = 0;
		final int maxcount = this.getSpielSteineCount();
		for (int stein = 0; stein < maxcount; stein++) {
			value += this.spielSteine[stein].ordinal();
			value *= 10;
		}
		value /= 10;
		return value;
	}
}
