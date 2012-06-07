package org.hitzemann.mms.solver.rule.cache;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests für {@link CacheRule}.
 * 
 * @author schusterc
 */
@RunWith(MockitoJUnitRunner.class)
public final class CacheRuleTest {

	/**
	 * Mock für die zu übergebende {@link IRule}.
	 */
	@Mock
	private IRule ruleMock;

	/**
	 * Mock für den zu übergebenden Cache.
	 */
	@Mock
	private Map<Set<SpielKombination>, SpielKombination> cacheMock;

	/**
	 * Mock für die zu übergebende {@link ICacheRuleFactory}.
	 */
	@Mock
	private ICacheRuleFactory ruleFactoryMock;

	/**
	 * Test für {@link IRule#getGuess(java.util.Collection)} bei einem
	 * "Cache Miss".
	 */
	@Test
	public void testGetGuessCacheMiss() {
		final CacheRule underTest = new CacheRule(ruleMock, cacheMock,
				ruleFactoryMock);
		final List<SpielKombination> candidates = Collections.emptyList();
		final SpielKombination guess = new SpielKombination(1);

		when(cacheMock.get(any())).thenReturn(null);
		when(ruleMock.getGuess(anyCollectionOf(SpielKombination.class)))
				.thenReturn(guess);

		assertSame(guess, underTest.getGuess(candidates));

		final Set<SpielKombination> key = new HashSet<SpielKombination>(
				candidates);
		verify(cacheMock).get(eq(key));
		verify(ruleMock).getGuess(same(candidates));
		verify(cacheMock).put(eq(key), same(guess));
		verifyNoMoreInteractions(cacheMock, ruleMock, ruleFactoryMock);
	}

	/**
	 * Test für {@link IRule#getGuess(java.util.Collection)} bei einem
	 * "Cache Hit".
	 */
	@Test
	public void testGetGuessCacheHit() {
		final CacheRule underTest = new CacheRule(ruleMock, cacheMock,
				ruleFactoryMock);
		final List<SpielKombination> candidates = Collections.emptyList();
		final SpielKombination guess = new SpielKombination(1);

		when(cacheMock.get(any())).thenReturn(guess);

		assertSame(guess, underTest.getGuess(candidates));

		final Set<SpielKombination> key = new HashSet<SpielKombination>(
				candidates);
		verify(cacheMock).get(eq(key));
		verifyNoMoreInteractions(cacheMock, ruleMock, ruleFactoryMock);
	}

	/**
	 * Test für {@link IRule#getRuleForResponse(ErgebnisKombination)}.
	 */
	@Test
	public void testGetRuleForResponse() {
		final CacheRule underTest = new CacheRule(ruleMock, cacheMock,
				ruleFactoryMock);
		final IRule nextRuleMock = mock(IRule.class);
		final IRule wrappedNextRuleMock = mock(IRule.class);
		final ErgebnisKombination response = new ErgebnisKombination(0, 0);

		when(ruleMock.getRuleForResponse(any(ErgebnisKombination.class)))
				.thenReturn(nextRuleMock);
		final Map<Set<SpielKombination>, SpielKombination> anyCache = any();
		when(ruleFactoryMock.createCachingRule(any(IRule.class), anyCache))
				.thenReturn(wrappedNextRuleMock);

		assertSame(wrappedNextRuleMock, underTest.getRuleForResponse(response));

		verify(ruleMock).getRuleForResponse(same(response));
		verify(ruleFactoryMock).createCachingRule(same(nextRuleMock),
				same(cacheMock));
		verifyNoMoreInteractions(cacheMock, ruleMock, ruleFactoryMock,
				nextRuleMock, wrappedNextRuleMock);
	}
}
