package org.hitzemann.mms.solver.rule.cache;

import java.util.Map;
import java.util.Set;

import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * Factory-Schnittstelle zur Erzeugung der benötigten Regel-Typen.
 * 
 * @author chschu
 */
public interface ICacheRuleFactory {

    /**
     * Erzeugt eine Instanz von {@link CacheRule} mit initial leerem Cache.
     * 
     * @param delegate
     *            Die gekapselte Regel.
     * @return Die neu erzeugte Regel-Instanz.
     */
    IRule createCachingRule(IRule delegate);

    /**
     * Erzeugt eine Instanz von {@link CacheRule} mit gegebenem Cache.
     * 
     * @param delegate
     *            Die gekapselte Regel.
     * @param guessCache
     *            Der zu verwendende Cache.
     * @return Die neu erzeugte Regel-Instanz.
     */
    IRule createCachingRule(IRule delegate, Map<Set<SpielKombination>, SpielKombination> guessCache);
}
