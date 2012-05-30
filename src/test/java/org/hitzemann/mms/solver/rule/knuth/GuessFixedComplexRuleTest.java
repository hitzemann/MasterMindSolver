package org.hitzemann.mms.solver.rule.knuth;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;
import org.junit.Test;

/**
 * Tests für {@link GuessFixedComplexRule}.
 * 
 * @author schusterc
 */
public final class GuessFixedComplexRuleTest {

	/**
	 * Logger für Testausgaben.
	 */
	private static final Logger LOGGER = Logger.getLogger(GuessFirstRule.class
			.getName());

	/**
	 * Eine zu ratende {@link SpielKombination}.
	 */
	private final SpielKombination guess = new SpielKombination(1, 2);

	/**
	 * Ein Folge-Regel-Array.
	 */
	private final IRule[][] nextRules = new IRule[][] {
			{ mock(IRule.class), mock(IRule.class), mock(IRule.class), },
			{ mock(IRule.class), mock(IRule.class), }, { mock(IRule.class), }, };

	/**
	 * Testet den Konstruktor mit <code>null</code> als zu ratende
	 * {@link SpielKombination}.
	 */
	@Test
	public void testNullGuess() {
		try {
			new GuessFixedComplexRule(123, null, nextRules);
			fail("expected Exception not thrown");
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.FINEST, "expected exception", e);
		}
	}

	/**
	 * Testet den Konstruktor mit <code>null</code> als Folge-Regel-Array.
	 */
	@Test
	public void testNullNextRules() {
		try {
			new GuessFixedComplexRule(123, guess, null);
			fail("expected Exception not thrown");
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.FINEST, "expected exception", e);
		}
	}

	/**
	 * Testet den Konstruktor mit <code>null</code> als Eintrag im
	 * Folge-Regel-Array.
	 */
	@Test
	public void testNullNextRulesEntry() {
		try {
			new GuessFixedComplexRule(123, guess, new IRule[3][]);
			fail("expected Exception not thrown");
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.FINEST, "expected exception", e);
		}
	}

	/**
	 * Testet den Konstruktor mit nicht zur Länge der zu ratenden
	 * {@link SpielKombination} passendem Folge-Regel-Array.
	 */
	@Test
	public void testNonMatchingLength() {
		try {
			new GuessFixedComplexRule(123, new SpielKombination(1, 2, 3),
					nextRules);
			fail("expected Exception not thrown");
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.FINEST, "expected exception", e);
		}
	}

	/**
	 * Testet den Konstruktor mit nicht dreieckigem Folge-Regel-Array.
	 */
	@Test
	public void testNonTriangularNextRules() {
		try {
			new GuessFixedComplexRule(123, guess, new IRule[3][3]);
			fail("expected Exception not thrown");
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.FINEST, "expected exception", e);
		}
	}

	/**
	 * Testet den regulären Aufruf von
	 * {@link IRule#getGuess(java.util.Collection)}.
	 */
	@Test
	public void testGetGuess() {

		final IRule underTest = new GuessFixedComplexRule(123, guess, nextRules);

		@SuppressWarnings("unchecked")
		final List<SpielKombination> candidatesMock = mock(List.class);

		when(candidatesMock.size()).thenReturn(123);

		assertSame(guess, underTest.getGuess(candidatesMock));

		verify(candidatesMock).size();
		verifyNoMoreInteractions(candidatesMock);
	}

	/**
	 * Testet den Aufruf von {@link IRule#getGuess(java.util.Collection)} mit
	 * einer falschen Anzahl an Kandidaten.
	 */
	@Test
	public void testGetGuessInvalidCandidateCount() {
		final IRule underTest = new GuessFixedComplexRule(15, guess, nextRules);

		@SuppressWarnings("unchecked")
		final List<SpielKombination> candidatesMock = mock(List.class);

		when(candidatesMock.size()).thenReturn(14);

		try {
			underTest.getGuess(candidatesMock);
			fail("expected Exception not thrown");
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.FINEST, "expected exception", e);
		}

		verify(candidatesMock).size();
		verifyNoMoreInteractions(candidatesMock);
	}

	/**
	 * Testet den Aufruf von
	 * {@link IRule#getRuleForResponse(ErgebnisKombination)}.
	 */
	@Test
	public void testGetNextRule() {
		final IRule underTest = new GuessFixedComplexRule(1, guess, nextRules);

		assertSame(nextRules[0][0],
				underTest.getRuleForResponse(new ErgebnisKombination(0, 2)));
		assertSame(nextRules[0][1],
				underTest.getRuleForResponse(new ErgebnisKombination(0, 1)));
		assertSame(nextRules[0][2],
				underTest.getRuleForResponse(new ErgebnisKombination(0, 0)));

		assertSame(nextRules[1][0],
				underTest.getRuleForResponse(new ErgebnisKombination(1, 1)));
		assertSame(nextRules[1][1],
				underTest.getRuleForResponse(new ErgebnisKombination(1, 0)));

		assertSame(nextRules[2][0],
				underTest.getRuleForResponse(new ErgebnisKombination(2, 0)));
	}
}
