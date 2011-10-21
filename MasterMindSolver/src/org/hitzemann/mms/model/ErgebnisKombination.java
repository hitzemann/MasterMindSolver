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

	private final int schwarz;
	private final int weiss;

	public int getSchwarz() {
		return schwarz;
	}

	public int getWeiss() {
		return weiss;
	}
}
