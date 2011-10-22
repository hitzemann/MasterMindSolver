package org.hitzemann.mms.solver;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;

/**
 * Implementierung von {@link IErgebnisBerechnung} mit linearer Laufzeit.
 * 
 * <p>
 * Das Ergebnis wird folgendermaßen bestimmt:
 * </p>
 * 
 * <ul>
 * <li>schwarz := Summe(schwarzProFarbe[X], X über alle Farben}</li>
 * <li>weiss := Summe(Minimum(geheimProFarbe[X], geratenProFarbe[X]) - schwarzProFarbe[X], X über alle Farben}</li>
 * </ul>
 * 
 * <p>
 * Die verwendeten Werte berechnen sich so:
 * </p>
 * 
 * <ul>
 * <li>schwarzProFarbe[X] := Anzahl der vollständig korrekt geratenen Spielsteine mit der Farbe X</li>
 * <li>geheimProFarbe[X] := Anzahl der Spielsteine mit der Farbe X in der geheimen Kombination</li>
 * <li>geratenProFarbe[X] := Anzahl der Spielsteine mit der Farbe X in der geratenen Kombination</li>
 * </ul>
 * 
 * @author chschu
 */
public final class LinearerErgebnisBerechner implements IErgebnisBerechnung {

    @Override
    public ErgebnisKombination berechneErgebnis(final SpielKombination geheim, final SpielKombination geraten) {
        int groesse = geheim.getSpielSteineCount();
        if (geraten.getSpielSteineCount() != groesse) {
            throw new IllegalArgumentException("Spielkombinationen haben unterschiedliche Größen!");
        }

        int anzahlFarben = SpielStein.values().length;

        // benötigte Teilwerte ermitteln (pro Farbe zählen)
        int[] schwarzProFarbe = new int[anzahlFarben];
        int[] geheimProFarbe = new int[anzahlFarben];
        int[] geratenProFarbe = new int[anzahlFarben];
        for (int i = 0; i < groesse; i++) {
            int geheimFarbe = geheim.getSpielStein(i).ordinal();
            int geratenFarbe = geraten.getSpielStein(i).ordinal();
            geheimProFarbe[geheimFarbe]++;
            geratenProFarbe[geratenFarbe]++;
            if (geheimFarbe == geratenFarbe) {
                schwarzProFarbe[geheimFarbe]++;
            }
        }

        // Summen bilden (über alle Farben)
        int schwarz = 0;
        int weiss = 0;
        for (int i = 0; i < anzahlFarben; i++) {
            schwarz += schwarzProFarbe[i];
            weiss += Math.min(geheimProFarbe[i], geratenProFarbe[i]) - schwarzProFarbe[i];
        }

        return new ErgebnisKombination(schwarz, weiss);
    }
}
