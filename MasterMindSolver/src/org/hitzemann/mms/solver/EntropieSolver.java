package org.hitzemann.mms.solver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
 * gegebene Menge von Kandidaten für die geheime Kombination aus.
 * </p>
 * 
 * @author schusterc
 */
public final class EntropieSolver implements ISolver {

    private final IErgebnisBerechnung berechner;

    private final Set<SpielKombination> geheimKandidaten;

    private final Set<SpielKombination> alleKombinationen;

    /**
     * Erzeugt einen {@link EntropieSolver} mit dem angegebenen {@link IErgebnisBerechnung} und der Kombinationsgröße.
     * 
     * @param derBerechner
     *            Der Ergebnis-Berechner.
     * @param groesse
     *            Die Kombinationsgröße.
     */
    public EntropieSolver(final IErgebnisBerechnung derBerechner, final int groesse) {
        berechner = derBerechner;
        alleKombinationen = erzeugeAlleKombinationen(groesse);
        geheimKandidaten = new HashSet<SpielKombination>(alleKombinationen);
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
            return geheimKandidaten.iterator().next();
        }
        return ermittleKombinationMitGroessterEntropie(geheimKandidaten, alleKombinationen);
    }

    @Override
    public void setLetzterZug(final SpielKombination zug, final ErgebnisKombination antwort) {
        eliminiereUnmoeglicheGeheimeKombinationen(geheimKandidaten, zug, antwort);
    }

    /**
     * Eliminiert alle Kombinationen aus der Kandidatenmenge, die mit der übergebenen geratenen Kombination nicht das
     * erwartete Ergebnis liefern.
     * 
     * @param geheimKandidaten
     *            Die Kandidatenmenge. Aus dieser Menge werden die unmöglichen Kandidaten entfernt.
     * @param geraten
     *            Die geratene Kombination.
     * @param sollErgebnis
     *            Das erwartete Ergebnis.
     */
    private void eliminiereUnmoeglicheGeheimeKombinationen(final Set<SpielKombination> geheimKandidaten,
            final SpielKombination geraten, final ErgebnisKombination sollErgebnis) {
        for (Iterator<SpielKombination> i = geheimKandidaten.iterator(); i.hasNext();) {
            SpielKombination geheim = i.next();
            ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);
            if (!ergebnis.equals(sollErgebnis)) {
                i.remove();
            }
        }
    }

    /**
     * Rekursiver Algorithmus zur Erzeugung aller {@link SpielKombination}en.
     * 
     * @param spielSteine
     *            Das rekursiv zu füllende Array mit {@link SpielStein}en. Die Länge dieses Arrays bestimmt die Länge
     *            der erzeugten Kombinationen.
     * @param iterierOffset
     *            Das in diesem Aufruf zu iterierende Offset in das spielSteine-Array. Alle höheren Indizes werden durch
     *            rekursive Aufrufe dieser Methode befüllt.
     * @param ergebnisSet
     *            Das {@link Set} zum Einsammeln der erzeugten {@link SpielKombination}en.
     */
    private void erzeugeAlleKombinationenRekursiv(final SpielStein[] spielSteine, final int iterierOffset,
            final Set<SpielKombination> ergebnisSet) {
        if (iterierOffset < spielSteine.length) {
            for (SpielStein spielStein : SpielStein.values()) {
                spielSteine[iterierOffset] = spielStein;
                erzeugeAlleKombinationenRekursiv(spielSteine, iterierOffset + 1, ergebnisSet);
            }
        } else {
            // Array klonen, damit nicht alle erzeugten Kombinationen das gleiche benutzen
            ergebnisSet.add(new SpielKombination(spielSteine.clone()));
        }
    }

    /**
     * Erzeugt eine Menge mit allen möglichen Kombinationen einer bestimten Größe.
     * 
     * @param groesse
     *            Die Kombinationsgröße
     * @return Ein {@link Set} mit allen möglichen Kombinationen.
     */
    private Set<SpielKombination> erzeugeAlleKombinationen(final int groesse) {
        Set<SpielKombination> ergebnisSet = new HashSet<SpielKombination>();
        erzeugeAlleKombinationenRekursiv(new SpielStein[groesse], 0, ergebnisSet);
        return ergebnisSet;
    }

    /**
     * Ermittelt eine Kombination mit maximaler Entropie, bei gegebener Kandidatenmenge für die geheime Kombination.
     * 
     * @param geheimKandidaten
     *            Die Kandidaten für die geheime Kombination.
     * @param alle
     *            Die Grundgesamtheit aller Kombinationen. Aus dieser wird die zu ratende Kombination ausgewählt.
     * @return Die zu ratende Kombination
     */
    public SpielKombination ermittleKombinationMitGroessterEntropie(final Set<SpielKombination> geheimKandidaten,
            Set<SpielKombination> alle) {
        double maxEntropy = -1.0;
        SpielKombination besteZuRaten = null;

        for (SpielKombination zuRaten : alle) {
            double entropy = ermittleEntropie(zuRaten, geheimKandidaten);
            if (entropy > maxEntropy) {
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
     * @return Der Wert der Entropie.
     */
    private double ermittleEntropie(final SpielKombination zuRaten, final Set<SpielKombination> geheimKandidaten) {
        // absolute Häufigkeiten der Ergebnisse ermitteln
        int[][] absoluteHaeufigkeit = ermittleAbsoluteErgebnisHaeufigkeiten(zuRaten, geheimKandidaten);

        // Entropie der geratenen Kombination berechnen
        double entropy = 0.0;
        int laenge = zuRaten.getSpielSteineCount();
        int anzahlGeheimKandidaten = geheimKandidaten.size();
        for (int schwarz = laenge; schwarz >= 0; schwarz--) {
            for (int weiss = laenge - schwarz; weiss >= 0; weiss--) {
                int absolut = absoluteHaeufigkeit[schwarz][weiss];
                if (absolut != 0) {
                    // relative Häufigkeit des Ergebnisses berechnen
                    // e.getValue() ist wegen getAbsoluteErgebnisHaeufigkeiten(...) nie 0
                    double p = 1.0 * absolut / anzahlGeheimKandidaten;
                    entropy -= p * Math.log(p);
                }
            }
        }

        // Umrechnung wegen Logarithmus zur Basis 2
        entropy /= Math.log(2.0);

        return entropy;
    }

    /**
     * Ermittelt die absoluten Häufigkeiten der Ergebnisse für eine zu ratende Kombination und eine Kandidatenmenge für
     * die geheime Kombination.
     * 
     * @param zuRaten
     *            Die zu ratende Kombination.
     * @param geheimKandidaten
     *            Die Kandidaten für die geheime Kombination.
     * @return Ein zweidimensionales Array, dass für jedes Ergebnis ("schwarz" in der 1. Dimension, "weiß" in der 2.
     *         Dimension) dessen absolute Häufigkeit enthält.
     */
    private int[][] ermittleAbsoluteErgebnisHaeufigkeiten(final SpielKombination zuRaten,
            final Set<SpielKombination> geheimKandidaten) {
        int laenge = zuRaten.getSpielSteineCount();
        int[][] result = new int[laenge + 1][laenge + 1];

        // alle geheimen Kombinationen ausprobieren
        for (SpielKombination geheim : geheimKandidaten) {
            ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, zuRaten);
            result[ergebnis.getSchwarz()][ergebnis.getWeiss()]++;
        }

        return result;
    }
}
