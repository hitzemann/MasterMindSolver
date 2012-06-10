grammar KnuthRule;

options {
  language = Java;
}

@parser::header {
package org.hitzemann.mms.solver.rule.knuth;

import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.hitzemann.mms.solver.rule.IRule;

import java.math.BigInteger;
import java.util.LinkedList;
}

@lexer::header {
package org.hitzemann.mms.solver.rule.knuth;
}

@parser::members {
private static final SpielStein[] colorValues = SpielStein.values();

private IKnuthRuleFactory ruleFactory;

public final void setKnuthRuleFactory(IKnuthRuleFactory theRuleFactory) {
	ruleFactory = theRuleFactory;
}
}

start returns [IRule value]
@after {
$value = $r.value;
}
  :
  r=rule EOF
  ;

rule returns [IRule value]
@after {
$value = $r.value;
}
  :
  r=ruleAtMostTwo
  | r=ruleGuessFixedLeavesAtMostOne
  | r=ruleGuessFixedLeavesAtMostTwo
  | r=ruleGuessFixedLeavesMoreThanTwo
  ;

ruleAtMostTwo returns [IRule value]
@after {
final int candidateCount = $n.value.intValue();
$value = ruleFactory.createGuessFirstRule(candidateCount, candidateCount);
}
  :
  n=integer
  ;

ruleGuessFixedLeavesAtMostOne returns [IRule value]
@after {
$value = ruleFactory.createGuessFixedSimpleRule($n.value.intValue(), $c.value,
		ruleFactory.createGuessFirstRule(0, 1));
}
  :
  n=integer '(' c=combination ')'
  ;

ruleGuessFixedLeavesAtMostTwo returns [IRule value]
@after {
$value = ruleFactory.createGuessFixedSimpleRule($n.value.intValue(), $c.value,
		ruleFactory.createGuessFirstRule(0, 2));
}
  :
  n=integer '(' c=combination 'x' ')'
  ;

ruleGuessFixedLeavesMoreThanTwo returns [IRule value]
@after {
$value = ruleFactory.createGuessFixedComplexRule($n.value.intValue(), $c.value,
		$rll.value);
}
  :
  n=integer '(' c=combination ':' rll=ruleListList ')'
  ;

ruleListList returns [IRule[\][\] value]
@init {
final List<IRule[]> resultList = new LinkedList<IRule[]>();
}
@after {
$value = resultList.toArray(new IRule[resultList.size()][]);
}
  :
  r=ruleList 
             {
              resultList.add($r.value);
             }
  (';' r=ruleList 
                  {
                   resultList.add($r.value);
                  })*
  ;

ruleList returns [IRule[\] value]
@init {
final List<IRule> resultList = new LinkedList<IRule>();
}
@after {
$value = resultList.toArray(new IRule[resultList.size()]);
}
  :
  r=rule 
         {
          resultList.add($r.value);
         }
  (',' r=rule 
              {
               resultList.add($r.value);
              })*
  ;

combination returns [SpielKombination value]
@init {
final List<SpielStein> tempList = new LinkedList<SpielStein>();
}
@after {
$value = new SpielKombination(tempList.toArray(new SpielStein[tempList.size()]));
}
  :
  (
    d=DIGIT 
            {
             int digitValue = Character.getNumericValue($d.text.charAt(0));
             tempList.add(colorValues[digitValue - 1]);
            }
    | '{' i=integer '}' 
                        {
                         tempList.add(colorValues[$i.value.intValue() - 1]);
                        }
  )+
  ;

integer returns [BigInteger value]
@init {
final StringBuilder builder = new StringBuilder();
}
@after {
$value = new BigInteger(builder.toString());
}
  :
  (d=DIGIT 
           {
            builder.append($d.text);
           })+
  ;

DIGIT
  :
  '0'..'9'
  ;
