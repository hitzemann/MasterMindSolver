package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;

public interface IUserInteraktion {

	public abstract ErgebnisKombination frageantwort(SpielKombination geraten);
}
