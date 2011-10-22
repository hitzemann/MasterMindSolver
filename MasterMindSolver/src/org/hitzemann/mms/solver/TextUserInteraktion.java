package org.hitzemann.mms.solver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

public class TextUserInteraktion implements IUserInteraktion {

	@Override
	public ErgebnisKombination frageantwort(SpielKombination geraten) {
		if (geraten == null) {
			throw new IllegalArgumentException();
		}
		int richtig = 0;
		int position = 0;
		boolean doLoop = false;
		System.out.println("Ich rate folgendes:");
		for (int pos = 0; pos < geraten.getSpielSteineCount(); pos++) {
			System.out.print(geraten.getSpielStein(pos));
			System.out.print(" ");
		}
		System.out.print("\n");

		do {
			doLoop = false;
			System.out.print("Wieviele Steine sind richtig?");
			String line = null;
			try {
				BufferedReader is = new BufferedReader(new InputStreamReader(
						System.in));
				line = is.readLine();
				richtig = Integer.parseInt(line);
			} catch (NumberFormatException ex) {
				System.out.println("Das ist keine Zahl: " + line);
				doLoop = true;
			} catch (IOException e) {
				System.out.println("IO ERROR: " + e);
				doLoop = true;
			}
		} while (doLoop);
		do {
			doLoop = false;
			System.out.print("Wieviele Farben sind richtig, aber mit falschen Positionen?");
			String line = null;
			try {
				BufferedReader is = new BufferedReader(new InputStreamReader(
						System.in));
				line = is.readLine();
				position = Integer.parseInt(line);
			} catch (NumberFormatException ex) {
				System.out.println("Das ist keine Zahl: " + line);
				doLoop = true;
			} catch (IOException e) {
				System.out.println("IO ERROR: " + e);
				doLoop = true;
			}
		} while (doLoop);
		
		return new ErgebnisKombination(richtig, position);
	}
	
	@Override
	public void gewonnen() {
		System.out.println("Gewonnen!");
		System.exit(0);
	}
}
