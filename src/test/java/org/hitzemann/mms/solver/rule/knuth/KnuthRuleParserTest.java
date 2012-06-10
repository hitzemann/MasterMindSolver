package org.hitzemann.mms.solver.rule.knuth;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * Tests für {@link KnuthRuleParser}.
 * 
 * @author chschu
 */
public final class KnuthRuleParserTest {

    /**
     * Der Mock für die Factory zur Erzeugung der Regeln.
     */
    private IKnuthRuleFactory ruleFactoryMock = mock(IKnuthRuleFactory.class);

    /**
     * Testet das Parsen einer {@link GuessFirstRule}.
     */
    @Test
    public void testGuessFirstRule() {
        final IRule ruleMock = mock(IRule.class);
        when(ruleFactoryMock.createGuessFirstRule(anyInt(), anyInt())).thenReturn(ruleMock);

        assertSame(ruleMock, parse("912"));

        verify(ruleFactoryMock).createGuessFirstRule(912, 912);
        verifyNoMoreInteractions(ruleFactoryMock, ruleMock);
    }

    /**
     * Testet das Parsen einer {@link GuessFixedSimpleRule} ohne "x".
     */
    @Test
    public void testGuessFixedSimpleRuleStrict() {
        final IRule ruleMock = mock(IRule.class);
        final IRule nextRuleMock = mock(IRule.class);
        when(ruleFactoryMock.createGuessFixedSimpleRule(anyInt(), any(SpielKombination.class), any(IRule.class)))
                .thenReturn(ruleMock);
        when(ruleFactoryMock.createGuessFirstRule(anyInt(), anyInt())).thenReturn(nextRuleMock);

        assertSame(ruleMock, parse("123(4123)"));

        verify(ruleFactoryMock).createGuessFirstRule(0, 1);
        verify(ruleFactoryMock).createGuessFixedSimpleRule(eq(123), eq(new SpielKombination(4, 1, 2, 3)),
                same(nextRuleMock));
        verifyNoMoreInteractions(ruleFactoryMock, ruleMock, nextRuleMock);
    }

    /**
     * Testet das Parsen einer {@link GuessFixedSimpleRule} mit "x".
     */
    @Test
    public void testGuessFixedSimpleRuleLenient() {
        final IRule ruleMock = mock(IRule.class);
        final IRule nextRuleMock = mock(IRule.class);
        when(ruleFactoryMock.createGuessFixedSimpleRule(anyInt(), any(SpielKombination.class), any(IRule.class)))
                .thenReturn(ruleMock);
        when(ruleFactoryMock.createGuessFirstRule(anyInt(), anyInt())).thenReturn(nextRuleMock);

        assertSame(ruleMock, parse("3182(2231x)"));

        verify(ruleFactoryMock).createGuessFirstRule(0, 2);
        verify(ruleFactoryMock).createGuessFixedSimpleRule(eq(3182), eq(new SpielKombination(2, 2, 3, 1)),
                same(nextRuleMock));
        verifyNoMoreInteractions(ruleFactoryMock, ruleMock, nextRuleMock);
    }

    /**
     * Testet das Parsen einer {@link GuessFixedSimpleRule} mit geklammerter Ratekombination.
     */
    @Test
    public void testGuessFixedSimpleRuleBraced() {
        final IRule ruleMock = mock(IRule.class);
        final IRule nextRuleMock = mock(IRule.class);
        when(ruleFactoryMock.createGuessFixedSimpleRule(anyInt(), any(SpielKombination.class), any(IRule.class)))
                .thenReturn(ruleMock);
        when(ruleFactoryMock.createGuessFirstRule(anyInt(), anyInt())).thenReturn(nextRuleMock);

        assertSame(ruleMock, parse("12(4{1}52x)"));

        verify(ruleFactoryMock).createGuessFirstRule(0, 2);
        verify(ruleFactoryMock).createGuessFixedSimpleRule(eq(12), eq(new SpielKombination(4, 1, 5, 2)),
                same(nextRuleMock));
        verifyNoMoreInteractions(ruleFactoryMock, ruleMock, nextRuleMock);
    }

    /**
     * Testet das Parsen einer {@link GuessFixedComplexRule}.
     */
    @Test
    public void testGuessFixedComplexRule() {
        final IRule ruleMock = mock(IRule.class);
        final IRule nextRuleMockA = mock(IRule.class);
        final IRule nextRuleMockB = mock(IRule.class);
        final IRule nextRuleMockC = mock(IRule.class);
        final IRule nextRuleMockD = mock(IRule.class);

        when(ruleFactoryMock.createGuessFixedComplexRule(anyInt(), any(SpielKombination.class), any(IRule[][].class)))
                .thenReturn(ruleMock);
        when(ruleFactoryMock.createGuessFirstRule(anyInt(), anyInt())).thenReturn(nextRuleMockA, nextRuleMockB,
                nextRuleMockC, nextRuleMockD);

        assertSame(ruleMock, parse("14(4415:41;123,12;18)"));

        verify(ruleFactoryMock).createGuessFirstRule(41, 41);
        verify(ruleFactoryMock).createGuessFirstRule(123, 123);
        verify(ruleFactoryMock).createGuessFirstRule(12, 12);
        verify(ruleFactoryMock).createGuessFirstRule(18, 18);

        final ArgumentCaptor<IRule[][]> nextRuleCaptor = ArgumentCaptor.forClass(IRule[][].class);
        verify(ruleFactoryMock).createGuessFixedComplexRule(eq(14), eq(new SpielKombination(4, 4, 1, 5)),
                nextRuleCaptor.capture());
        final IRule[][] actualNextRules = nextRuleCaptor.getValue();
        assertTrue(actualNextRules.length == 3);
        assertTrue(actualNextRules[0].length == 1);
        assertTrue(actualNextRules[1].length == 2);
        assertTrue(actualNextRules[2].length == 1);
        assertSame(nextRuleMockA, actualNextRules[0][0]);
        assertSame(nextRuleMockB, actualNextRules[1][0]);
        assertSame(nextRuleMockC, actualNextRules[1][1]);
        assertSame(nextRuleMockD, actualNextRules[2][0]);

        verifyNoMoreInteractions(ruleFactoryMock, ruleMock, nextRuleMockA, nextRuleMockB, nextRuleMockC, nextRuleMockD);
    }

    /**
     * Startet den Parser für die übergebene Eingabe.
     * 
     * @param input
     *            Die Eingabe.
     * @return Die geparste {@link IRule}.
     */
    private IRule parse(final String input) {
        try {
            final CharStream stream = new ANTLRStringStream(input);
            final Lexer lexer = new KnuthRuleLexer(stream);
            final TokenStream tokenStream = new CommonTokenStream(lexer);
            final KnuthRuleParser parser = new KnuthRuleParser(tokenStream);
            parser.setKnuthRuleFactory(ruleFactoryMock);
            return parser.start();
        } catch (RecognitionException e) {
            throw new IllegalStateException(e);
        }
    }
}
