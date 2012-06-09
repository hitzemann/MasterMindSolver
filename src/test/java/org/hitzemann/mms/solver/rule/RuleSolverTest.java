package org.hitzemann.mms.solver.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.ISpielKombinationFactory;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.IErgebnisBerechnung;
import org.hitzemann.mms.solver.ISolver;
import org.junit.Test;

/**
 * Tests für {@link RuleSolver}.
 * 
 * @author chschu
 */
public final class RuleSolverTest {

	/**
	 * Test für die Interaktion des Konstruktors mit
	 * {@link ISpielKombinationFactory}.
	 */
	@Test
	public void testConstructor() {
		final IErgebnisBerechnung calculatorMock = mock(IErgebnisBerechnung.class);
		final ISpielKombinationFactory factoryMock = mock(ISpielKombinationFactory.class);
		final IRule ruleMock = mock(IRule.class);

		new RuleSolver(calculatorMock, factoryMock, 143, ruleMock);

		verify(factoryMock).erzeugeAlle(143);
		verifyNoMoreInteractions(calculatorMock, factoryMock, ruleMock);
	}

	/**
	 * Test für {@link ISolver#getNeuerZug()}.
	 */
	@Test
	public void testGetNeuerZug() {
		final IErgebnisBerechnung calculatorMock = mock(IErgebnisBerechnung.class);
		final ISpielKombinationFactory factoryMock = mock(ISpielKombinationFactory.class);
		final IRule ruleMock = mock(IRule.class);
		final SpielKombination guess = new SpielKombination(new int[0]);
		@SuppressWarnings("unchecked")
		final List<SpielKombination> candidatesMock = mock(List.class);

		when(factoryMock.erzeugeAlle(anyInt())).thenReturn(candidatesMock);

		final ISolver underTest = new RuleSolver(calculatorMock, factoryMock,
				3, ruleMock);

		when(ruleMock.getGuess(anyListOf(SpielKombination.class))).thenReturn(
				guess);

		assertSame(guess, underTest.getNeuerZug());

		verify(ruleMock).getGuess(eq(candidatesMock));
		verify(factoryMock).erzeugeAlle(3);
		verifyNoMoreInteractions(calculatorMock, factoryMock, ruleMock,
				candidatesMock);
	}

	/**
	 * Test für {@link ISolver#setLetzterZug()}.
	 */
	@Test
	public void testSetLetzterZug() {
		final IErgebnisBerechnung calculatorMock = mock(IErgebnisBerechnung.class);
		final ISpielKombinationFactory factoryMock = mock(ISpielKombinationFactory.class);
		final IRule ruleMock = mock(IRule.class);
		final SpielKombination candidate1 = new SpielKombination(new int[0]);
		final SpielKombination candidate2 = new SpielKombination(new int[0]);
		final SpielKombination candidate3 = new SpielKombination(new int[0]);
		final SpielKombination candidate4 = new SpielKombination(new int[0]);
		final SpielKombination guess = new SpielKombination(new int[0]);
		final List<SpielKombination> candidates = new LinkedList<SpielKombination>(
				Arrays.asList(candidate1, candidate2, candidate3, candidate4));
		final ErgebnisKombination response = new ErgebnisKombination(0, 0);

		when(factoryMock.erzeugeAlle(anyInt())).thenReturn(candidates);

		final ISolver underTest = new RuleSolver(calculatorMock, factoryMock,
				5, ruleMock);

		final ErgebnisKombination otherResponse = new ErgebnisKombination(1, 0);
		when(
				calculatorMock.berechneErgebnis(any(SpielKombination.class),
						any(SpielKombination.class))).thenReturn(response,
				response, otherResponse);

		underTest.setLetzterZug(guess, response);

		assertEquals(2, candidates.size());

		verify(calculatorMock).berechneErgebnis(same(candidate1), same(guess));
		verify(calculatorMock).berechneErgebnis(same(candidate2), same(guess));
		verify(calculatorMock).berechneErgebnis(same(candidate3), same(guess));
		verify(calculatorMock).berechneErgebnis(same(candidate4), same(guess));
		verify(factoryMock).erzeugeAlle(5);
		verify(ruleMock).getRuleForResponse(same(response));
		verifyNoMoreInteractions(calculatorMock, factoryMock, ruleMock);
	}
}
