// $ANTLR 3.4 C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g 2012-06-10 17:56:00

package org.hitzemann.mms.solver.rule.knuth;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class KnuthRuleLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public KnuthRuleLexer() {} 
    public KnuthRuleLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public KnuthRuleLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g"; }

    // $ANTLR start "T__5"
    public final void mT__5() throws RecognitionException {
        try {
            int _type = T__5;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:11:6: ( '(' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:11:8: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__5"

    // $ANTLR start "T__6"
    public final void mT__6() throws RecognitionException {
        try {
            int _type = T__6;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:12:6: ( ')' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:12:8: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__6"

    // $ANTLR start "T__7"
    public final void mT__7() throws RecognitionException {
        try {
            int _type = T__7;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:13:6: ( ',' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:13:8: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__7"

    // $ANTLR start "T__8"
    public final void mT__8() throws RecognitionException {
        try {
            int _type = T__8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:14:6: ( ':' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:14:8: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__8"

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:15:6: ( ';' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:15:8: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:16:7: ( 'x' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:16:9: 'x'
            {
            match('x'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:17:7: ( '{' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:17:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:18:7: ( '}' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:18:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            int _type = DIGIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:159:3: ( '0' .. '9' )
            // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIGIT"

    public void mTokens() throws RecognitionException {
        // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:1:8: ( T__5 | T__6 | T__7 | T__8 | T__9 | T__10 | T__11 | T__12 | DIGIT )
        int alt1=9;
        switch ( input.LA(1) ) {
        case '(':
            {
            alt1=1;
            }
            break;
        case ')':
            {
            alt1=2;
            }
            break;
        case ',':
            {
            alt1=3;
            }
            break;
        case ':':
            {
            alt1=4;
            }
            break;
        case ';':
            {
            alt1=5;
            }
            break;
        case 'x':
            {
            alt1=6;
            }
            break;
        case '{':
            {
            alt1=7;
            }
            break;
        case '}':
            {
            alt1=8;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt1=9;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 1, 0, input);

            throw nvae;

        }

        switch (alt1) {
            case 1 :
                // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:1:10: T__5
                {
                mT__5(); 


                }
                break;
            case 2 :
                // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:1:15: T__6
                {
                mT__6(); 


                }
                break;
            case 3 :
                // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:1:20: T__7
                {
                mT__7(); 


                }
                break;
            case 4 :
                // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:1:25: T__8
                {
                mT__8(); 


                }
                break;
            case 5 :
                // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:1:30: T__9
                {
                mT__9(); 


                }
                break;
            case 6 :
                // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:1:35: T__10
                {
                mT__10(); 


                }
                break;
            case 7 :
                // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:1:41: T__11
                {
                mT__11(); 


                }
                break;
            case 8 :
                // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:1:47: T__12
                {
                mT__12(); 


                }
                break;
            case 9 :
                // C:\\Dokumente und Einstellungen\\schusterc\\git\\MasterMindSolver\\src\\main\\antlr\\KnuthRule.g:1:53: DIGIT
                {
                mDIGIT(); 


                }
                break;

        }

    }


 

}