package org.hitzemann.mms.solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;

/**
 * Testklasse für einen {@link ISolver}. Erzeugt alle möglichen Kombinationen und lässt eine {@link ISolver}-Instanz
 * gegen eine {@link IErgebnisBerechnung}-Instanz antreten. Die Verarbeitung erfolgt parallelisiert. Nachdem alle
 * Kombinationen getestet wurden, wird die durchschnittliche Anzahl Rateversuche ausgegeben.
 * 
 * @author schusterc
 */
public final class AlgorithmusTester {

    /**
     * Anzahl der Pins (Länge der Kombinationen).
     */
    private static final int PINS = 4;

    /**
     * Anzahl paralleler Threads.
     */
    private static final int THREADS = 4;

    /**
     * Privater Default-Konstruktor - Klasse wird nur von {@link #main(String[])} instanziiert.
     */
    private AlgorithmusTester() {
    }

    /**
     * Factory-Methode für den Berechner.
     * 
     * @return Eine neue Instanz von {@link IErgebnisBerechnung}.
     */
    private IErgebnisBerechnung erzeugeBerechner() {
        return new LinearerErgebnisBerechner();
    }

    /**
     * Factory-Methode für den Solver.
     * 
     * @param berechner
     *            Der zu verwendende Berechner.
     * 
     * @return Eine neue Instanz von {@link ISolver}.
     */
    private ISolver erzeugeSolver(final IErgebnisBerechnung berechner) {
        return new EntropieSolver(berechner, PINS);
    }

    /**
     * Startet den Tester.
     * 
     * @throws InterruptedException
     *             Der Haupt-Thread wurde (beim Warten auf Beendigung der Tasks) unterbrochen.
     * @throws ExecutionException
     *             In einem der Tasks ist eine Exception aufgetreten.
     */
    private void start() throws InterruptedException, ExecutionException {
        final IErgebnisBerechnung berechner = erzeugeBerechner();
        final ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        final AtomicInteger versucheGesamt = new AtomicInteger();
        final Set<SpielKombination> alleKombinationen = erzeugeAlleKombinationen(PINS);
        final List<Future<?>> futures = new ArrayList<Future<?>>(alleKombinationen.size());
        for (final SpielKombination geheim : alleKombinationen) {
            futures.add(executor.submit(new Runnable() {
                @Override
                public void run() {
                    // Spiel starten
                    final ISolver spielSolver = erzeugeSolver(berechner);
                    int versuche = 0;
                    while (true) {
                        final SpielKombination geraten = spielSolver.getNeuerZug();
                        final ErgebnisKombination ergebnis = berechner.berechneErgebnis(geheim, geraten);
                        versuche++;
                        if (ergebnis.getSchwarz() == PINS) {
                            // gewonnen
                            break;
                        }
                        spielSolver.setLetzterZug(geraten, ergebnis);
                    }

                    // Kombination und Rateversuche ausgeben
                    synchronized (System.out) {
                        System.out.print(versuche + " Rateversuche (geheime Kombination");
                        for (int i = 0; i < PINS; i++) {
                            System.out.print(" " + geheim.getSpielStein(i));
                        }
                        System.out.println(")");
                    }

                    // Rateversuche aufsummieren
                    versucheGesamt.addAndGet(versuche);
                }
            }));
        }

        // Executor stoppen
        executor.shutdown();

        // auf Ende aller Tasks warten
        for (Future<?> future : futures) {
            future.get();
        }

        // Durchschnitt ausgeben
        System.out.println(1.0 * versucheGesamt.get() / alleKombinationen.size() + " Rateversuche Durchschnitt");
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
        final Set<SpielKombination> ergebnisSet = new HashSet<SpielKombination>();
        erzeugeAlleKombinationenRekursiv(new SpielStein[groesse], 0, ergebnisSet);
        return ergebnisSet;
    }

    /**
     * Das Hauptprogramm.
     * 
     * @param args
     *            Die Kommandozeilenargumente - nicht verwendet.
     * @throws InterruptedException
     *             Der Haupt-Thread wurde unterbrochen.
     * @throws ExecutionException
     *             In einem der Tasks ist eine Exception aufgetreten.
     */
    public static void main(final String[] args) throws InterruptedException, ExecutionException {
        final AlgorithmusTester instanz = new AlgorithmusTester();
        instanz.start();
    }
}
