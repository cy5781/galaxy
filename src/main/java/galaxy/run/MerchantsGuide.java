package galaxy.run;

import galaxy.logic.Output;
import galaxy.logic.TxtFileReader;

import java.util.LinkedHashMap;

/**
 * Created by ming on 2018/1/11.Merchants' Guide to Galaxy
 */

public class MerchantsGuide {

    private static LinkedHashMap<String, String> lastProcessedResult = new LinkedHashMap<String, String>();

    public static void main(String args[]){
        TxtFileReader.txtReader("src/main/resources/input.txt");
        TxtFileReader.AlienRoman2Decimal();
        lastProcessedResult = TxtFileReader.getLastProcessedResult();
        Output.outputAnswers(lastProcessedResult);

    }

}
