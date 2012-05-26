package org.hitzemann.mms.solver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;

/**
 * Ein entropiebasierter EntropieSolver für MasterMind.
 * <p>
 * Die Entropie (mittlerer Informationsgehalt) einer zu ratenden Kombination ist ein Maß für die "Ungewissheit" des zu
 * ihr gehörenden Ergebnisses. Je höher diese Ungewissheit ist, umso höher ist der Informationsgewinn beim Raten der
 * Kombination.
 * </p>
 * <p>
 * Im Folgenden wird angenommen, dass alle Kandidaten für die geheime Kombination gleich wahrscheinlich sind. Ferner sei
 * </p>
 * <ul>
 * <li>f(x,y) := Ergebnis für geratene Kombination x und geheime Kombination y</li>
 * </ul>
 * <p>
 * Der Wert p(S,x,e) sei die Wahrscheinlichkeit des Ergebnisses e für die geratene Kombination x und eine Menge S von
 * Kandidaten für die geheime Kombination. Unter Annahme der oben beschriebenen Gleichverteilung gilt
 * </p>
 * <ul>
 * <li>p(S,x,e) := |{y aus S mit f(x,y)=e}| / |S|</li>
 * </ul>
 * <p>
 * Der Informationsgehalt einer zu ratenden Kombination ergibt sich dann zu
 * </p>
 * <ul>
 * <li>H(S,x) := Summe(p(S,x,e)*log2(p(S,x,e), e aus allen Ergebnissen)</li>
 * </ul>
 * <p>
 * Dieser Algorithmus wählt anhand dieses Wertes aus allen (!) Kombinationen eine mit maximaler Entropie für eine
 * gegebene Menge von Kandidaten für die geheime Kombination aus. Existieren mehrere Kombinationen mit gleicher
 * Entropie, wird sofern möglich eine gewählt, die einer der Kandidaten für die geheime Kombination ist.
 * </p>
 * 
 * @author schusterc
 */
public final class EntropieSolver implements ISolver {

    /**
     * Epsilon für Vergleiche von Fließkommazahlen.
     */
    private static final double EPSILON = 1e-9;

    /**
     * Map mit dem ersten Zug für die jeweilige Anzahl an Pins.
     */
    private static final Map<Integer, SpielKombination> ERSTER_ZUG_CACHE = new HashMap<Integer, SpielKombination>();

    /**
     * Die zu verwendende Implementierung von {@link IErgebnisBerechnung}.
     */
    private final IErgebnisBerechnung berechner;

    /**
     * Die verbleibenden Kandidaten für die geheime Kombination.
     */
    private final Set<SpielKombination> geheimKandidaten;

    /**
     * Alle {@link SpielKombination}en, die für diesen {@link ISolver} in Frage kommen.
     */
    private final Set<SpielKombination> alleKombinationen;

    /**
     * Die Kombinationsgröße.
     */
    private final int groesse;

    /**
     * Kennzeichen, das nur vor dem ersten Zug <code>true</code> ist.
     */
    private boolean ersterZug = true;

    /**
     * Erzeugt einen {@link EntropieSolver} mit dem angegebenen {@link IErgebnisBerechnung} und der Kombinationsgröße.
     * 
     * @param derBerechner
     *            Der Ergebnis-Berechner.
     * @param dieGroesse
     *            Die Kombinationsgröße.
     */
    public EntropieSolver(final IErgebnisBerechnung derBerechner, final int dieGroesse) {
        berechner = derBerechner;
        groesse = dieGroesse;
        alleKombinationen = erzeugeAlleKombinationen(groesse);
        geheimKandidaten = new TreeSet<SpielKombination>(alleKombinationen);
    }

    @Override
    public int getNumLoesungen() {
        return geheimKandidaten.size();
    }

