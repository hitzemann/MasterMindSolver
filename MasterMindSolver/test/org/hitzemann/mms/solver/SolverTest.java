package org.hitzemann.mms.solver;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests für {@link KnuthSolver} und {@link EntropieSolver}.
 * 
 * @author schusterc
 */
@RunWith(Parameterized.class)
public final class SolverTest {

    /**
     * Die Factory zur Erzeugung neuer {@link ISolver}-Instanzen.
     */
    private final ISolverFactory solverFactory;

    /**
     * Die gemeinsam benutzte {@link IErgebnisBerechnung}-Instanz.
     */
    private static final IErgebnisBerechnung BERECHNER = new LinearerErgebnisBerechner();

    /**
     * Erzeugt einen parametrisierten Testdurchlauf mit der angegebenen Implementierung von {@link ISolver}.
     * 
     * @param aktuelleSolverFactory
     *            Die Factory zur Erzeugung neuer {@link ISolver}-Instanzen.
     */
    public SolverTest(final ISolverFactory aktuelleSolverFactory) {
        solverFactory = aktuelleSolverFactory;
    }

    /**
     * Erzeugt die Parameter für den Test, die dann vom {@link Parameterized}-Runner als Argument an den Konstruktor
     * übergeben werden.
     * 
     * @return Liste mit einem {@link Object}-Array pro parametrisiertem Testlauf.
     */
    @Parameters
    public static List<Object[]> erzeugeParameter() {
        return Arrays.asList(new Object[] { new KnuthSolverFactory() }, new Object[] { new EntropieSolverFactory() });
    }

    /**
     * Test für den Lösungsalgorithmus. Überprüft lediglich, dass keine Kombination zweimal geraten wird. Damit ist
     * sichergestellt, dass der Algorithmus irgendwann die korrekte Kombination rät.
     */
    @Test
    public void testAlgorithmus() {
        // Deterministischer Zufallszahlengenerator
        final Random rng = new Random(1);

        for (int i = 0; i < 20; i++) {
            // neue Solver-Instanz erzeugen
            final ISolverInfo solverInfo = solverFactory.createISolver();
            final ISolver solver = solverInfo.getSolver();
            final int pins = solverInfo.getAnzahlPins();

            // zufällige geheime Kombination erzeugen
            final SpielStein[] alleSteine = SpielStein.values();
            final SpielStein[] geheimArray = new SpielStein[pins];
            for (int j = 0; j < pins; j++) {
                geheimArray[j] = alleSteine[rng.nextInt(alleSteine.length)];
            }
            final SpielKombination geheim = new SpielKombination(geheimArray);

            // Set mit bereits geratenen Kombinationen
            final Set<SpielKombination> geratenSet = new HashSet<SpielKombination>();

            while (true) {
                // neue geratene Kombination vom Solver holen
                final SpielKombination geraten = solver.getNeuerZug();

                // Prüfung auf richtig geratene Kombination
                if (geraten.equals(geheim)) {
                    break;
                }

                // Prüfen dass Kombination noch nicht geraten wurde
                assertTrue(geratenSet.add(geraten));

                // Ergebnis zu geratener und geheimer Kombination berechnen
                final ErgebnisKombination ergebnis = BERECHNER.berechneErgebnis(geheim, geraten);

                // Solver aktualisieren
                solver.setLetzterZug(geraten, ergebnis);
            }
        }
    }

    /**
     * Informations-Kapsel für einen von einer {@link ISolverFactory} erzeugten {@link ISolver}.
     * 
     * @author schusterc
     */
    private interface ISolverInfo {

        /**
         * Liefert die erzeugte {@link ISolver}-Instanz. Mehrfache Aufrufe dieser Methode auf der gleichen
         * {@link ISolverInfo} -Instanz liefert immer dieselbe {@link ISolver}-Instanz.
         * 
         * @return Die {@link ISolver}-Instanz.
         */
        ISolver getSolver();

        /**
         * Liefert die Anzahl der Pins (Größe der geratenen und geheimen Kombinationen), die die erzeugte
         * {@link ISolver}-Instanz verarbeiten kann.
         * 
         * @return Die Anzahl der Pins.
         */
        int getAnzahlPins();
    }

    /**
     * Schnittstelle für eine Factory, die {@link ISolver}-Instanzen erzeugt.
     * 
     * @author schusterc
     */
    private interface ISolverFactory {

        /**
         * Erzeugt eine neue {@link ISolver}-Instanz.
         * 
         * @return Eine {@link ISolverInfo}-Instanz mit der neuen {@link ISolver}-Instanz.
         */
        ISolverInfo createISolver();
    }

    /**
     * Solver-Factory für {@link KnuthSolver}-Instanzen mit 4 Pins.
     * 
     * @author schusterc
     */
    private static final class KnuthSolverFactory implements ISolverFactory {

        @Override
        public ISolverInfo createISolver() {
            final ISolver newSolver = new KnuthSolver(BERECHNER);
            return new ISolverInfo() {
                @Override
                public ISolver getSolver() {
                    return newSolver;
                }

                @Override
                public int getAnzahlPins() {
                    return 4;
                }
            };
        }
    };

    /**
     * Solver-Factory für {@link EntropieSolver}-Instanzen mit variabler Pin-Anzahl (1 bis 6).
     * 
     * @author schusterc
     */
    private static final class EntropieSolverFactory implements ISolverFactory {

        /**
         * Deterministischer Zufallszahlengenerator.
         */
        private final Random rng = new Random(1);

        @Override
        public ISolverInfo createISolver() {
            // zufällige Anzahl Pins
            final int pins = rng.nextInt(6) + 1;
            final ISolver newSolver = new EntropieSolver(BERECHNER, pins);
            return new ISolverInfo() {
                @Override
                public ISolver getSolver() {
                    return newSolver;
                }

                @Override
                public int getAnzahlPins() {
                    return pins;
                }
            };
        }
    };
}
