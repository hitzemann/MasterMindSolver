package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

public interface ISolver {

	int getNumLoesungen();

	SpielKombination getNeuerZug();

	void setLetzterZug(SpielKombination zug, ErgebnisKombination antwort);

}