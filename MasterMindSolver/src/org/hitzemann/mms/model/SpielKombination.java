package org.hitzemann.mms.model;

import java.util.Arrays;

/**
 * @author simon
 *
 */
public class SpielKombination {
	public SpielKombination(SpielStein... spielSteine) {
		this.spielSteine = spielSteine;
	}

	private SpielStein[] spielSteine;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(spielSteine);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpielKombination other = (SpielKombination) obj;
		if (!Arrays.equals(spielSteine, other.spielSteine))
			return false;
		return true;
	}
	
	public SpielStein getSpielStein(int position) {
		return spielSteine[position];
	}
	
	public int getSpielSteineCount() {
		return spielSteine.length;
	}
}
