package org.hitzemann.mms.solver.rule.cache;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Map;

import org.hitzemann.mms.solver.rule.IRule;
import org.junit.Test;

/**
 * Tests für {@link CacheRuleFactory}.
 * 
 * @author chschu
 */
public final class CacheRuleFactoryTest {

	/**
	 * Die zu testende Instanz.
	 */
	private final ICacheRuleFactory underTest = new CacheRuleFactory();

	/**
	 * Test für die Methode zur Erzeugung einer {@link CacheRule} mit initial
	 * leerem Cache.
	 */
	@Test
	public void testCreateCacheRuleNewCache() {
		final IRule rule = underTest.createCachingRule(mock(IRule.class));
		assertEquals(CacheRule.class, rule.getClass());
	}

	/**
	 * Test für die Methode zur Erzeugung einer {@link CacheRule} mit
	 * übergebenem Cache.
	 */
	@Test
	public void tetCreateCachedRuleReuseCache() {
		@SuppressWarnings("unchecked")
		final IRule rule = underTest.createCachingRule(mock(IRule.class),
				mock(Map.class));
		assertEquals(CacheRule.class, rule.getClass());
	}
}
