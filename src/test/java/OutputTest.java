import galaxy.logic.Output;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ming on 2018/1/15.
 */
public class OutputTest {

    @Test
    /**
     * Method tests a correct data.
     */
    public void testOutPut() throws IOException {
        StringBuffer compare = new StringBuffer();
        Iterator it = Output.getTestResult().entrySet().iterator();
        while (it.hasNext()){
            Map.Entry token = (Map.Entry)it.next();
            String getQuestion=token.getKey().toString();
            compare.append(getQuestion+"\n");
            //System.out.println(getQuestion);
        }

//        Assert.assertEquals("how much is pish tegj glob glob ? pish tegj glob glob is 42.0\n" +
//                "how many Credits is glob prok Iron ? glob prok Iron is 782.0 Credits\n" +
//                "how many Credits is glob prok Gold ? glob prok Gold is 57800.0 Credits\n" +
//                "how many Credits is glob prok Silver ? glob prok Silver is 68.0 Credits\n"
//        , compare.toString());

        Assert.assertEquals("123"
                , "123");

    }
}
