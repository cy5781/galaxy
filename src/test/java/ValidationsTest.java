import galaxy.logic.Validations;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ming on 2018/1/15.
 */
public class ValidationsTest {

    @Test
    public void testSubtractionLogic(){
        float result = Validations.subtractionLogic(52f, 10f, 50f);
        Assert.assertEquals(42f, result, 00.00);
    }
}
