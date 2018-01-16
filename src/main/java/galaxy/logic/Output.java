package galaxy.logic;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ming on 2018/1/11.
 */
public class Output {

    public static LinkedHashMap<String, String> getTestResult() {
        return testResult;
    }

//    public static void setTestResult(LinkedHashMap<String, String> testResult) {
//        Output.testResult = testResult;
//    }

    private static LinkedHashMap<String, String> testResult = new LinkedHashMap<String, String>();

    public static void outputAnswers(LinkedHashMap processedAnswers){
        processedAnswers = TxtFileReader.getLastProcessedResult();
        testResult = processedAnswers;
        Iterator it = processedAnswers.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry token = (Map.Entry)it.next();
            String getQuestion=token.getKey().toString();
            System.out.println(getQuestion);
        }

    }
}
