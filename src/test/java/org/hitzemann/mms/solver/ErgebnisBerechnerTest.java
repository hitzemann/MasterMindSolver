package org.hitzemann.mms.solver;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests für {@link DefaultErgebnisBerechner} und {@link LinearerErgebnisBerechner}.
 * 
 * @author schusterc
 */
@RunWith(Parameterized.class)
public final class ErgebnisBerechnerTest {

    /**
     * Die aktuell zu testende Implementierung von {@link IErgebnisBerechnung}.
     */
    private final IErgebnisBerechnung berechner;

    /**
     * Erzeugt einen parametrisierten Testdurchlauf mit der angegebenen Implementierung von {@link IErgebnisBerechnung}.
     * 
     * @param aktuellerBerechner
     *            Die zu testende Implementierung von {@link IErgebnisBerechnung}.
     */
    public ErgebnisBerechnerTest(final IErgebnisBerechnung aktuellerBerechner) {
        berechner = aktuellerBerechner;
    }

    /**
     * Erzeugt die Parameter für den Test, die dann vom {@link Parameterized}-Runner als Argument an den Konstruktor
     * übergeben werden.
     * 
     * @return Liste mit einem {@link Object}-Array pro parametrisiertem Testlauf.
     */
    @Parameters
    public static List<Object[]> erzeugeParameter() {
        return Arrays.asList(new Object[] { new DefaultErgebnisBerechner() },
                new Object[] { new LinearerErgebnisBerechner() });
    }

