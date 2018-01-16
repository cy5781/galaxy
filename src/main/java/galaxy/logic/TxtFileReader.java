package galaxy.logic;

import java.io.*;
import java.util.*;

/**
 * Created by ming on 2018/1/11.Use this class to get input from txt and process the content line by line.
 */
public class TxtFileReader {

    /**
     *  alienRomanMappings --> alien Roman number Mappings (glob is I ,prok is V ,pish is X ,tegj is L)
     *  RomanIntValueMappings --> Roman number vs arabic numbers
     *  questionAndAnswers ---> (how much and how many ...)
     */
    private static LinkedHashMap<String,String> alienRomanMappings = new LinkedHashMap<String,String>();
    private static LinkedHashMap<String,String> questionAndAnswers = new LinkedHashMap<String,String>();
    private static LinkedList<String> unknownValues = new LinkedList<String>();
    private static LinkedHashMap<String, Float> tokenDecimalValue = new LinkedHashMap<String, Float>();
    private static LinkedHashMap<String, Float> elementValueList = new LinkedHashMap<String, Float>();



    private static LinkedHashMap<String, String> lastProcessedResult = new LinkedHashMap<String, String>();

    public static LinkedHashMap<String, String> getLastProcessedResult() {
        return lastProcessedResult;
    }


    /**
     * txtReader read file and process according to it's condition.
     * @param filename
     */
    public static void txtReader(String filename) {
        try {
            BufferedReader bufferedReader =null;
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
            String getLine ;
            while((getLine=bufferedReader.readLine())!=null){
                processLine(getLine);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!"+e);
        } catch (IOException e) {
            System.err.println("IO Exception !"+e);
        }

    }

    /**
     * processline add file inputs to map and process according to different ways.
     * @param line
     */
    public static void processLine(String line){

        String arr[] = line.split("((?<=:)|(?=:))|( )");
        if(line.endsWith("?")){
            questionAndAnswers.put(line,"");
        }
        else if(arr.length==3&&arr[arr.length-2].equalsIgnoreCase("is")){
            alienRomanMappings.put(arr[0],arr[arr.length-1]);
        }
        else if(line.toLowerCase().endsWith("credits")){
            unknownValues.add(line);
        }

    }

    /**
     * alien word maps to real arabic numbers eg: glob =1.0
     *
     */
    public static void AlienRoman2Decimal(){
        Iterator it = alienRomanMappings.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry alian2roman = (Map.Entry)it.next();
            alian2roman.getValue().toString().toCharArray();
            char[] romans=alian2roman.getValue().toString().toCharArray();
            char single_roman =romans[0];
            switch (single_roman){
                case 'I':
                    tokenDecimalValue.put(alian2roman.getKey().toString(),(float)1.0);
                    break;
                case 'V':
                    tokenDecimalValue.put(alian2roman.getKey().toString(),(float)5.0);
                    break;
                case 'X':
                    tokenDecimalValue.put(alian2roman.getKey().toString(),(float)10.0);
                    break;
                case 'L':
                    tokenDecimalValue.put(alian2roman.getKey().toString(),(float)50.0);
                    break;
                case 'C':
                    tokenDecimalValue.put(alian2roman.getKey().toString(),(float)100.0);
                    break;
                case 'D':
                    tokenDecimalValue.put(alian2roman.getKey().toString(),(float)500.0);
                    break;
                case 'M':
                    tokenDecimalValue.put(alian2roman.getKey().toString(),(float)1000.0);
                    break;
            }

        }

        mapMissingElements();
    }

    /**
     * Finds the value of unknown elements like Silver Gold Iron
     */
    private static void mapMissingElements(){
        for (int z = 0; z < unknownValues.size(); z++) {
            calculateUnknownElements(unknownValues.get(z));
        }
        calculateHowMuch();
    }

