package org.hitzemann.mms.solver.rule;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.IErgebnisBerechnung;
import org.hitzemann.mms.solver.ISolver;
import org.junit.Test;

/**
 * Tests für {@link RuleSolver}.
 * 
 * @author schusterc
 */
public final class RuleSolverTest {

	/**
	 * Test für {@link ISolver#getNeuerZug()}.
	 */
	@Test
	public void testGetNeuerZug() {
		final IErgebnisBerechnung calculatorMock = mock(IErgebnisBerechnung.class);
		final IRule ruleMock = mock(IRule.class);
		final SpielKombination guess = new SpielKombination(new int[0]);
		final ISolver underTest = new RuleSolver(calculatorMock, 3, ruleMock);

		when(ruleMock.getGuess(anySetOf(SpielKombination.class))).thenReturn(
				guess);

		assertSame(guess, underTest.getNeuerZug());

		verify(ruleMock).getGuess(anySetOf(SpielKombination.class));
		verifyNoMoreInteractions(calculatorMock, ruleMock);
	}

	/**
	 * Test für {@link ISolver#setLetzterZug()}.
	 */
	@Test
	public void testSetLetzterZug() {
		final IErgebnisBerechnung calculatorMock = mock(IErgebnisBerechnung.class);
		final IRule ruleMock = mock(IRule.class);
		final SpielKombination guess = new SpielKombination(new int[0]);
		final ErgebnisKombination response = new ErgebnisKombination(0, 0);
		final ISolver underTest = new RuleSolver(calculatorMock, 3, ruleMock);

		final ErgebnisKombination otherResponse = new ErgebnisKombination(1, 0);
		when(
				calculatorMock.berechneErgebnis(any(SpielKombination.class),
						any(SpielKombination.class))).thenReturn(response,
				otherResponse);

		underTest.setLetzterZug(guess, response);

		verify(calculatorMock, times(6 * 6 * 6)).berechneErgebnis(
				any(SpielKombination.class), same(guess));
		verify(ruleMock).getRuleForResponse(same(response));
		verifyNoMoreInteractions(calculatorMock, ruleMock);
	}
}
