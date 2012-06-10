package org.hitzemann.mms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

/**
 * Tests für {@link ErgebnisKombination}.
 * 
 * @author simon
 * 
 */
public class ErgebnisKombinationTest {

    /**
     * Maximale Anzahl der Pins.
     */
    private static final int MAX_PINS = 30;

    /**
     * Anzahl der Stichproben.
     */
    private static final int SAMPLES = 10000;

    /**
     * Testet stichprobenartig, ob die Relation { (a,b) | a.compareTo(b) <= 0 } antisymmetrisch ist.
     */
    @Test
    public final void testCompareToAntisymmetric() {
        final Random rng = new Random(1);

        for (int i = 0; i < SAMPLES; i++) {
            final int aSchwarz = rng.nextInt(MAX_PINS);
            final int aWeiss = rng.nextInt(MAX_PINS - aSchwarz);
            final int bSchwarz = rng.nextInt(MAX_PINS);
            final int bWeiss = rng.nextInt(MAX_PINS - bSchwarz);
            final ErgebnisKombination a = new ErgebnisKombination(aSchwarz, aWeiss);
            final ErgebnisKombination b = new ErgebnisKombination(bSchwarz, bWeiss);
            assertFalse("compareTo not antisymmetric for a = " + a + ", b = " + b,
                    a.compareTo(b) <= 0 && b.compareTo(a) <= 0 && !a.equals(b));
        }
    }

    /**
     * Testet stichprobenartig, ob die Relation { (a,b) | a.compareTo(b) <= 0 } transitiv ist.
     */
    @Test
    public final void testCompareToTransitive() {
        final Random rng = new Random(1);

        for (int i = 0; i < SAMPLES; i++) {
            final int aSchwarz = rng.nextInt(MAX_PINS);
            final int aWeiss = rng.nextInt(MAX_PINS - aSchwarz);
            final int bSchwarz = rng.nextInt(MAX_PINS);
            final int bWeiss = rng.nextInt(MAX_PINS - bSchwarz);
            final int cSchwarz = rng.nextInt(MAX_PINS);
            final int cWeiss = rng.nextInt(MAX_PINS - bSchwarz);
            final ErgebnisKombination a = new ErgebnisKombination(aSchwarz, aWeiss);
            final ErgebnisKombination b = new ErgebnisKombination(bSchwarz, bWeiss);
            final ErgebnisKombination c = new ErgebnisKombination(cSchwarz, cWeiss);
            assertFalse("compareTo not transitive for a = " + a + ", b = " + b, a.compareTo(b) <= 0
                    && b.compareTo(c) <= 0 && !(a.compareTo(c) <= 0));
        }
    }

    /**
     * Testet stichprobenartig, ob die Relation { (a,b) | a.compareTo(b) <= 0 } total ist.
     */
    @Test
    public final void testCompareToTotal() {
        final Random rng = new Random(1);

        for (int i = 0; i < SAMPLES; i++) {
            final int aSchwarz = rng.nextInt(MAX_PINS);
            final int aWeiss = rng.nextInt(MAX_PINS - aSchwarz);
            final int bSchwarz = rng.nextInt(MAX_PINS);
            final int bWeiss = rng.nextInt(MAX_PINS - bSchwarz);
            final ErgebnisKombination a = new ErgebnisKombination(aSchwarz, aWeiss);
            final ErgebnisKombination b = new ErgebnisKombination(bSchwarz, bWeiss);
            assertTrue("compareTo not total for a = " + a + ", b = " + b, a.compareTo(b) <= 0 || b.compareTo(a) <= 0);
        }
    }

