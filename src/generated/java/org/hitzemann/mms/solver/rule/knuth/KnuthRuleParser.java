// $ANTLR 3.4 C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g 2012-05-31 00:26:47

// CHECKSTYLE:OFF
package org.hitzemann.mms.solver.rule.knuth;

import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.model.SpielStein;
import org.hitzemann.mms.solver.rule.IRule;

import java.math.BigInteger;
import java.util.LinkedList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class KnuthRuleParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DIGIT", "'('", "')'", "','", "':'", "';'", "'x'", "'{'", "'}'"
    };

    public static final int EOF=-1;
    public static final int T__5=5;
    public static final int T__6=6;
    public static final int T__7=7;
    public static final int T__8=8;
    public static final int T__9=9;
    public static final int T__10=10;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int DIGIT=4;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public KnuthRuleParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public KnuthRuleParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return KnuthRuleParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g"; }


    private static final SpielStein[] colorValues = SpielStein.values();

    private IKnuthRuleFactory ruleFactory;

    public void setKnuthRuleFactory(IKnuthRuleFactory theRuleFactory) {
    	ruleFactory = theRuleFactory;
    }



    // $ANTLR start "start"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:34:1: start returns [IRule value] : r= rule EOF ;
    public final IRule start() throws RecognitionException {
        IRule value = null;


        IRule r =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:35:3: (r= rule EOF )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:36:3: r= rule EOF
            {
            pushFollow(FOLLOW_rule_in_start61);
            r=rule();

            state._fsp--;



                      value = r;
                     

            match(input,EOF,FOLLOW_EOF_in_start77); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "start"



    // $ANTLR start "rule"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:43:1: rule returns [IRule value] : (n= integer |n= integer '(' c= combination ( 'x' )? ')' |n= integer '(' c= combination ':' rll= rulelistlist ')' );
    public final IRule rule() throws RecognitionException {
        IRule value = null;


        BigInteger n =null;

        SpielKombination c =null;

        IRule[][] rll =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:44:3: (n= integer |n= integer '(' c= combination ( 'x' )? ')' |n= integer '(' c= combination ':' rll= rulelistlist ')' )
            int alt2=3;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:45:3: n= integer
                    {
                    pushFollow(FOLLOW_integer_in_rule98);
                    n=integer();

                    state._fsp--;



                                 value = ruleFactory.createGuessFirstRule(n.intValue(),
                                 		n.intValue());
                                

                    }
                    break;
                case 2 :
                    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:50:5: n= integer '(' c= combination ( 'x' )? ')'
                    {
                    pushFollow(FOLLOW_integer_in_rule121);
                    n=integer();

                    state._fsp--;


                    match(input,5,FOLLOW_5_in_rule123); 

                    pushFollow(FOLLOW_combination_in_rule127);
                    c=combination();

                    state._fsp--;



                                                     int maxNextCandidates = 1;
                                                    

                    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:54:3: ( 'x' )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==10) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:54:4: 'x'
                            {
                            match(input,10,FOLLOW_10_in_rule167); 


                                    maxNextCandidates = 2;
                                   

                            }
                            break;

                    }


                    match(input,6,FOLLOW_6_in_rule181); 


                                    value = ruleFactory.createGuessFixedSimpleRule(n.intValue(), c,
                                    		ruleFactory.createGuessFirstRule(0, maxNextCandidates));
                                   

                    }
                    break;
                case 3 :
                    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:62:5: n= integer '(' c= combination ':' rll= rulelistlist ')'
                    {
                    pushFollow(FOLLOW_integer_in_rule207);
                    n=integer();

                    state._fsp--;


                    match(input,5,FOLLOW_5_in_rule209); 

                    pushFollow(FOLLOW_combination_in_rule213);
                    c=combination();

                    state._fsp--;


                    match(input,8,FOLLOW_8_in_rule215); 

                    pushFollow(FOLLOW_rulelistlist_in_rule219);
                    rll=rulelistlist();

                    state._fsp--;


                    match(input,6,FOLLOW_6_in_rule221); 


                                                                              value = ruleFactory.createGuessFixedComplexRule(n.intValue(), c,
                                                                              		rll);
                                                                             

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "rule"



    // $ANTLR start "rulelistlist"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:69:1: rulelistlist returns [IRule[][] value] : r= rulelist ( ';' r= rulelist )* ;
    public final IRule[][] rulelistlist() throws RecognitionException {
        IRule[][] value = null;


        IRule[] r =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:70:3: (r= rulelist ( ';' r= rulelist )* )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:71:3: r= rulelist ( ';' r= rulelist )*
            {
            pushFollow(FOLLOW_rulelist_in_rulelistlist302);
            r=rulelist();

            state._fsp--;



                          List<IRule[]> resultList = new LinkedList<IRule[]>();
                          resultList.add(r);
                         

            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:76:3: ( ';' r= rulelist )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==9) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:76:4: ';' r= rulelist
            	    {
            	    match(input,9,FOLLOW_9_in_rulelistlist323); 

            	    pushFollow(FOLLOW_rulelist_in_rulelistlist327);
            	    r=rulelist();

            	    state._fsp--;



            	                       resultList.add(r);
            	                      

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);



                                   value = resultList.toArray(new IRule[resultList.size()][]);
                                  

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "rulelistlist"



    // $ANTLR start "rulelist"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:85:1: rulelist returns [IRule[] value] : r= rule ( ',' r= rule )* ;
    public final IRule[] rulelist() throws RecognitionException {
        IRule[] value = null;


        IRule r =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:86:3: (r= rule ( ',' r= rule )* )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:87:3: r= rule ( ',' r= rule )*
            {
            pushFollow(FOLLOW_rule_in_rulelist396);
            r=rule();

            state._fsp--;



                      List<IRule> resultList = new LinkedList<IRule>();
                      resultList.add(r);
                     

            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:92:3: ( ',' r= rule )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==7) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:92:4: ',' r= rule
            	    {
            	    match(input,7,FOLLOW_7_in_rulelist413); 

            	    pushFollow(FOLLOW_rule_in_rulelist417);
            	    r=rule();

            	    state._fsp--;



            	                   resultList.add(r);
            	                  

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);



                               value = resultList.toArray(new IRule[resultList.size()]);
                              

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "rulelist"



    // $ANTLR start "combination"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:101:1: combination returns [SpielKombination value] : (d= DIGIT | '{' i= integer '}' )+ ;
    public final SpielKombination combination() throws RecognitionException {
        SpielKombination value = null;


        Token d=null;
        BigInteger i =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:102:3: ( (d= DIGIT | '{' i= integer '}' )+ )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:104:4: (d= DIGIT | '{' i= integer '}' )+
            {

                List<SpielStein> tempList = new LinkedList<SpielStein>();
               

            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:107:3: (d= DIGIT | '{' i= integer '}' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=3;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==DIGIT) ) {
                    alt5=1;
                }
                else if ( (LA5_0==11) ) {
                    alt5=2;
                }


                switch (alt5) {
            	case 1 :
            	    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:108:5: d= DIGIT
            	    {
            	    d=(Token)match(input,DIGIT,FOLLOW_DIGIT_in_combination492); 


            	                 int digitValue = Character.getNumericValue((d!=null?d.getText():null).charAt(0));
            	                 tempList.add(colorValues[digitValue - 1]);
            	                

            	    }
            	    break;
            	case 2 :
            	    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:113:7: '{' i= integer '}'
            	    {
            	    match(input,11,FOLLOW_11_in_combination515); 

            	    pushFollow(FOLLOW_integer_in_combination519);
            	    i=integer();

            	    state._fsp--;


            	    match(input,12,FOLLOW_12_in_combination521); 


            	                             tempList.add(colorValues[i.intValue() - 1]);
            	                            

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);



                value = new SpielKombination(tempList.toArray(new SpielStein[tempList.size()]));
               

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "combination"



    // $ANTLR start "integer"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:124:1: integer returns [BigInteger value] : (d= DIGIT )+ ;
    public final BigInteger integer() throws RecognitionException {
        BigInteger value = null;


        Token d=null;

        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:125:3: ( (d= DIGIT )+ )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:127:4: (d= DIGIT )+
            {

                StringBuilder builder = new StringBuilder();
               

            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:130:3: (d= DIGIT )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==DIGIT) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:130:4: d= DIGIT
            	    {
            	    d=(Token)match(input,DIGIT,FOLLOW_DIGIT_in_integer591); 


            	                builder.append((d!=null?d.getText():null));
            	               

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);



                            value = new BigInteger(builder.toString());
                           

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "integer"

    // Delegated rules


    protected DFA2 dfa2 = new DFA2(this);
    static final String DFA2_eotS =
        "\12\uffff";
    static final String DFA2_eofS =
        "\1\uffff\1\2\10\uffff";
    static final String DFA2_minS =
        "\2\4\1\uffff\3\4\2\uffff\2\4";
    static final String DFA2_maxS =
        "\1\4\1\11\1\uffff\2\13\1\4\2\uffff\1\14\1\13";
    static final String DFA2_acceptS =
        "\2\uffff\1\1\3\uffff\1\2\1\3\2\uffff";
    static final String DFA2_specialS =
        "\12\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\1",
            "\1\1\1\3\2\2\1\uffff\1\2",
            "",
            "\1\4\6\uffff\1\5",
            "\1\4\1\uffff\1\6\1\uffff\1\7\1\uffff\1\6\1\5",
            "\1\10",
            "",
            "",
            "\1\10\7\uffff\1\11",
            "\1\4\1\uffff\1\6\1\uffff\1\7\1\uffff\1\6\1\5"
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "43:1: rule returns [IRule value] : (n= integer |n= integer '(' c= combination ( 'x' )? ')' |n= integer '(' c= combination ':' rll= rulelistlist ')' );";
        }
    }
 

    public static final BitSet FOLLOW_rule_in_start61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_start77 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_in_rule98 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_in_rule121 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_5_in_rule123 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_combination_in_rule127 = new BitSet(new long[]{0x0000000000000440L});
    public static final BitSet FOLLOW_10_in_rule167 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_6_in_rule181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_in_rule207 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_5_in_rule209 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_combination_in_rule213 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_rule215 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulelistlist_in_rule219 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_6_in_rule221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelist_in_rulelistlist302 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_9_in_rulelistlist323 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulelist_in_rulelistlist327 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_rule_in_rulelist396 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_7_in_rulelist413 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule_in_rulelist417 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_DIGIT_in_combination492 = new BitSet(new long[]{0x0000000000000812L});
    public static final BitSet FOLLOW_11_in_combination515 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_integer_in_combination519 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_combination521 = new BitSet(new long[]{0x0000000000000812L});
    public static final BitSet FOLLOW_DIGIT_in_integer591 = new BitSet(new long[]{0x0000000000000012L});

}