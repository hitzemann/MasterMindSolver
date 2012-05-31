package org.hitzemann.mms.solver.rule.cache;

import java.util.Map;
import java.util.Set;

import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * Implementierung von {@link ICacheRuleFactory}.
 * 
 * @author schusterc
 */
public final class CacheRuleFactory implements ICacheRuleFactory {

	@Override
	public IRule createCachingRule(final IRule delegate) {
		return new CacheRule(delegate, this);
	}

	@Override
	public IRule createCachingRule(final IRule delegate,
			final Map<Set<SpielKombination>, SpielKombination> cache) {
		return new CacheRule(delegate, cache, this);
	}
}