    /**
     * Test mit Permutationen der geheimen Kombination. Muss immer eine "volle" Antwort liefern.
     */
    @Test
    public void testPermutationenLiefernKorrekteAnzahl() {
        // Deterministischer Zufallszahlengenerator
        final Random rng = new Random(1);

        final int kombinationsGroesse = 100;

        // zufällige geheime Kombination erzeugen
        final List<SpielStein> geheimListe = new ArrayList<SpielStein>(kombinationsGroesse);
        final SpielStein[] alleSteine = SpielStein.values();
        for (int i = 0; i < kombinationsGroesse; i++) {
            final SpielStein zufallsStein = alleSteine[rng.nextInt(alleSteine.length)];
            geheimListe.add(zufallsStein);
        }
        final SpielStein[] geheimArray = geheimListe.toArray(new SpielStein[geheimListe.size()]);
        final SpielKombination geheim = new SpielKombination(geheimArray);

        for (int i = 0; i < 1000; i++) {
            // zufällige Permutation der geheimen Kombination raten
            final List<SpielStein> geratenListe = new LinkedList<SpielStein>(geheimListe);
            Collections.shuffle(geratenListe, rng);
            final SpielStein[] geratenArray = geratenListe.toArray(new SpielStein[geratenListe.size()]);
            final SpielKombination geraten = new SpielKombination(geratenArray);

            final ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);

            // Anzahl weißer und schwarzer Steine im Ergebnis muss Kombinationsgröße sein
            assertEquals(geheimArray.length, ergebnis.getSchwarz() + ergebnis.getWeiss());
        }
    }

    /**
     * Test der Symmetrie. Vertauschung von "geraten" und "geheim" muss das gleiche Ergebnis liefern.
     */
    @Test
    public void testSymmetrie() {
        // Deterministischer Zufallszahlengenerator
        final Random rng = new Random(1);

        final SpielStein[] alleSteine = SpielStein.values();

        for (int i = 0; i < 1000; i++) {
            final int kombinationsGroesse = rng.nextInt(100);

            // 2 zufällige Kombinationen erzeugen
            final SpielStein[] kombi1Array = new SpielStein[kombinationsGroesse];
            final SpielStein[] kombi2Array = new SpielStein[kombinationsGroesse];
            for (int j = 0; j < kombinationsGroesse; j++) {
                kombi1Array[j] = alleSteine[rng.nextInt(alleSteine.length)];
                kombi2Array[j] = alleSteine[rng.nextInt(alleSteine.length)];
            }
            final SpielKombination kombi1 = new SpielKombination(kombi1Array);
            final SpielKombination kombi2 = new SpielKombination(kombi2Array);

            // mit beiden Argumentreihenfolgen aufrufen
            final ErgebnisKombination ergebnis1 = berechner.berechneErgebnis(kombi1, kombi2);
            final ErgebnisKombination ergebnis2 = berechner.berechneErgebnis(kombi2, kombi1);

            // Ergebnisse müssen gleich sein
            assertEquals(ergebnis1.getSchwarz(), ergebnis2.getSchwarz());
            assertEquals(ergebnis1.getWeiss(), ergebnis2.getWeiss());
        }
    }

    /**
     * Test mit einfarbigen geratenen Kombination. Darf nie "weiße" Ergebnisse liefern, und die Summe der "schwarzen"
     * Ergebnisse über alle geratenen Farben muss die Kombinationsgröße sein.
     */
    @Test
    public void testEinfarbigesRaten() {
        // Deterministischer Zufallszahlengenerator
        final Random rng = new Random(1);

        final int kombinationsGroesse = 20;

        // zufällige geheime Kombination erzeugen
        final SpielStein[] geheimArray = new SpielStein[kombinationsGroesse];
        final SpielStein[] alleSteine = SpielStein.values();
        for (int i = 0; i < kombinationsGroesse; i++) {
            final SpielStein zufallsStein = alleSteine[rng.nextInt(alleSteine.length)];
            geheimArray[i] = zufallsStein;
        }
        final SpielKombination geheim = new SpielKombination(geheimArray);

        // Summe der Anzahl schwarzer Ergebnissteine über alle Farben
        int gesamtSchwarz = 0;

        // alle Farben nacheinander raten
        for (final SpielStein stein : alleSteine) {
            // einfarbige geratene Kombination erzeugen
            final SpielStein[] geratenArray = new SpielStein[kombinationsGroesse];
            for (int i = 0; i < kombinationsGroesse; i++) {
                geratenArray[i] = stein;
            }
            final SpielKombination geraten = new SpielKombination(geratenArray);

            final ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);

            // Anzahl weißer Steine im Ergebnis muss immer 0 sein
            assertEquals(0, ergebnis.getWeiss());

            // Anzahl schwarze Steine aufsummieren
            gesamtSchwarz += ergebnis.getSchwarz();
        }

        // Gesamtzahl schwarzer Steine muss Kombinationsgröße sein
        assertEquals(kombinationsGroesse, gesamtSchwarz);
    }

    /**
     * Test mit <code>null</code> als geheime Kombination.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullGeheim() {
        berechner.berechneErgebnis(new SpielKombination(), null);
    }

    /**
     * Test mit <code>null</code> als geratene Kombination.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullGeraten() {
        berechner.berechneErgebnis(null, new SpielKombination());
    }

    /**
     * Test mit unterschiedlichen Eingabelängen. Muss eine {@link IllegalArgumentException} werfen.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnterschiedlicheGroessen() {
        final SpielKombination geheim = new SpielKombination();
        final SpielKombination geraten = new SpielKombination(SpielStein.BLAU);

        berechner.berechneErgebnis(geheim, geraten);
    }

    /**
     * Test mit leeren Spielkombinationen. Ergebniskombination muss leer sein.
     */
    @Test
    public void testGroesseNull() {
        final SpielKombination geheim = new SpielKombination();
        final SpielKombination geraten = new SpielKombination();

        final ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);

        assertEquals(0, ergebnis.getSchwarz());
        assertEquals(0, ergebnis.getWeiss());
    }

    /**
     * Test mit voll korrekter Spielkombination.
     */
    @Test
    public void testVollSchwarz() {
        final SpielKombination geheim = new SpielKombination(SpielStein.ROT, SpielStein.GRUEN, SpielStein.ROT,
                SpielStein.BLAU);
        final SpielKombination geraten = new SpielKombination(SpielStein.ROT, SpielStein.GRUEN, SpielStein.ROT,
                SpielStein.BLAU);

        final ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);

        assertEquals(4, ergebnis.getSchwarz());
        assertEquals(0, ergebnis.getWeiss());
    }

    /**
     * Test mit voll vertauschter Spielkombination.
     */
    @Test
    public void testVollWeiss() {
        final SpielKombination geheim = new SpielKombination(SpielStein.ROT, SpielStein.BLAU, SpielStein.ROT,
                SpielStein.GELB);
        final SpielKombination geraten = new SpielKombination(SpielStein.GELB, SpielStein.ROT, SpielStein.BLAU,
                SpielStein.ROT);

        final ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);

        assertEquals(0, ergebnis.getSchwarz());
        assertEquals(4, ergebnis.getWeiss());
    }

    /**
     * Test mit leerem Ergebnis.
     */
    @Test
    public void testLeeresErgebnis() {
        final SpielKombination geheim = new SpielKombination(SpielStein.ROT, SpielStein.ROT, SpielStein.GELB,
                SpielStein.GELB);
        final SpielKombination geraten = new SpielKombination(SpielStein.GRUEN, SpielStein.BLAU, SpielStein.BLAU,
                SpielStein.GRUEN);

        final ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);

        assertEquals(0, ergebnis.getSchwarz());
        assertEquals(0, ergebnis.getWeiss());
    }
}
