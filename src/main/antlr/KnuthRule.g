grammar KnuthRule;

options {
  language = Java;
}

@parser::header {
// CHECKSTYLE:OFF
package org.hitzemann.mms.solver.rule.knuth;

import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.hitzemann.mms.solver.rule.IRule;

import java.math.BigInteger;
import java.util.LinkedList;
}

@lexer::header {
// CHECKSTYLE:OFF
package org.hitzemann.mms.solver.rule.knuth;
}

@parser::members {
private static final SpielStein[] colorValues = SpielStein.values();

private IKnuthRuleFactory ruleFactory;

public void setKnuthRuleFactory(IKnuthRuleFactory theRuleFactory) {
	ruleFactory = theRuleFactory;
}
}

start returns [IRule value]
  :
  r=rule 
         {
          $value = $r.value;
         }
  EOF
  ;

rule returns [IRule value]
  :
  n=integer 
            {
             $value = ruleFactory.createGuessFirstRule($n.value.intValue(),
             		$n.value.intValue());
            }
  | n=integer '(' c=combination 
                                {
                                 int maxNextCandidates = 1;
                                }
  ('x' 
       {
        maxNextCandidates = 2;
       })? ')' 
               {
                $value = ruleFactory.createGuessFixedSimpleRule($n.value.intValue(), $c.value,
                		ruleFactory.createGuessFirstRule(0, maxNextCandidates));
               }
  | n=integer '(' c=combination ':' rll=rulelistlist ')' 
                                                         {
                                                          $value = ruleFactory.createGuessFixedComplexRule($n.value.intValue(), $c.value,
                                                          		$rll.value);
                                                         }
  ;

rulelistlist returns [IRule[\][\] value]
  :
  r=rulelist 
             {
              List<IRule[]> resultList = new LinkedList<IRule[]>();
              resultList.add($r.value);
             }
  (';' r=rulelist 
                  {
                   resultList.add($r.value);
                  })* 
                      {
                       $value = resultList.toArray(new IRule[resultList.size()][]);
                      }
  ;

rulelist returns [IRule[\] value]
  :
  r=rule 
         {
          List<IRule> resultList = new LinkedList<IRule>();
          resultList.add($r.value);
         }
  (',' r=rule 
              {
               resultList.add($r.value);
              })* 
                  {
                   $value = resultList.toArray(new IRule[resultList.size()]);
                  }
  ;

combination returns [SpielKombination value]
  :
  n=colorsequence 
                  {
                   $value = new SpielKombination($n.value.toArray(new SpielStein[$n.value.size()]));
                  }
  ;

colorsequence returns [List < SpielStein > value]
  :
  
   {
    $value = new LinkedList<SpielStein>();
   }
  (
    d=DIGIT 
            {
             int digitValue = Character.getNumericValue($d.text.charAt(0));
             $value.add(colorValues[digitValue - 1]);
            }
    | '{' i=integer '}' 
                        {
                         $value.add(colorValues[$i.value.intValue() - 1]);
                        }
  )+
  ;

integer returns [BigInteger value]
  :
  
   {
    StringBuilder builder = new StringBuilder();
   }
  (d=DIGIT 
           {
            builder.append($d.text);
           })+ 
               {
                $value = new BigInteger(builder.toString());
               }
  ;

DIGIT
  :
  '0'..'9'
  ;
