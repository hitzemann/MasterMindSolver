package org.hitzemann.mms.solver;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import org.hitzemann.mms.model.DefaultSpielKombinationFactory;
import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.ISpielKombinationFactory;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.hitzemann.mms.solver.rule.RuleSolver;
import org.hitzemann.mms.solver.rule.knuth.KnuthRule;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Integrations-Testklasse für einen {@link ISolver}. Erzeugt alle möglichen Kombinationen und lässt eine
 * {@link ISolver}-Instanz gegen eine {@link IErgebnisBerechnung}-Instanz antreten. Die Verarbeitung erfolgt
 * parallelisiert. Nachdem alle Kombinationen getestet wurden, wird die Verteilung der Anzahl Rateversuche ausgegeben.
 * 
 * @author chschu
 */
@RunWith(Parameterized.class)
public final class SolverPerformanceIT {

    /**
     * Anzahl der Pins (Länge der Kombinationen).
     */
    private static final int PINS = 4;

    /**
     * Die gemeinsam benutzte {@link IErgebnisBerechnung}-Instanz.
     */
    private static final IErgebnisBerechnung BERECHNER = new LinearerErgebnisBerechner();

    /**
     * Die gemeinsam benutzte {@link ISpielKombinationFactory}-Instanz.
     */
    private static final ISpielKombinationFactory FACTORY = new DefaultSpielKombinationFactory();

    /**
     * Das Histogramm mit den Häufigkeiten der Anzahl der benötigten Rateversuche.
     */
    private static final SortedMap<Integer, Integer> HISTOGRAMM = new TreeMap<Integer, Integer>();

    /**
     * Die aktuell getestete geheime Kombination.
     */
    private final SpielKombination geheim;

    /**
     * Erzeugt einen Test mit der angegebenen geheimen Kombination.
     * 
     * @param theGeheim
     *            Die geheime Kombination.
     */
    public SolverPerformanceIT(final SpielKombination theGeheim) {
        geheim = theGeheim;
    }

    /**
     * Erzeugt für jede mögliche geheime Kombination ein Parameterobjekt.
     * 
     * @return Liste mit Parameter-Tupeln für den Konstruktor dieser Klasse.
     */
    @Parameters
    public static List<Object[]> erzeugeParameter() {
        final Set<SpielKombination> alle = erzeugeAlleKombinationen(PINS);

        final List<Object[]> parameter = new LinkedList<Object[]>();

        for (SpielKombination geheim : alle) {
            parameter.add(new Object[] { geheim });
        }

        return parameter;
    }

    /**
     * Factory-Methode zum Erzeugen einer neuen {@link ISolver}-Instanz.
     * 
     * @return Eine neue Instanz einer {@link ISolver}-Implementierung.
     */
    private ISolver createSolver() {
        return new RuleSolver(BERECHNER, FACTORY, PINS, new KnuthRule());
    }

    /**
     * Startet den Test für die geheime Kombination in {@link #geheim}.
     * 
     * @throws InterruptedException
     *             Der Haupt-Thread wurde (beim Warten auf Beendigung der Tasks) unterbrochen.
     * @throws ExecutionException
     *             In einem der Tasks ist eine Exception aufgetreten.
     */
    @Test
    public void start() throws InterruptedException, ExecutionException {
        final ISolver solver = createSolver();

        // Spiel starten
        int versuche = 0;
        while (true) {
            final SpielKombination geraten = solver.getNeuerZug();
            final ErgebnisKombination ergebnis = BERECHNER.berechneErgebnis(geheim, geraten);
            versuche++;
            if (ergebnis.getSchwarz() == PINS) {
                // gewonnen
                break;
            }
            solver.setLetzterZug(geraten, ergebnis);
        }

        Integer freq = HISTOGRAMM.get(versuche);
        if (freq == null) {
            freq = 0;
        }
        HISTOGRAMM.put(versuche, freq + 1);
    }

    /**
     * Gibt die Statistik des Lösungsvorgangs aus.
     */
    @AfterClass
    public static void printStats() {
        int sum = 0;
        int count = 0;

        // Histogramm ausgeben
        System.out.println("#guesses;frequency");
        for (Map.Entry<Integer, Integer> e : HISTOGRAMM.entrySet()) {
            System.out.println(e.getKey() + ";" + e.getValue());
            sum += e.getKey() * e.getValue();
            count += e.getValue();
        }
        System.out.println("---");
        System.out.println("average = " + 1.0 * sum / count);
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
    private static void erzeugeAlleKombinationenRekursiv(final SpielStein[] spielSteine, final int iterierOffset,
            final Set<SpielKombination> ergebnisSet) {
        if (iterierOffset < spielSteine.length) {
            for (SpielStein spielStein : SpielStein.values()) {
                spielSteine[iterierOffset] = spielStein;
                erzeugeAlleKombinationenRekursiv(spielSteine, iterierOffset + 1, ergebnisSet);
            }
        } else {
            // Array klonen, damit nicht alle erzeugten Kombinationen das
            // gleiche benutzen
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
    private static Set<SpielKombination> erzeugeAlleKombinationen(final int groesse) {
        final Set<SpielKombination> ergebnisSet = new HashSet<SpielKombination>();
        erzeugeAlleKombinationenRekursiv(new SpielStein[groesse], 0, ergebnisSet);
        return ergebnisSet;
    }
}