    /**
     * Testet stichprobenartig, ob {@link ErgebnisKombination#compareTo(ErgebnisKombination)} konsistent zu
     * {@link ErgebnisKombination#equals(Object)} ist.
     */
    @Test
    public final void testCompareToConsistentWithEquals() {
        final Random rng = new Random(1);

        for (int i = 0; i < SAMPLES; i++) {
            final int aSchwarz = rng.nextInt(MAX_PINS);
            final int aWeiss = rng.nextInt(MAX_PINS - aSchwarz);
            final int bSchwarz = rng.nextInt(MAX_PINS);
            final int bWeiss = rng.nextInt(MAX_PINS - bSchwarz);
            final ErgebnisKombination a = new ErgebnisKombination(aSchwarz, aWeiss);
            final ErgebnisKombination b = new ErgebnisKombination(bSchwarz, bWeiss);

            final int cmp = a.compareTo(b);
            final boolean eq = a.equals(b);

            assertEquals("compareTo inconsistent with equals for a = " + a + ", b = " + b, cmp == 0, eq);
        }
    }

    /**
     * Testet, ob {@link ErgebnisKombination#hashCode()} korrekt für einen festen Wert ist.
     */
    @Test
    public final void testSingleHashCode() {
        final ErgebnisKombination a = new ErgebnisKombination(1, 1);
        assertEquals("hashCode inconsistent a = " + a.hashCode() + ", should be 993.", a.hashCode(), 993);
    }

    /**
     * Testet {@link ErgebnisKombination#equals(Object)} für a.equals(a).
     */
    @Test
    public final void testSameEquals() {
        final Random rng = new Random(1);
        for (int i = 0; i < SAMPLES; i++) {
            final int aSchwarz = rng.nextInt(MAX_PINS);
            final int aWeiss = rng.nextInt(MAX_PINS - aSchwarz);
            final ErgebnisKombination a = new ErgebnisKombination(aSchwarz, aWeiss);
            final boolean eq = a.equals(a);
            assertTrue("equals is false for a.equals(a)", eq);
        }
    }

    /**
     * Testet {@link ErgebnisKombination#equals(Object)} für a.equals(b) wenn b keine ErgebnisKombination ist.
     */
    @Test
    public final void testDifferentClassEquals() {
        final Random rng = new Random(1);
        for (int i = 0; i < SAMPLES; i++) {
            final int aSchwarz = rng.nextInt(MAX_PINS);
            final int aWeiss = rng.nextInt(MAX_PINS - aSchwarz);
            final ErgebnisKombination a = new ErgebnisKombination(aSchwarz, aWeiss);
            final Integer[] b = new Integer[MAX_PINS];
            final boolean eq = a.equals(b);
            assertFalse("equals is true for a.equals(Integer[] b)", eq);
        }
    }

    /**
     * Testet {@link ErgebnisKombination#equals(Object)} für a.equals(b) wenn b null ist.
     */
    @Test
    public final void testUninitializedClassEquals() {
        final Random rng = new Random(1);
        for (int i = 0; i < SAMPLES; i++) {
            final int aSchwarz = rng.nextInt(MAX_PINS);
            final int aWeiss = rng.nextInt(MAX_PINS - aSchwarz);
            final ErgebnisKombination a = new ErgebnisKombination(aSchwarz, aWeiss);
            final ErgebnisKombination b = null;
            final boolean eq = a.equals(b);
            assertFalse("equals is true for a.equals(b)", eq);
        }
    }

    /**
     * Tested {@link ErgebnisKombination#toString()}.
     */
    @Test
    public final void testToString() {
        final Random rng = new Random(1);
        for (int i = 0; i < SAMPLES; i++) {
            final int aSchwarz = rng.nextInt(MAX_PINS);
            final int aWeiss = rng.nextInt(MAX_PINS - aSchwarz);
            final ErgebnisKombination a = new ErgebnisKombination(aSchwarz, aWeiss);
            final String str = "ErgebnisKombination [schwarz=" + aSchwarz + ", weiss=" + aWeiss + "]";
            assertEquals("a.toString is not equal to assumed string: " + a.toString() + " vs " + str, a.toString(), str);
        }
    }
}
