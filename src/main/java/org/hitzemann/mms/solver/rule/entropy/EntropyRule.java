package org.hitzemann.mms.solver.rule.entropy;

import java.util.List;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.ISpielKombinationFactory;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.IErgebnisBerechnung;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * <p>
 * Eine entropiebasierte Regel.
 * </p>
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
 * Diese Regel wählt anhand dieses Wertes aus allen (!) Kombinationen eine mit maximaler Entropie für eine gegebene
 * Menge von Kandidaten für die geheime Kombination aus. Existieren mehrere Kombinationen mit gleicher Entropie, wird
 * sofern möglich eine gewählt, die einer der Kandidaten für die geheime Kombination ist.
 * </p>
 * <p>
 * Da die Regel wiederholt angewendet werden soll, liefert {@link #getRuleForResponse(ErgebnisKombination)} einfach die
 * aktuelle Instanz.
 * </p>
 * 
 * @author chschu
 */
public final class EntropyRule implements IRule {

    /**
     * Epsilon für Vergleiche von Fließkommazahlen.
     */
    private static final double EPSILON = 1e-9;

    /**
     * Die zu verwendende Implementierung von {@link IErgebnisBerechnung}.
     */
    private final IErgebnisBerechnung calculator;

    /**
     * Eine Liste mit allen ratbaren {@link SpielKombination}en.
     */
    private final List<SpielKombination> allGuesses;

    /**
     * Erzeugt eine Instanz.
     * 
     * @param theCalculator
     *            Die zu verwendende Implementierung von {@link IErgebnisBerechnung}.
     * @param factory
     *            Die zu verwendende Implementierung von {@link ISpielKombinationFactory}.
     * @param pins
     *            Die Kombinationsgröße.
     */
    public EntropyRule(final IErgebnisBerechnung theCalculator, final ISpielKombinationFactory factory, final int pins) {
        calculator = theCalculator;
        allGuesses = factory.erzeugeAlle(pins);
    }

    /**
     * Ermittelt eine Kombination mit maximaler Entropie, bei gegebener Kandidatenmenge für die geheime Kombination.
     * Falls es mehrere Kombinationen mit maximaler Entropie gibt, wird falls möglich eine gewählt, die Element der
     * Kandidatenmenge.
     * 
     * @param candidates
     *            Die Kandidatenmenge für die geheime Kombination.
     * @return Die zu ratende Kombination
     */
    private SpielKombination determineMaximumEntropyGuess(final List<SpielKombination> candidates) {
        double maxEntropy = -1.0;
        SpielKombination result = null;

        for (SpielKombination guess : allGuesses) {
            final double entropy = getEntropy(guess, candidates);
            // größere Entropie oder (gleiche Entropie und Kandidat)
            if (entropy > maxEntropy + EPSILON || entropy > maxEntropy - EPSILON && candidates.contains(guess)) {
                maxEntropy = entropy;
                result = guess;
            }
        }

        return result;
    }

    /**
     * Ermittelt die Entropie für eine zu ratende Kombination, bei gegebener Kandidatenmenge für die geheime
     * Kombination.
     * 
     * @param guess
     *            Die zu ratende Kombination.
     * @param candidates
     *            Die Kandidatenmenge für die geheime Kombination.
     * @return Die Entropie.
     */
    private double getEntropy(final SpielKombination guess, final List<SpielKombination> candidates) {
        // absolute Häufigkeiten der Ergebnisse ermitteln
        final int[] frequencies = determineResponseFrequencies(guess, candidates);

        // Entropie der geratenen Kombination berechnen
        double entropy = 0.0;
        for (int f : frequencies) {
            // nur tatsächlich auftretende Ergebnisse einbeziehen (ansonsten
            // müste log(0) gezogen werden)
            if (f > 0) {
                // relative Häufigkeit des Ergebnisses berechnen und in Entropie
                // einfließen lassen
                final double p = 1.0 * f / candidates.size();
                entropy -= p * Math.log(p);
            }
        }

        // Umrechnung wegen Logarithmus zur Basis 2
        entropy /= Math.log(2.0);

        return entropy;
    }

    /**
     * Ermittelt die absoluten Häufigkeiten der Ergebnisse für eine zu ratende Kombination. Die Zuordnung eines
     * konkreten Ergebnisses zu seiner absoluten Häufigkeit ist für die Berechnung der Entropie irrelevant, deshalb
     * werden hier nur die absoluten Häufigkeiten als int-Array zurückgegeben.
     * 
     * @param candidates
     *            Die Kandidatenmenge für die geheime Kombination.
     * @param zuRaten
     *            Die zu ratende Kombination.
     * 
     * @return Die absoluten Häufigkeiten aller Ergebnisse, ohne Zuordnung zu konkretem Ergebnis (da für Entropie
     *         irrelevant).
     */
    private int[] determineResponseFrequencies(final SpielKombination zuRaten, final List<SpielKombination> candidates) {
        final int pins = zuRaten.getSpielSteineCount();
        final int[] result = new int[(pins + 1) * (pins + 1)];

        // alle geheimen Kombinationen ausprobieren
        for (SpielKombination geheim : candidates) {
            final ErgebnisKombination ergebnis = calculator.berechneErgebnis(geheim, zuRaten);
            final int key = ergebnis.getSchwarz() * (pins + 1) + ergebnis.getWeiss();
            result[key]++;
        }

        return result;
    }

    @Override
    public SpielKombination getGuess(final List<SpielKombination> candidates) {
        return determineMaximumEntropyGuess(candidates);
    }

    @Override
    public IRule getRuleForResponse(final ErgebnisKombination response) {
        return this;
    }
}