    @Override
    public SpielKombination getNeuerZug() {
        if (geheimKandidaten.isEmpty()) {
            throw new IllegalStateException("keine Kandidaten");
        }
        if (geheimKandidaten.size() == 1) {
            // Spezialfall: nur noch ein Kandidat
            // Entropie-Modell funktioniert nicht, da kein weiterer Informationsgewinn möglich
            return geheimKandidaten.iterator().next();
        }

        SpielKombination result = null;

        // Cache für ersten Zug prüfen
        if (ersterZug) {
            result = ERSTER_ZUG_CACHE.get(groesse);
        }

        // Zug ermitteln, falls nicht schon aus Cache geladen
        if (result == null) {
            result = ermittleKombinationMitGroessterEntropie(geheimKandidaten, alleKombinationen, berechner);

            // ersten Zug im Cache speichern
            if (ersterZug) {
                ERSTER_ZUG_CACHE.put(groesse, result);
                ersterZug = false;
            }
        } else {
            ersterZug = false;
        }

        return result;
    }

    @Override
    public void setLetzterZug(final SpielKombination zug, final ErgebnisKombination antwort) {
        eliminiereUnmoeglicheGeheimeKombinationen(berechner, geheimKandidaten, zug, antwort);
    }

    /**
     * Eliminiert alle Kombinationen aus der Kandidatenmenge, die mit der übergebenen geratenen Kombination nicht das
     * erwartete Ergebnis liefern.
     * 
     * @param berechner
     *            Die zu verwendende Implementierung von {@link IErgebnisBerechnung}.
     * @param geheimKandidaten
     *            Die Kandidatenmenge. Aus dieser Menge werden die unmöglichen Kandidaten entfernt.
     * @param geraten
     *            Die geratene Kombination.
     * @param sollErgebnis
     *            Das erwartete Ergebnis.
     */
    private static void eliminiereUnmoeglicheGeheimeKombinationen(final IErgebnisBerechnung berechner,
            final Set<SpielKombination> geheimKandidaten, final SpielKombination geraten,
            final ErgebnisKombination sollErgebnis) {
        for (final Iterator<SpielKombination> i = geheimKandidaten.iterator(); i.hasNext();) {
            final SpielKombination geheim = i.next();
            final ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);
            if (!ergebnis.equals(sollErgebnis)) {
                i.remove();
            }
        }
    }

    /**
     * Erzeugt eine Menge mit allen möglichen Kombinationen einer bestimten Größe.
     * 
     * @param groesse
     *            Die Kombinationsgröße
     * @return Ein {@link Set} mit allen möglichen Kombinationen.
     */
    private static Set<SpielKombination> erzeugeAlleKombinationen(final int groesse) {
        final SpielStein[] farben = SpielStein.values();
        final int farbZahl = farben.length;

        final Set<SpielKombination> result = new TreeSet<SpielKombination>();

        // Array mit Ordinalwerten der SpielStein-Farben
        final int[] kombi = new int[groesse];

        // Schleife bei Überlauf an letzter Stelle beenden
        while (kombi[groesse - 1] < farbZahl) {
            final SpielStein[] steinKombi = new SpielStein[groesse];
            for (int i = 0; i < groesse; i++) {
                steinKombi[i] = farben[kombi[i]];
            }
            result.add(new SpielKombination(steinKombi));

            // erste Stelle hochzählen
            kombi[0]++;

            // Überlauf weitergeben (nur bei letzter Stelle nicht)
            for (int i = 0; i < groesse - 1 && kombi[i] == farbZahl; i++) {
                kombi[i] = 0;
                kombi[i + 1]++;
            }
        }

        return result;
    }

    /**
     * Ermittelt eine Kombination mit maximaler Entropie, bei gegebener Kandidatenmenge für die geheime Kombination.
     * Falls es mehrere Kombinationen mit maximaler Entropie gibt, wird falls möglich eine gewählt, die ein Kandidat für
     * die geheime Kombination ist.
     * 
     * @param geheimKandidaten
     *            Die Kandidaten für die geheime Kombination.
     * @param alle
     *            Die Grundgesamtheit aller Kombinationen. Aus dieser wird die zu ratende Kombination ausgewählt.
     * @param berechner
     *            Die zu verwendende Implementierung von {@link IErgebnisBerechnung}.
     * 
     * @return Die zu ratende Kombination
     */
    private static SpielKombination ermittleKombinationMitGroessterEntropie(
            final Set<SpielKombination> geheimKandidaten, final Set<SpielKombination> alle,
            final IErgebnisBerechnung berechner) {
        double maxEntropy = -1.0;
        SpielKombination besteZuRaten = null;

        for (SpielKombination zuRaten : alle) {
            final double entropy = ermittleEntropie(zuRaten, geheimKandidaten, berechner);
            // größere Entropie oder (gleiche Entropie und Kandidat)
            if (entropy > maxEntropy + EPSILON || entropy > maxEntropy - EPSILON && geheimKandidaten.contains(zuRaten)) {
                maxEntropy = entropy;
                besteZuRaten = zuRaten;
            }
        }

        return besteZuRaten;
    }

    /**
     * Ermittelt die Entropie für eine zu ratende Kombination, bei gegebener Kandidatenmenge für die geheime
     * Kombination.
     * 
     * @param zuRaten
     *            Die zu ratende Kombination.
     * @param geheimKandidaten
     *            Die Kandidaten für die geheime Kombination.
     * @param berechner
     *            Die zu verwendende Implementierung von {@link IErgebnisBerechnung}.
     * 
     * @return Der Wert der Entropie.
     */
    private static double ermittleEntropie(final SpielKombination zuRaten,
            final Set<SpielKombination> geheimKandidaten, final IErgebnisBerechnung berechner) {
        // absolute Häufigkeiten der Ergebnisse ermitteln
        final int[] absoluteHaeufigkeiten = ermittleAbsoluteErgebnisHaeufigkeiten(zuRaten, geheimKandidaten, berechner);

        // Entropie der geratenen Kombination berechnen
        double entropy = 0.0;
        for (int e : absoluteHaeufigkeiten) {
            // nur tatsächlich auftretende Ergebnisse einbeziehen (ansonsten müste log(0) gezogen werden)
            if (e > 0) {
                // relative Häufigkeit des Ergebnisses berechnen und in Entropie einfließen lassen
                final double p = 1.0 * e / geheimKandidaten.size();
                entropy -= p * Math.log(p);
            }
        }

        // Umrechnung wegen Logarithmus zur Basis 2
        entropy /= Math.log(2.0);

        return entropy;
    }

    /**
     * Ermittelt die absoluten Häufigkeiten der Ergebnisse für eine zu ratende Kombination und eine Kandidatenmenge für
     * die geheime Kombination. Die Zuordnung eines konkreten Ergebnisses zu seiner absoluten Häufigkeit ist für die
     * Berechnung der Entropie irrelevant, deshalb werden hier nur die absoluten Häufigkeiten als int-Array
     * zurückgegeben.
     * 
     * @param zuRaten
     *            Die zu ratende Kombination.
     * @param geheimKandidaten
     *            Die Kandidaten für die geheime Kombination.
     * @param berechner
     *            Die zu verwendende Implementierung von {@link IErgebnisBerechnung}.
     * 
     * @return Die absoluten Häufigkeiten aller Ergebnisse, ohne Zuordnung zu konkretem Ergebnis (für Entropie
     *         irrelevant).
     */
    private static int[] ermittleAbsoluteErgebnisHaeufigkeiten(final SpielKombination zuRaten,
            final Set<SpielKombination> geheimKandidaten, final IErgebnisBerechnung berechner) {
        final int pins = zuRaten.getSpielSteineCount();
        final int[] result = new int[(pins + 1) * (pins + 1)];

        // alle geheimen Kombinationen ausprobieren
        for (SpielKombination geheim : geheimKandidaten) {
            final ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, zuRaten);
            final int key = ergebnis.getSchwarz() * (pins + 1) + ergebnis.getWeiss();
            result[key]++;
        }

        return result;
    }
}
