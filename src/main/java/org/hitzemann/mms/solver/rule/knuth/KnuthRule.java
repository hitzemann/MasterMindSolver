package org.hitzemann.mms.solver.rule.knuth;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.hitzemann.mms.model.ErgebnisKombination;
import org.hitzemann.mms.model.SpielKombination;
import org.hitzemann.mms.solver.rule.IRule;

/**
 * <p>
 * Eine auf Knuth's Algorithmus basierende Regel für Kombinationen mit 4 Pins.
 * </p>
 * 
 * <p>
 * Die verwendete Regel wird rekursiv beschrieben. Wenn n die Anzahl der Kandidaten ist, dann gibt es die folgenden
 * Arten von Regeln:
 * </p>
 * 
 * <ol>
 * <li>n, n &lt;= 2</li>
 * <li>n(y<sub>1</sub>y<sub>2</sub>y<sub>3</sub>y<sub>4</sub>), n &gt; 2 und nach dem Raten der Kombination
 * y<sub>1</sub>y<sub>2</sub>y<sub>3</sub>y<sub>4</sub> ist die geheime Kombination eindeutig definiert</li>
 * <li>n(y<sub>1</sub>y<sub>2</sub>y<sub>3</sub>y<sub>4</sub>x), n &gt; 2 und nach dem Raten der Kombination
 * y<sub>1</sub>y<sub>2</sub>y<sub>3</sub>y<sub>4</sub> ist die geheime Kombination nicht eindeutig bestimmt, aber auf
 * maximal 2 Möglichkeiten beschränkt</li>
 * <li>n(y<sub>1</sub>y<sub>2</sub>y<sub>3</sub>y<sub>4</sub>: a<sub>04</sub>,a<sub
 * >03</sub>,a<sub>02</sub>,a<sub>01</sub>,a<sub>00</sub>;a<sub >13</sub>,a<sub>12
 * </sub>,a<sub>11</sub>,a<sub>10</sub>;a<sub>22</sub>,a<sub>21
 * </sub>,a<sub>20</sub>;a<sub>31</sub>,a<sub>30</sub>;a<sub>40</sub>), n &gt; 2 und nach dem Raten der Kombination
 * y<sub>1</sub>y<sub>2</sub>y<sub>3</sub>y<sub>4</sub> mit der Antwort &bdquo;j schwarz, k weiss&ldquo; wird mit der
 * Folge-Regel a<sub>jk</sub> fortgesetzt</li>
 * </ol>
 * 
 * <p>
 * Für n = 1 ist die geheime Kombination bekannt, muss aber ggf. noch geraten werden.
 * </p>
 * 
 * <p>
 * Für n = 2 kann eine beliebige der beiden Möglichkeiten geraten werden, danach ggf. auch noch die andere.
 * </p>
 * 
 * @author chschu
 */
public final class KnuthRule implements IRule {

    /**
     * Die Haupt-Regel.
     */
    private static final String MAIN = "1296(1122:1,16(1213:0,0,0,0,0;1,4(1415),3(1145),0;1,3(4115),3(1145);0,1;0),96A,256B,256C;0,36D,208E,256F;4(1213),32G,114H;0,20I;1)";

    /**
     * Die Teil-Regel "A".
     */
    private static final String A = "(2344:0,2,16(3215:0,0,0,0,0;1,2,1,1;2,3(3231),2;0,3(3213);1),14(5215:0,0,0,0,0;0,1,3(3511),3(3611);1,1,2;0,2;1),4(1515);0,6(2413),18(2415:1,1,0,0,0;1,2,3(2253),3(2236);1,2,2;0,1;1),15(2256x);0,4(2234),14(3315x);0,3(2314);0)";

    /**
     * Die Teil-Regel "B".
     */
    private static final String B = "(2344:0,7(2335),41(3235:0,0,2,3(4613),2;0,3(5263),6(3413),6(3416);2,4(3256),6(1336);0,6(1536);1),44(3516:1,4(4651),6(6255),1,0;3(5613),7(1461),5(4551),1;3(1113),5(3551),3(4515);0,4(1145);1),16(5515:0,0,1,1,0;0,2,2,1;1,1,3(1516);0,3(1516);1);2,21(3245:1,3(2436),0,0,0;2,2,2,0;2,3(3234),2;0,3(3243);1),42(4514:1,1,7(2456),4(2635),3(2636);0,4(1356),5(4361),6(1635);2,2,3(3614);0,3(4414);1),34(3315:0,0,3(5641),4(2566),1;1,4(5361),4(5614),5(6614);2,4(3331),1;0,4(3316);1);3(2434),13(2425x),23(1545:0,1,3(2654),3(2353),4(1136);0,2,4(2564),3(2335);0,0,2;0,1;0);0,9(1335x);1)";

