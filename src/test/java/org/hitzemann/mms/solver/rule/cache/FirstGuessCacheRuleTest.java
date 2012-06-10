package org.hitzemann.mms.solver.rule.cache;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link FirstGuessCacheRule}.
 * 
 * @author chschu
 */
@RunWith(MockitoJUnitRunner.class)
public final class FirstGuessCacheRuleTest {

    /**
     * Mock für die zu übergebende {@link IRule}.
     */
    @Mock
    private IRule ruleMock;

    /**
     * Test für {@link IRule#getGuess(List<SpielKombination>)} bei einem "Cache Miss".
     */
    @Test
    public void testGetGuessCacheMiss() {
        final FirstGuessCacheRule underTest = new FirstGuessCacheRule(ruleMock);
        final List<SpielKombination> candidates = Collections.emptyList();
        final SpielKombination guess = new SpielKombination(1);

        when(ruleMock.getGuess(anyListOf(SpielKombination.class))).thenReturn(guess);

        assertSame(guess, underTest.getGuess(candidates));

        verify(ruleMock).getGuess(same(candidates));
        verifyNoMoreInteractions(ruleMock);
    }

    /**
     * Test für {@link IRule#getGuess(List<SpielKombination>)} bei einem "Cache Hit".
     */
    @Test
    public void testGetGuessCacheHit() {
        final SpielKombination guess = new SpielKombination(1);

        final FirstGuessCacheRule underTest = new FirstGuessCacheRule(ruleMock, guess);
        final List<SpielKombination> candidates = Collections.emptyList();

        assertSame(guess, underTest.getGuess(candidates));

        verifyNoMoreInteractions(ruleMock);
    }

    /**
     * Test für {@link IRule#getGuess(List<SpielKombination>)} bei einem "Cache Hit" im zweiten Anlauf.
     */
    @Test
    public void testGetGuessCacheHitSecondCall() {
        final FirstGuessCacheRule underTest = new FirstGuessCacheRule(ruleMock);
        final List<SpielKombination> candidates = Collections.emptyList();
        final SpielKombination guess = new SpielKombination(1);

        when(ruleMock.getGuess(anyListOf(SpielKombination.class))).thenReturn(guess);

        // zweimal aufrufen (ruleMock wird nur einmal aufgerufen)
        assertSame(guess, underTest.getGuess(candidates));
        assertSame(guess, underTest.getGuess(candidates));

        verify(ruleMock).getGuess(same(candidates));
        verifyNoMoreInteractions(ruleMock);
    }

    /**
     * Test für {@link IRule#getRuleForResponse(ErgebnisKombination)}.
     */
    @Test
    public void testGetRuleForResponse() {
        final FirstGuessCacheRule underTest = new FirstGuessCacheRule(ruleMock);
        final IRule nextRuleMock = mock(IRule.class);
        final ErgebnisKombination response = new ErgebnisKombination(0, 0);

        when(ruleMock.getRuleForResponse(any(ErgebnisKombination.class))).thenReturn(nextRuleMock);

        assertSame(nextRuleMock, underTest.getRuleForResponse(response));

        verify(ruleMock).getRuleForResponse(same(response));
        verifyNoMoreInteractions(ruleMock, nextRuleMock);
    }
}
