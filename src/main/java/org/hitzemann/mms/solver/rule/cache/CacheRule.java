package org.hitzemann.mms.solver.rule.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * <p>
 * Eine Regel, die als Cache für eine andere Regel fungiert. Die geratene Kombination wird in Abhängigkeit von der
 * Kandidatenmenge (siehe {@link Set#equals(Object)}) in einem Cache hinterlegt.
 * </p>
 * 
 * <p>
 * Die Folge-Regeln werden ebenfalls von einer {@link CacheRule} umschlossen, wobei der Cache innerhalb der gesamten
 * Regel-Hierarchie gemeinsam verwendet wird.
 * </p>
 * 
 * <p>
 * Damit sich der Cache für den Aufrufer neutral verhält, darf die von der gekapselten Regel (und der gesamten
 * Hierarchie ihrer Folge-Regeln) bei {@link IRule#getGuess(List<SpielKombination>)} zurückgegebene
 * {@link SpielKombination} nur vom Parameter der Methode (also der Kandidatenmenge) abhängig sein.
 * </p>
 * 
 * @author chschu
 */
public final class CacheRule implements IRule {

    /**
     * Die gekapselte Regel.
     */
    private final IRule delegate;

    /**
     * Die Factory zur Erzeugung der Folge-Regeln.
     */
    private final ICacheRuleFactory ruleFactory;

    /**
     * Der Cache zur Abbildung einer Kandidatenmenge auf die zu ratende {@link SpielKombination}.
     */
    private Map<Set<SpielKombination>, SpielKombination> guessCache;

    /**
     * Erzeugt eine Instanz mit initial leerem Cache.
     * 
     * @param theDelegate
     *            Die gekapselte Regel.
     * @param theRuleFactory
     *            Die Factory zur Erzeugung der Folge-Regeln.
     */
    public CacheRule(final IRule theDelegate, final ICacheRuleFactory theRuleFactory) {
        this(theDelegate, new HashMap<Set<SpielKombination>, SpielKombination>(), theRuleFactory);
    }

    /**
     * Erzeugt eine Instanz mit gegebenem Cache. Wird zum Erzeugen der Folge-Regeln verwendet, da diese den gleichen
     * Cache verwenden sollen.
     * 
     * @param theDelegate
     *            Die gekapselte Regel.
     * @param theGuessCache
     *            Der zu verwendende Cache.
     * @param theRuleFactory
     *            Die Factory zur Erzeugung der Folge-Regeln.
     */
    public CacheRule(final IRule theDelegate, final Map<Set<SpielKombination>, SpielKombination> theGuessCache,
            final ICacheRuleFactory theRuleFactory) {
        delegate = theDelegate;
        ruleFactory = theRuleFactory;
        guessCache = theGuessCache;
    }

    @Override
    public SpielKombination getGuess(final List<SpielKombination> candidates) {
        // Kopie der Kandidatenmenge als Schlüssel verwenden
        final Set<SpielKombination> key = new HashSet<SpielKombination>(candidates);
        SpielKombination cached = guessCache.get(key);
        if (cached == null) {
            cached = delegate.getGuess(candidates);
            guessCache.put(key, cached);
        }
        return cached;
    }

    @Override
    public IRule getRuleForResponse(final ErgebnisKombination response) {
        // Folge-Regel wieder verpacken
        return ruleFactory.createCachingRule(delegate.getRuleForResponse(response), guessCache);
    }
}
