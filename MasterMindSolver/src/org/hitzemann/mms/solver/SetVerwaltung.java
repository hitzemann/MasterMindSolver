/**
 * 
 */
package org.hitzemann.mms.solver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.Pair;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;

/**
 * @author simon
 * 
 */
public class SetVerwaltung {
	
	private Map<Pair<SpielKombination,SpielKombination>,ErgebnisKombination> ergebnisMap;
	private IErgebnisBerechnung ergebnisBerechner;
	private Set<SpielKombination> ergebnisMoeglichkeiten;

	public SetVerwaltung() {
			initialisiere_ergebnisMoeglichkeiten();
			ergebnisBerechner = new DefaultErgebnisBerechner();
			initialisiere_ergebnisMap();
			//throw new UnsupportedOperationException("Not yet implemented.");
	}
/**
 * Alle möglichen Kombinationen für 4 Steine in das Set schreiben.
 * Ungültige Lösungen können dann später einfach entfernt werden.
 */
	private void initialisiere_ergebnisMoeglichkeiten() {
		for (SpielStein i1 : SpielStein.values()) {
			for (SpielStein i2 : SpielStein.values()) {
				for (SpielStein i3 : SpielStein.values()) {
					for (SpielStein i4 : SpielStein.values()) {
						SpielKombination kombi = new SpielKombination(i1,i2,i3,i4);
						ergebnisMoeglichkeiten.add(kombi);
					}
				}
			}
		}
	}
	/**
	 * Statische Map aufbauen, die einem Paar aus Steinkombinationen ein Ergebnis zuordnet
	 */
	private void initialisiere_ergebnisMap() {
		ergebnisMap = new HashMap<Pair<SpielKombination,SpielKombination>,ErgebnisKombination>();
		for (SpielStein i1 : SpielStein.values()) {
			for (SpielStein i2 : SpielStein.values()) {
				for (SpielStein i3 : SpielStein.values()) {
					for (SpielStein i4 : SpielStein.values()) {
						for (SpielStein o1 : SpielStein.values()) {
							for (SpielStein o2 : SpielStein.values()) {
								for (SpielStein o3 : SpielStein.values()) {
									for (SpielStein o4 : SpielStein.values()) {
										SpielKombination kombi1 = new SpielKombination(i1,i2,i3,i4);
										SpielKombination kombi2 = new SpielKombination(o1,o2,o3,o4);
										ergebnisMap.put(new Pair<SpielKombination,SpielKombination>(kombi1,kombi2), ergebnisBerechner.berechneErgebnis(kombi1, kombi2));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	// TODO: Berechnen, welche SpielKombinationen durch das Paar SpielKombination,ErgebnisKombination keine möglichen SpielKombinationen mehr sind
	private void eleminiereMoeglichkeiten(SpielKombination ratekombi, ErgebnisKombination ergebnis) {
		if (ratekombi.getSpielSteineCount() != 4) {
			throw new IllegalArgumentException("Im Moment können nur 4 Pins gelöst werden");
		}
		
		for (SpielStein geheim1 : SpielStein.values()) {
			for (SpielStein geheim2 : SpielStein.values()) {
				for (SpielStein geheim3 : SpielStein.values()) {
					for (SpielStein geheim4 : SpielStein.values()) {
						SpielKombination potentielleLoesung = new SpielKombination(geheim1,geheim2,geheim3,geheim4);
						if (!ergebnis.equals(ergebnisBerechner.berechneErgebnis(potentielleLoesung, ratekombi))) {
							ergebnisMoeglichkeiten.remove(potentielleLoesung);
						}
					}
				}
			}
		}
		if (ergebnisMoeglichkeiten.size() < 1) {
			throw new RuntimeException("Liste der potentiellen Möglichkeiten ist leer!");
		}
	}
	// TODO: Berechnen, wieviele der noch möglichen SpielKombinationen durch das Paar SpielKombination,ErgebnisKombination eleminiert werden
	// TODO: Für alle Kombinationen aus einer SpielKombination mit allen ErgebnisKombinationen die niedrigste Anzahl an Eleminierungen aus dem ergebnisMoeglichkeiten Set merken
	// TODO: Aus allen Paaren SpielKombination, Eleminierungen die SpielKombination mit der höchsten Anzahl an Eleminierungen auswählen
	// TODO: Für die letzte Geratene SpielKombination und die zurückgegebene ErgebnisKombination alle ungültigen SpielKombinationen aus dem ergebnisMoeglichkeiten Set entfernen
	
	private int zaehleEleminierungen(SpielKombination geheim, SpielKombination geraten, ErgebnisKombination ergebnis) {
		if (geheim.getSpielSteineCount() != 4) {
			throw new IllegalArgumentException("Im Moment können nur 4 Pins gelöst werden");
		}
		if (geheim.getSpielSteineCount() != geraten.getSpielSteineCount()) {
			throw new IllegalArgumentException("Geheime Kombination und geratene Kombination müssen gleich lang sein.");
		}
		int eleminiert=9999;
		return eleminiert;
	}
}
