package org.hitzemann.mms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests für {@link DefaultSpielKombinationFactory}.
 * 
 * @author chschu
 */
@RunWith(Enclosed.class)
public final class DefaultSpielKombinationFactoryTest {

    /**
     * Allgemeine Tests.
     * 
     * @author chschu
     */
    public static final class Allgemein {

        /**
         * Die zu testende Instanz.
         */
        private ISpielKombinationFactory underTest = new DefaultSpielKombinationFactory();

        /**
         * Test für die bei negativem Parameter zu werfende {@link IllegalArgumentException}.
         */
        @Test(expected = IllegalArgumentException.class)
        public void testNegativeGroesse() {
            underTest.erzeugeAlle(-1);
        }
    }

    /**
     * Tests mit parametrisierter Pin-Anzahl.
     * 
     * @author chschu
     */
    @RunWith(Parameterized.class)
    public static final class VariablePins {

        /**
         * Die maximale Pin-Anzahl.
         */
        private static final int MAX_PINS = 7;

        /**
         * Die zu testende Instanz.
         */
        private ISpielKombinationFactory underTest = new DefaultSpielKombinationFactory();

        /**
         * Die aktuelle Pin-Anzahl.
         */
        private final int pins;

        /**
         * Konstruktor für den Test mit gegebener Pin-Anzahl.
         * 
         * @param thePins
         *            Die zu testende Pin-Anzahl.
         */
        public VariablePins(final int thePins) {
            pins = thePins;
        }

        /**
         * Methode zur Bereitstellung der Konstruktor-Parameter.
         * 
         * @return Liste von Arrays, die jeweils als Konstruktor-Parameter verwendet werden.
         */
        @Parameters
        public static List<Object[]> getParameters() {
            final List<Object[]> params = new LinkedList<Object[]>();
            for (int i = 0; i <= MAX_PINS; i++) {
                params.add(new Object[] { i });
            }
            return params;
        }

        /**
         * Test dass die richtige Anzahl Kombinationen geliefert wird.
         */
        @Test
        public void testAnzahl() {
            final int erwartet = BigInteger.valueOf(SpielStein.values().length).pow(pins).intValue();
            final List<SpielKombination> alle = underTest.erzeugeAlle(pins);
            assertEquals(erwartet, alle.size());
        }

        /**
         * Test dass die Kombinationen die richtige Länge haben.
         */
        @Test
        public void testLaenge() {
            final List<SpielKombination> alle = underTest.erzeugeAlle(pins);
            for (SpielKombination cur : alle) {
                assertEquals(cur.getSpielSteineCount(), pins);
            }
        }

        /**
         * Test dass die Rückgabe aufsteigend sortiert ist.
         */
        @Test
        public void testAufsteigend() {
            final List<SpielKombination> alle = underTest.erzeugeAlle(pins);
            SpielKombination prev = null;
            for (SpielKombination cur : alle) {
                if (prev != null) {
                    assertTrue(cur.compareTo(prev) > 0);
                }
                prev = cur;
            }
        }
    }
}
