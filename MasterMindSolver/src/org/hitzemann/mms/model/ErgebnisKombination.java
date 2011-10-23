/**
 * 
 */
package org.hitzemann.mms.model;

/**
 * @author simon
 * 
 */
public class ErgebnisKombination {

	public ErgebnisKombination(int schwarz, int weiss) {
		this.schwarz = schwarz;
		this.weiss = weiss;
	}
	// schwarz sind korrekte Steine, weiss sind korrekte Farben mit falschen Positionen
	private final int schwarz;
	private final int weiss;

	public int getSchwarz() {
		return schwarz;
	}

	public int getWeiss() {
		return weiss;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + schwarz;
		result = prime * result + weiss;
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
		ErgebnisKombination other = (ErgebnisKombination) obj;
		if (schwarz != other.schwarz)
			return false;
		if (weiss != other.weiss)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ErgebnisKombination [schwarz=" + schwarz + ", weiss=" + weiss
				+ "]";
	}
	
}
