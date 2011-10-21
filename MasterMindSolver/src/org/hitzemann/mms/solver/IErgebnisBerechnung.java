package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

public interface IErgebnisBerechnung {

	public abstract ErgebnisKombination berechneErgebnis(
			SpielKombination geheim, SpielKombination geraten);

}