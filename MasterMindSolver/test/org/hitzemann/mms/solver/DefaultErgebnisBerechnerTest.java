package org.hitzemann.mms.solver;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.junit.Test;

/**
 * Tests für {@link DefaultErgebnisBerechner}.
 * 
 * @author schusterc
 */
public class DefaultErgebnisBerechnerTest {

    private IErgebnisBerechnung berechner = new DefaultErgebnisBerechner();

    /**
     * Test mit Permutationen der geheimen Kombination. Muss immer eine "volle" Antwort liefern.
     */
    @Test
    public void testPermutationenLiefernKorrekteAnzahl() {
        Random rng = new Random();

        int kombinationsGroesse = 20;

        // zufällige geheime Kombination erzeugen
        List<SpielStein> geheimListe = new ArrayList<SpielStein>(kombinationsGroesse);
        SpielStein[] alleSteine = SpielStein.values();
        for (int i = 0; i < kombinationsGroesse; i++) {
            SpielStein zufallsStein = alleSteine[rng.nextInt(alleSteine.length)];
            geheimListe.add(zufallsStein);
        }
        SpielStein[] geheimArray = geheimListe.toArray(new SpielStein[geheimListe.size()]);
        SpielKombination geheim = new SpielKombination(geheimArray);

        for (int i = 0; i < 10000; i++) {
            // zufällige Permutation der geheimen Kombination raten
            List<SpielStein> geratenListe = new LinkedList<SpielStein>(geheimListe);
            Collections.shuffle(geratenListe, rng);
            SpielStein[] geratenArray = geratenListe.toArray(new SpielStein[geratenListe.size()]);
            SpielKombination geraten = new SpielKombination(geratenArray);

            ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);

            // Anzahl weißer und schwarzer Steine im Ergebnis muss Kombinationsgröße sein
            assertEquals(geheimArray.length, ergebnis.getSchwarz() + ergebnis.getWeiss());
        }
    }

    /**
     * Test mit unterschiedlichen Eingabelängen. Muss eine {@link IllegalArgumentException} werfen.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnterschiedlicheGroessen() {
        SpielKombination geheim = new SpielKombination();
        SpielKombination geraten = new SpielKombination(SpielStein.BLUE);

        berechner.berechneErgebnis(geheim, geraten);
    }

    /**
     * Test mit leeren Spielkombinationen. Ergebniskombination muss leer sein.
     */
    @Test
    public void testGroesseNull() {
        SpielKombination geheim = new SpielKombination();
        SpielKombination geraten = new SpielKombination();

        ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);

        assertEquals(0, ergebnis.getSchwarz());
        assertEquals(0, ergebnis.getWeiss());
    }

    /**
     * Test mit definierten Spielkombinationen.
     */
    @Test
    public void testKombination() {
        SpielKombination geheim;
        SpielKombination geraten;
        ErgebnisKombination ergebnis;

        geheim = new SpielKombination(SpielStein.RED, SpielStein.GREEN, SpielStein.RED, SpielStein.BLUE);
        geraten = new SpielKombination(SpielStein.RED, SpielStein.RED, SpielStein.GREEN, SpielStein.RED);

        ergebnis = berechner.berechneErgebnis(geheim, geraten);

        assertEquals(1, ergebnis.getSchwarz());
        assertEquals(2, ergebnis.getWeiss());
    }
}