    /**
     * Die Teil-Regel "C".
     */
    private static final String C = "(3345:2,20(4653:2,2,0,0,0;3(4536),3(4534),1,0;2,2,1;0,3(4453);1),42(6634:0,3(4566),4(4556),1,0;2,5(4656),6(5653),4(1444);2,5(5636),5(4654);0,4(1413);1),16(6646:0,0,1,0,0;0,3(1416),1,1;3(1416),3(5666),2;0,2;0),1;4(3453),40(3454:1,5(4535),6(1436),0,0;2,5(4356),6(3536),0;1,3(3564),6(3463);0,4(3456);1),46(3636:1,1,3(4364),6(4565),6(4544);0,5(4366),6(1565),6(4546);2,4(3466),3(3556);0,2;1),18(3656:0,1,1,1,1;0,3(5665),3(6446),3(4446);0,1,3(4646);0,1;0);5(3435x),20(3443:0,0,4(4355),0,0;0,3(3334),4(3356),0;1,2,4(3455);0,1;1),29(3636:0,1,3(5365),4(6445),4(1444);0,2,3(3565),4(4645);1,1,4(3446);0,2;0);0,12(3446x);1)";

    /**
     * Die Teil-Regel "D".
     */
    private static final String D = "(1213:1,4(1145),3(1415),0,0;0,6(1114x),7(2412x),0;2,4(1145),4(1145x);0,4(1114x);1)";

    /**
     * Die Teil-Regel "E".
     */
    private static final String E = "(1134:0,4(1312),24(3521:1,2,4(4612),0,0;0,3(3312),3(2423),0;2,2,3(4621);0,3(3321);1),38(2352:2,4(3226),4(5621),1,0;1,5(2223),7(6242),1;2,4(2323),4(2462);0,2;1),20(2525:1,2,1,0,0;0,3(2252),3(2262),0;2,2,2;0,3(2225);1);4(1341),34(1315:1,3(4151),4(4161),0,0;1,6(6451),6(1461),0;3(1351),3(1361),2;0,4(1113);1),32(1516:2,2,3(2145),0,4(2324);2,4(1661),4(1245),0;3(1561),3(1551),1;0,3(1511);1),22(1256:1,0,4(2524),2,0;0,2,4(5224),4(2224);2,0,0;0,2;1);4(1314),12(1315x),12(1235x);0,2;0)";

    /**
     * Die Teil-Regel "F".
     */
    private static final String F = "(1344:0,7(1335),41(3135:0,0,2,3(4623),2;0,3(5163),6(3423),6(3426);2,4(3156),6(1436);0,6(1536);1),44(3526:1,4(4652),6(6155),1,0;3(5623),7(1462),5(4552),1;3(1123),5(3552),3(4525);0,4(1145);1),16(5525:0,0,1,1,0;0,2,2,1;1,1,3(1516);0,3(1516);1);2,21(3145:1,3(1436),0,0,0;2,2,2,0;2,3(3134),2;0,3(3143);1),42(4524:1,1,7(1456),4(1635),3(1636);0,4(1356),5(4362),6(1336);2,2,3(3624);0,3(4424);1),34(3325:0,0,3(5642),4(1566),1;1,4(5362),4(5624),5(6624);2,4(3332),1;0,4(3326);1);3(1434),13(1415x),23(1415:0,0,2,4(3324),0;0,4(1546),4(1356),4(1136);0,2,3(1136);0,0;0);0,9(1335x);1)";

    /**
     * Die Teil-Regel "G".
     */
    private static final String G = "(1223:1,4(2145),3(4115),0,0;0,5(2145),6(4512),0;2,4(1245),3(1415);0,3(1145);1)";

    /**
     * Die Teil-Regel "H".
     */
    private static final String H = "(1234:2,16(1325:1,3(4152),3(4162),0,0;1,3(3126),2,0;1,1,1;0,0;0),20(1325:0,3(5162),1,0,0;0,2,4(4522),4(4622);0,3(5125),3(2116);0,0;1),6(2515),0;4(1323),21(1352:0,1,2,0,0;2,4(1623),2,0;1,3(1323),3(1462);0,2;1),16(2156x),12(1315x);2,6(3526),8(1536x);0,1;0)";

    /**
     * Die Teil-Regel "I".
     */
    private static final String I = "(1223:0,0,0,0,0;1,5(1145x),4(1114x),0;1,3(1415),4(1114x);0,2;0)";

    /**
     * Die vollständige Regel.
     */
    private static final String ALL = MAIN.replace("A", A).replace("B", B).replace("C", C).replace("D", D)
            .replace("E", E).replace("F", F).replace("G", G).replace("H", H).replace("I", I);

    /**
     * Die geparste vollständige Regel.
     */
    private static final IRule RULE;

    static {
        final CharStream input = new ANTLRStringStream(ALL);
        final Lexer lexer = new KnuthRuleLexer(input);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final KnuthRuleParser parser = new KnuthRuleParser(tokenStream);
        parser.setKnuthRuleFactory(new KnuthRuleFactory());
        try {
            RULE = parser.start();
        } catch (RecognitionException e) {
            throw new IllegalStateException("rule string is invalid", e);
        }
    }

    @Override
    public SpielKombination getGuess(final List<SpielKombination> candidates) {
        return RULE.getGuess(candidates);
    }

    @Override
    public IRule getRuleForResponse(final ErgebnisKombination response) {
        return RULE.getRuleForResponse(response);
    }
}