    /**
     * Finds the value of unknown elements like Silver Gold Iron
     * @param unknownElements
     */
    public static void calculateUnknownElements(String unknownElements){
        String array[] = unknownElements.split("((?<=:)|(?=:))|( )");
        int splitIndex = 0;
        int creditValue = 0;
        String element= null;
        String[] valueofElement = null;
      //  String[] beforeMetals =null;
        float knownElementPrice = 0;
        StringBuffer to_roman_numerals = new StringBuffer();

        for (int i = 0; i < array.length; i++) {
            if(array[i].toLowerCase().equals("credits")){
                creditValue = Integer.parseInt(array[i-1]);
            }
            if(array[i].toLowerCase().equals("is")){
                splitIndex = i-1;
                element = array[i-1];
            }
            valueofElement = Arrays.copyOfRange(array, 0, splitIndex);
         }
        //mapping alien language to
        for(int z=0;z<valueofElement.length;z++){

            to_roman_numerals.append(alienRomanMappings.get(valueofElement[z]));
        }

        knownElementPrice=new Roman2Decimal().romanToDecimal(to_roman_numerals.toString());

        elementValueList.put(element,(float)creditValue/knownElementPrice);

    }

    public static void calculateHowMuch(){
       // String[] nameofElements = null;
        int splitIndex = 0;
        Iterator questionIt = questionAndAnswers.entrySet().iterator();
       // float howmuchValue =0;//use to store how much question result
        StringBuffer answer_front = new StringBuffer();
        StringBuffer roman_numerals = new StringBuffer();
        float howMuchResult=0;
        while (questionIt.hasNext()){
            Map.Entry token = (Map.Entry)questionIt.next();
            String getQuestion=token.getKey().toString();
            if(getQuestion.contains("much is")){

                String howmuch_array[] = getQuestion.split("((?<=:)|(?=:))|( )");
                for(int i=0;i<howmuch_array.length;i++){

                    if (howmuch_array[i].equals("is")){
                        splitIndex = i+1;
                    }

                }

                //generate the front sentence for how much questions and generate
                for(int k=splitIndex;k<howmuch_array.length-1;k++){
                    answer_front.append(howmuch_array[k]+" ");
                    roman_numerals.append(alienRomanMappings.get(howmuch_array[k]));
                }

                 howMuchResult =new Roman2Decimal().romanToDecimal(roman_numerals.toString());
                 //processedResult.add(answer_front.toString()+"is "+howMuchResult);
                 lastProcessedResult.put(answer_front.toString()+"is "+String.valueOf(howMuchResult),String.valueOf(howMuchResult));

            }
            else if(getQuestion.contains("many Credits")){
                calculateCredits(getQuestion);
            }
            else if(getQuestion.contains("much is")||!getQuestion.contains("many Credits")){
                lastProcessedResult.put("I have no idea what you are talking about",String.valueOf(0));
            }

        }
    }

    public static void calculateCredits(String getQuestion){
        String array[] = getQuestion.split("((?<=:)|(?=:))|( )");
        int splitIndex = 0;
        String cutArray[]= null;
        String toRomanArray[]= null;
        StringBuffer credits_answer_start=new StringBuffer();
        StringBuffer toRomanBuffer= new StringBuffer();
        float rommanValues = 0;
        float metalValues = 0;
        for (int i = 0; i < array.length; i++) {

            if(array[i].toLowerCase().equals("is")){
                splitIndex = i+1;
            }
            cutArray = Arrays.copyOfRange(array, splitIndex, array.length-1);
        }

        for(int j=0;j<cutArray.length;j++){
            credits_answer_start.append(cutArray[j]+" ");
            if(j==cutArray.length-1){
                //metalValues = elementValueList.get(cutArray[0]);
                if(cutArray[j].equals("Silver")){
                    metalValues=elementValueList.get(cutArray[j]);
                }
                else if(cutArray[j].equals("Gold")){
                    metalValues=elementValueList.get(cutArray[j]);
                }
                else if(cutArray[j].equals("Iron")){
                    metalValues= elementValueList.get(cutArray[j]);
                }
            }
        }
        toRomanArray = Arrays.copyOfRange(cutArray, 0, cutArray.length-1);
        for(int j=0;j<toRomanArray.length;j++){
            toRomanBuffer.append(alienRomanMappings.get(toRomanArray[j]));
        }
        rommanValues =new Roman2Decimal().romanToDecimal(toRomanBuffer.toString());
        float lastCredits = rommanValues*metalValues;
        lastProcessedResult.put(credits_answer_start +"is "+lastCredits + " Credits",lastCredits + " Credits");
    }

}
