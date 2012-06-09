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
 * Tests für {@link GuessFixedSimpleRule}.
 * 
 * @author chschu
 */
public final class GuessFixedSimpleRuleTest {

	/**
	 * Logger für Testausgaben.
	 */
	private static final Logger LOGGER = Logger.getLogger(GuessFirstRule.class
			.getName());

	/**
	 * Eine zu ratende {@link SpielKombination}.
	 */
	private final SpielKombination guess = new SpielKombination(new int[0]);

	/**
	 * Der Mock für eine Folge-Regel.
	 */
	private final IRule nextRuleMock = mock(IRule.class);

	/**
	 * Testet den Aufruf von
	 * {@link GuessFixedSimpleRule#GuessFixedSimpleRule(int, SpielKombination, IRule)}
	 * mit <code>null</code> als zu ratende {@link SpielKombination}.
	 */
	@Test
	public void testNullGuess() {
		try {
			new GuessFixedSimpleRule(123, null, nextRuleMock);
			fail("expected Exception not thrown");
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.FINEST, "expected exception", e);
		}

		verifyNoMoreInteractions(nextRuleMock);
	}

	/**
	 * Testet den Aufruf von
	 * {@link GuessFixedSimpleRule#GuessFixedSimpleRule(int, SpielKombination, IRule)}
	 * mit <code>null</code> als Folge-Regel.
	 */
	@Test
	public void testNullNextRule() {
		try {
			new GuessFixedSimpleRule(123, guess, null);
			fail("expected Exception not thrown");
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.FINEST, "expected exception", e);
		}
	}

	/**
	 * Testet den regulären Aufruf von
	 * {@link IRule#getGuess(List<SpielKombination>)}.
	 */
	@Test
	public void testGetGuess() {
		final IRule underTest = new GuessFixedSimpleRule(123, guess,
				nextRuleMock);

		@SuppressWarnings("unchecked")
		final List<SpielKombination> candidatesMock = mock(List.class);

		when(candidatesMock.size()).thenReturn(123);

		assertSame(guess, underTest.getGuess(candidatesMock));

		verify(candidatesMock).size();
		verifyNoMoreInteractions(nextRuleMock, candidatesMock);
	}

	/**
	 * Testet den Aufruf von {@link IRule#getGuess(List<SpielKombination>)} mit
	 * einer falschen Anzahl an Kandidaten.
	 */
	@Test
	public void testGetGuessInvalidCandidateCount() {
		final IRule underTest = new GuessFixedSimpleRule(15, guess,
				nextRuleMock);

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
		verifyNoMoreInteractions(nextRuleMock, candidatesMock);
	}

	/**
	 * Testet den Aufruf von
	 * {@link IRule#getRuleForResponse(ErgebnisKombination)}.
	 */
	@Test
	public void testGetNextRule() {
		final IRule underTest = new GuessFixedSimpleRule(1, guess, nextRuleMock);

		final ErgebnisKombination responseMock = mock(ErgebnisKombination.class);

		assertSame(nextRuleMock, underTest.getRuleForResponse(responseMock));

		verifyNoMoreInteractions(nextRuleMock, responseMock);
	}
}
