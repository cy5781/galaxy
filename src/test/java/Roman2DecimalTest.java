import galaxy.logic.Roman2Decimal;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by ming on 2018/1/15.
 */
public class Roman2DecimalTest {


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    protected String romanNumeral, anotherRomanNumeral;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Before
    public void setUp() throws Exception {
        romanNumeral = "MCMXLIV";
        anotherRomanNumeral = "MMMM";
    }

    @Test
    /**
     * Test the scenario of Roman to Numeric Conversion.
     */
    public void testRomanToDecimal(){
        Roman2Decimal romanToDecimal = new Roman2Decimal();
        float numericValue = romanToDecimal.romanToDecimal(romanNumeral);
        Assert.assertEquals(1944.00, numericValue, 00.00);
    }

    @Test
    /**
     * Test the scenario where Non repeatable Roman Numeral repeats 4 times successively thereby throwing error.
     */
    public void testRomanToDecimalFailing(){
        Roman2Decimal romanToDecimal1 = new Roman2Decimal();
        float value = romanToDecimal1.romanToDecimal(anotherRomanNumeral);
        Assert.assertEquals("Error : Roman Numeral M cannot repeat 4 times successively", errContent.toString());
    }
}
