package org.hitzemann.mms.solver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
/**
 * Implementierung eines rudimentären Textinterfaces.
 * @author simon
 *
 */
public final class TextUserInteraktion implements IUserInteraktion {
	
	/**
	 * Standardkonstruktor.
	 */
	public TextUserInteraktion() {
	}

	@Override
	public ErgebnisKombination frageantwort(final SpielKombination geraten) {
		if (geraten == null) {
			throw new IllegalArgumentException();
		}
		int richtig = 0;
		int position = 0;
		System.out.println("Ich rate folgendes:");
		for (int pos = 0; pos < geraten.getSpielSteineCount(); pos++) {
			System.out.print(geraten.getSpielStein(pos));
			System.out.print(" ");
		}
		System.out.print("\n");
		richtig = readnum("Wieviele Steine sind richtig? ");
		position = readnum("Wieviele Farben sind richtig, aber mit falschen Positionen? ");
		return new ErgebnisKombination(richtig, position);
	}
	
	@Override
	public void gewonnen() {
		System.out.println("Gewonnen!");
	}
	
	/**
	 * Helferlein um einen Text auszugeben und eine Zahl zurückzugeben.
	 * @param frageText Frage, die gestellt werden soll
	 * @return Zahl die zurückgegeben werden soll
	 */
	private int readnum(final String frageText) {
		boolean doLoop;
		int returnnum = 0;
		do {
			doLoop = false;
			System.out.print(frageText);
			String line = null;
			try {
				final BufferedReader is = new BufferedReader(new InputStreamReader(
						System.in));
				line = is.readLine();
				returnnum = Integer.parseInt(line);
			} catch (NumberFormatException ex) {
				System.out.println("Das ist keine Zahl: " + line);
				doLoop = true;
			} catch (IOException e) {
				System.out.println("IO ERROR: " + e);
				doLoop = true;
			}
		} while (doLoop);
		return returnnum;
	}
}
