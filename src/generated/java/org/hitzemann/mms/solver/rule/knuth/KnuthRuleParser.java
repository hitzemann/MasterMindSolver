// $ANTLR 3.4 C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g 2012-06-10 17:56:00

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

    public final void setKnuthRuleFactory(IKnuthRuleFactory theRuleFactory) {
    	ruleFactory = theRuleFactory;
    }



    // $ANTLR start "start"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:32:1: start returns [IRule value] : r= rule EOF ;
    public final IRule start() throws RecognitionException {
        IRule value = null;


        IRule r =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:36:3: (r= rule EOF )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:37:3: r= rule EOF
            {
            pushFollow(FOLLOW_rule_in_start66);
            r=rule();

            state._fsp--;


            match(input,EOF,FOLLOW_EOF_in_start68); 

            }


            value = r;

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
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:40:1: rule returns [IRule value] : (r= ruleAtMostTwo |r= ruleGuessFixedLeavesAtMostOne |r= ruleGuessFixedLeavesAtMostTwo |r= ruleGuessFixedLeavesMoreThanTwo );
    public final IRule rule() throws RecognitionException {
        IRule value = null;


        IRule r =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:44:3: (r= ruleAtMostTwo |r= ruleGuessFixedLeavesAtMostOne |r= ruleGuessFixedLeavesAtMostTwo |r= ruleGuessFixedLeavesMoreThanTwo )
            int alt1=4;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:45:3: r= ruleAtMostTwo
                    {
                    pushFollow(FOLLOW_ruleAtMostTwo_in_rule94);
                    r=ruleAtMostTwo();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:46:5: r= ruleGuessFixedLeavesAtMostOne
                    {
                    pushFollow(FOLLOW_ruleGuessFixedLeavesAtMostOne_in_rule102);
                    r=ruleGuessFixedLeavesAtMostOne();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:47:5: r= ruleGuessFixedLeavesAtMostTwo
                    {
                    pushFollow(FOLLOW_ruleGuessFixedLeavesAtMostTwo_in_rule110);
                    r=ruleGuessFixedLeavesAtMostTwo();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:48:5: r= ruleGuessFixedLeavesMoreThanTwo
                    {
                    pushFollow(FOLLOW_ruleGuessFixedLeavesMoreThanTwo_in_rule118);
                    r=ruleGuessFixedLeavesMoreThanTwo();

                    state._fsp--;


                    }
                    break;

            }

            value = r;

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



    // $ANTLR start "ruleAtMostTwo"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:51:1: ruleAtMostTwo returns [IRule value] : n= integer ;
    public final IRule ruleAtMostTwo() throws RecognitionException {
        IRule value = null;


        BigInteger n =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:56:3: (n= integer )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:57:3: n= integer
            {
            pushFollow(FOLLOW_integer_in_ruleAtMostTwo144);
            n=integer();

            state._fsp--;


            }


            final int candidateCount = n.intValue();
            value = ruleFactory.createGuessFirstRule(candidateCount, candidateCount);

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
    // $ANTLR end "ruleAtMostTwo"



    // $ANTLR start "ruleGuessFixedLeavesAtMostOne"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:60:1: ruleGuessFixedLeavesAtMostOne returns [IRule value] : n= integer '(' c= combination ')' ;
    public final IRule ruleGuessFixedLeavesAtMostOne() throws RecognitionException {
        IRule value = null;


        BigInteger n =null;

        SpielKombination c =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:65:3: (n= integer '(' c= combination ')' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:66:3: n= integer '(' c= combination ')'
            {
            pushFollow(FOLLOW_integer_in_ruleGuessFixedLeavesAtMostOne170);
            n=integer();

            state._fsp--;


            match(input,5,FOLLOW_5_in_ruleGuessFixedLeavesAtMostOne172); 

            pushFollow(FOLLOW_combination_in_ruleGuessFixedLeavesAtMostOne176);
            c=combination();

            state._fsp--;


            match(input,6,FOLLOW_6_in_ruleGuessFixedLeavesAtMostOne178); 

            }


            value = ruleFactory.createGuessFixedSimpleRule(n.intValue(), c,
            		ruleFactory.createGuessFirstRule(0, 1));

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
    // $ANTLR end "ruleGuessFixedLeavesAtMostOne"



    // $ANTLR start "ruleGuessFixedLeavesAtMostTwo"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:69:1: ruleGuessFixedLeavesAtMostTwo returns [IRule value] : n= integer '(' c= combination 'x' ')' ;
    public final IRule ruleGuessFixedLeavesAtMostTwo() throws RecognitionException {
        IRule value = null;


        BigInteger n =null;

        SpielKombination c =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:74:3: (n= integer '(' c= combination 'x' ')' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:75:3: n= integer '(' c= combination 'x' ')'
            {
            pushFollow(FOLLOW_integer_in_ruleGuessFixedLeavesAtMostTwo204);
            n=integer();

            state._fsp--;


            match(input,5,FOLLOW_5_in_ruleGuessFixedLeavesAtMostTwo206); 

            pushFollow(FOLLOW_combination_in_ruleGuessFixedLeavesAtMostTwo210);
            c=combination();

            state._fsp--;


            match(input,10,FOLLOW_10_in_ruleGuessFixedLeavesAtMostTwo212); 

            match(input,6,FOLLOW_6_in_ruleGuessFixedLeavesAtMostTwo214); 

            }


            value = ruleFactory.createGuessFixedSimpleRule(n.intValue(), c,
            		ruleFactory.createGuessFirstRule(0, 2));

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
    // $ANTLR end "ruleGuessFixedLeavesAtMostTwo"



    // $ANTLR start "ruleGuessFixedLeavesMoreThanTwo"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:78:1: ruleGuessFixedLeavesMoreThanTwo returns [IRule value] : n= integer '(' c= combination ':' rll= ruleListList ')' ;
    public final IRule ruleGuessFixedLeavesMoreThanTwo() throws RecognitionException {
        IRule value = null;


        BigInteger n =null;

        SpielKombination c =null;

        IRule[][] rll =null;


        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:83:3: (n= integer '(' c= combination ':' rll= ruleListList ')' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:84:3: n= integer '(' c= combination ':' rll= ruleListList ')'
            {
            pushFollow(FOLLOW_integer_in_ruleGuessFixedLeavesMoreThanTwo240);
            n=integer();

            state._fsp--;


            match(input,5,FOLLOW_5_in_ruleGuessFixedLeavesMoreThanTwo242); 

            pushFollow(FOLLOW_combination_in_ruleGuessFixedLeavesMoreThanTwo246);
            c=combination();

            state._fsp--;


            match(input,8,FOLLOW_8_in_ruleGuessFixedLeavesMoreThanTwo248); 

            pushFollow(FOLLOW_ruleListList_in_ruleGuessFixedLeavesMoreThanTwo252);
            rll=ruleListList();

            state._fsp--;


            match(input,6,FOLLOW_6_in_ruleGuessFixedLeavesMoreThanTwo254); 

            }


            value = ruleFactory.createGuessFixedComplexRule(n.intValue(), c,
            		rll);

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
    // $ANTLR end "ruleGuessFixedLeavesMoreThanTwo"



    // $ANTLR start "ruleListList"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:87:1: ruleListList returns [IRule[][] value] : r= ruleList ( ';' r= ruleList )* ;
    public final IRule[][] ruleListList() throws RecognitionException {
        IRule[][] value = null;


        IRule[] r =null;



        final List<IRule[]> resultList = new LinkedList<IRule[]>();

        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:94:3: (r= ruleList ( ';' r= ruleList )* )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:95:3: r= ruleList ( ';' r= ruleList )*
            {
            pushFollow(FOLLOW_ruleList_in_ruleListList285);
            r=ruleList();

            state._fsp--;



                          resultList.add(r);
                         

            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:99:3: ( ';' r= ruleList )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==9) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:99:4: ';' r= ruleList
            	    {
            	    match(input,9,FOLLOW_9_in_ruleListList306); 

            	    pushFollow(FOLLOW_ruleList_in_ruleListList310);
            	    r=ruleList();

            	    state._fsp--;



            	                       resultList.add(r);
            	                      

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


            value = resultList.toArray(new IRule[resultList.size()][]);

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
    // $ANTLR end "ruleListList"



    // $ANTLR start "ruleList"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:105:1: ruleList returns [IRule[] value] : r= rule ( ',' r= rule )* ;
    public final IRule[] ruleList() throws RecognitionException {
        IRule[] value = null;


        IRule r =null;



        final List<IRule> resultList = new LinkedList<IRule>();

        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:112:3: (r= rule ( ',' r= rule )* )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:113:3: r= rule ( ',' r= rule )*
            {
            pushFollow(FOLLOW_rule_in_ruleList364);
            r=rule();

            state._fsp--;



                      resultList.add(r);
                     

            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:117:3: ( ',' r= rule )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==7) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:117:4: ',' r= rule
            	    {
            	    match(input,7,FOLLOW_7_in_ruleList381); 

            	    pushFollow(FOLLOW_rule_in_ruleList385);
            	    r=rule();

            	    state._fsp--;



            	                   resultList.add(r);
            	                  

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }


            value = resultList.toArray(new IRule[resultList.size()]);

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
    // $ANTLR end "ruleList"



    // $ANTLR start "combination"
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:123:1: combination returns [SpielKombination value] : (d= DIGIT | '{' i= integer '}' )+ ;
    public final SpielKombination combination() throws RecognitionException {
        SpielKombination value = null;


        Token d=null;
        BigInteger i =null;



        final List<SpielStein> tempList = new LinkedList<SpielStein>();

        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:130:3: ( (d= DIGIT | '{' i= integer '}' )+ )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:131:3: (d= DIGIT | '{' i= integer '}' )+
            {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:131:3: (d= DIGIT | '{' i= integer '}' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=3;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==DIGIT) ) {
                    alt4=1;
                }
                else if ( (LA4_0==11) ) {
                    alt4=2;
                }


                switch (alt4) {
            	case 1 :
            	    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:132:5: d= DIGIT
            	    {
            	    d=(Token)match(input,DIGIT,FOLLOW_DIGIT_in_combination441); 


            	                 int digitValue = Character.getNumericValue((d!=null?d.getText():null).charAt(0));
            	                 tempList.add(colorValues[digitValue - 1]);
            	                

            	    }
            	    break;
            	case 2 :
            	    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:137:7: '{' i= integer '}'
            	    {
            	    match(input,11,FOLLOW_11_in_combination464); 

            	    pushFollow(FOLLOW_integer_in_combination468);
            	    i=integer();

            	    state._fsp--;


            	    match(input,12,FOLLOW_12_in_combination470); 


            	                             tempList.add(colorValues[i.intValue() - 1]);
            	                            

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }


            value = new SpielKombination(tempList.toArray(new SpielStein[tempList.size()]));

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
    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:144:1: integer returns [BigInteger value] : (d= DIGIT )+ ;
    public final BigInteger integer() throws RecognitionException {
        BigInteger value = null;


        Token d=null;


        final StringBuilder builder = new StringBuilder();

        try {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:151:3: ( (d= DIGIT )+ )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:152:3: (d= DIGIT )+
            {
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:152:3: (d= DIGIT )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==DIGIT) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:152:4: d= DIGIT
            	    {
            	    d=(Token)match(input,DIGIT,FOLLOW_DIGIT_in_integer534); 


            	                builder.append((d!=null?d.getText():null));
            	               

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


            }


            value = new BigInteger(builder.toString());

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


    protected DFA1 dfa1 = new DFA1(this);
    static final String DFA1_eotS =
        "\13\uffff";
    static final String DFA1_eofS =
        "\1\uffff\1\2\11\uffff";
    static final String DFA1_minS =
        "\2\4\1\uffff\3\4\3\uffff\2\4";
    static final String DFA1_maxS =
        "\1\4\1\11\1\uffff\2\13\1\4\3\uffff\1\14\1\13";
    static final String DFA1_acceptS =
        "\2\uffff\1\1\3\uffff\1\2\1\3\1\4\2\uffff";
    static final String DFA1_specialS =
        "\13\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\1",
            "\1\1\1\3\2\2\1\uffff\1\2",
            "",
            "\1\4\6\uffff\1\5",
            "\1\4\1\uffff\1\6\1\uffff\1\10\1\uffff\1\7\1\5",
            "\1\11",
            "",
            "",
            "",
            "\1\11\7\uffff\1\12",
            "\1\4\1\uffff\1\6\1\uffff\1\10\1\uffff\1\7\1\5"
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "40:1: rule returns [IRule value] : (r= ruleAtMostTwo |r= ruleGuessFixedLeavesAtMostOne |r= ruleGuessFixedLeavesAtMostTwo |r= ruleGuessFixedLeavesMoreThanTwo );";
        }
    }
 

    public static final BitSet FOLLOW_rule_in_start66 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_start68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAtMostTwo_in_rule94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGuessFixedLeavesAtMostOne_in_rule102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGuessFixedLeavesAtMostTwo_in_rule110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGuessFixedLeavesMoreThanTwo_in_rule118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_in_ruleAtMostTwo144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_in_ruleGuessFixedLeavesAtMostOne170 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_5_in_ruleGuessFixedLeavesAtMostOne172 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_combination_in_ruleGuessFixedLeavesAtMostOne176 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_6_in_ruleGuessFixedLeavesAtMostOne178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_in_ruleGuessFixedLeavesAtMostTwo204 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_5_in_ruleGuessFixedLeavesAtMostTwo206 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_combination_in_ruleGuessFixedLeavesAtMostTwo210 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleGuessFixedLeavesAtMostTwo212 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_6_in_ruleGuessFixedLeavesAtMostTwo214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_in_ruleGuessFixedLeavesMoreThanTwo240 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_5_in_ruleGuessFixedLeavesMoreThanTwo242 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_combination_in_ruleGuessFixedLeavesMoreThanTwo246 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_ruleGuessFixedLeavesMoreThanTwo248 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleListList_in_ruleGuessFixedLeavesMoreThanTwo252 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_6_in_ruleGuessFixedLeavesMoreThanTwo254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleList_in_ruleListList285 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_9_in_ruleListList306 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleList_in_ruleListList310 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_rule_in_ruleList364 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_7_in_ruleList381 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule_in_ruleList385 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_DIGIT_in_combination441 = new BitSet(new long[]{0x0000000000000812L});
    public static final BitSet FOLLOW_11_in_combination464 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_integer_in_combination468 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_combination470 = new BitSet(new long[]{0x0000000000000812L});
    public static final BitSet FOLLOW_DIGIT_in_integer534 = new BitSet(new long[]{0x0000000000000012L});

}