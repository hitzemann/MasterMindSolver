package org.hitzemann.mms.solver;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.hitzemann.mms.solver.rule.IRule;
import org.hitzemann.mms.solver.rule.RuleSolver;
import org.hitzemann.mms.solver.rule.cache.CacheRuleFactory;
import org.hitzemann.mms.solver.rule.entropy.EntropyRule;
import org.hitzemann.mms.solver.rule.knuth.KnuthRule;
import org.hitzemann.mms.solver.rule.mostparts.MostPartsRule;
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
public final class SolverIT {

    /**
     * Die gemeinsam benutzte {@link IErgebnisBerechnung}-Instanz.
     */
    private static final IErgebnisBerechnung BERECHNER = new LinearerErgebnisBerechner();

    /**
     * Die Factory zur Erzeugung neuer {@link ISolver}-Instanzen.
     */
    private final ISolverFactory solverFactory;

    /**
     * Die Anzahl der durchzuführenden Testläufe.
     */
    private final int repeatCount;

    /**
     * Erzeugt einen parametrisierten Testdurchlauf mit der angegebenen Implementierung von {@link ISolver}.
     * 
     * @param aktuelleSolverFactory
     *            Die Factory zur Erzeugung neuer {@link ISolver}-Instanzen.
     * @param aktuellerRepeatCount
     *            Die Anzahl der durchzuführenden Testläufe.
     */
    public SolverIT(final ISolverFactory aktuelleSolverFactory, final int aktuellerRepeatCount) {
        solverFactory = aktuelleSolverFactory;
        repeatCount = aktuellerRepeatCount;
    }

    /**
     * Erzeugt die Parameter für den Test, die dann vom {@link Parameterized}-Runner als Argument an den Konstruktor
     * übergeben werden.
     * 
     * @return Liste mit einem {@link Object}-Array pro parametrisiertem Testlauf.
     */
    @Parameters
    public static List<Object[]> erzeugeParameter() {
        final List<Object[]> parameter = new LinkedList<Object[]>();
        parameter.add(new Object[] { new KnuthSolverFactory(), 5 });
        parameter.add(new Object[] { new CachedEntropyRuleSolverFactory(3), 1000 });
        parameter.add(new Object[] { new CachedEntropyRuleSolverFactory(4), 100 });
        parameter.add(new Object[] { new CachedEntropyRuleSolverFactory(5), 1 });
        parameter.add(new Object[] { new KnuthRuleSolverFactory(), 1000 });
        parameter.add(new Object[] { new CachedMostPartsRuleSolverFactory(3), 10000 });
        parameter.add(new Object[] { new CachedMostPartsRuleSolverFactory(4), 1000 });
        return parameter;
    }

    /**
     * Test für den Lösungsalgorithmus. Überprüft lediglich, dass keine Kombination zweimal geraten wird. Damit ist
     * sichergestellt, dass der Algorithmus irgendwann die korrekte Kombination rät.
     */
    @Test
    public void testAlgorithmus() {
        // Deterministischer Zufallszahlengenerator
        final Random rng = new Random(1);

        for (int i = 0; i < repeatCount; i++) {
            // neue Solver-Instanz erzeugen
            final ISolverInfo solverInfo = solverFactory.createSolverInfo();
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
        ISolverInfo createSolverInfo();
    }

    /**
     * Solver-Factory für {@link KnuthSolver}-Instanzen mit 4 Pins.
     * 
     * @author schusterc
     */
    private static final class KnuthSolverFactory implements ISolverFactory {

        @Override
        public ISolverInfo createSolverInfo() {
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
     * Solver-Factory für {@link RuleSolver}-Instanzen mit {@link EntropyRule}
     * und variabler Pin-Anzahl. Die eigentliche Regel wird mit einer
     * {@link org.hitzemann.mms.solver.rule.cache.CacheRule} umhüllt.
     * 
     * @author schusterc
     */
    private static final class CachedEntropyRuleSolverFactory implements ISolverFactory {

        /**
         * Anzahl der Pins.
         */
        private final int pins;

        /**
         * Die zu verwendende Regel.
         */
        private final IRule rule;

        /**
         * Erzeugt eine Factory für die angegebene Anzahl Pins.
         * 
         * @param pinCount
         *            Die Anzahl der Pins.
         */
        public CachedEntropyRuleSolverFactory(final int pinCount) {
            pins = pinCount;
            rule = new CacheRuleFactory().createCachingRule(new EntropyRule(
                    BERECHNER, pins));

        }

        @Override
        public ISolverInfo createSolverInfo() {
            final ISolver newSolver = new RuleSolver(BERECHNER, pins, rule);
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

    /**
     * Solver-Factory für {@link RuleSolver}-Instanzen mit {@link KnuthRule} und
     * und 4 Pins.
     * 
     * @author schusterc
     */
    private static final class KnuthRuleSolverFactory implements ISolverFactory {

        /**
         * Die zu verwendende Regel.
         */
        private final IRule rule = new KnuthRule();

        @Override
        public ISolverInfo createSolverInfo() {
            final ISolver newSolver = new RuleSolver(BERECHNER, 4, rule);
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
     * Solver-Factory für {@link RuleSolver}-Instanzen mit {@link MostPartsRule}
     * und variabler Pin-Anzahl. Die eigentliche Regel wird mit einer
     * {@link org.hitzemann.mms.solver.rule.cache.CacheRule} umhüllt.
     * 
     * @author schusterc
     */
    private static final class CachedMostPartsRuleSolverFactory implements
            ISolverFactory {

        /**
         * Anzahl der Pins.
         */
        private final int pins;

        /**
         * Die zu verwendende Regel.
         */
        private final IRule rule;

        /**
         * Erzeugt eine Factory für die angegebene Anzahl Pins.
         * 
         * @param pinCount
         *            Die Anzahl der Pins.
         */
        public CachedMostPartsRuleSolverFactory(final int pinCount) {
            pins = pinCount;
            rule = new CacheRuleFactory().createCachingRule(new MostPartsRule(
                    BERECHNER, pins));
        }

        @Override
        public ISolverInfo createSolverInfo() {
            final ISolver newSolver = new RuleSolver(BERECHNER, pins, rule);
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
