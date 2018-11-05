/**
   @author Donia Tung, CS10, Dartmouth Fall 2018
   @author Ray Jones, CS10, Dartmouth Fall 2018

   Main Class to be run
*/

import java.util.*;
import java.io.*;


public class Main{
    
    /**
     * vitConsole
     *
     * Method to run the Viterbi tagging procedure on a user input from console
     * @param tr map of transitions
     * @param ob map of observations
     * @return tagged line
     */

    public static void VitConsole(HashMap<String, HashMap<String, Double>> tr, HashMap<String, HashMap<String, Double>> ob){
        Scanner scan = new Scanner(System.in);
        System.out.println("Type a sentence and view the tagged result:");
        String lineIn = scan.nextLine();
        String lineOut = ViterbiTrace.toVitLine(lineIn, tr, ob);
        System.out.println(lineOut);
    }
    
    /**
     * fileVit
     *
     * Method to run the Viterbi tagging procedure on a file, then return a new file that has been tagged. 
     * @param file name of input file to be tagged
     * @param trans map of transitions
     * @param obs map of observations
     * @return filename of the tagged file
     */
    public static String fileVit(String file, HashMap<String, HashMap<String, Double>> trans, HashMap<String, HashMap<String, Double>> obs){
        String tagsFile = null;
        BufferedReader input = null;
        BufferedWriter output = null;
        try{
            // remove .txt and add the new kind of file name
            tagsFile = file.substring(0,file.length()-4) + "_ViterbiTagged.txt";
            input = new BufferedReader(new FileReader(file));
            output = new BufferedWriter(new FileWriter(tagsFile));
            String lineIn = null;
            String tagsLine = null;
            // go line by line and perform viterbi tagging on each, writing to an output file
            while((lineIn = input.readLine()) != null){ 
                tagsLine = ViterbiTrace.toVitLine(lineIn, trans, obs);
                output.write(tagsLine +"\n");
            }

        }
        catch(Exception e){
            e.printStackTrace();

        }
        finally{
            // If the file exists, close it. Catch an exception if it doesn't. 
            try{
                input.close();
                output.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return tagsFile;
        }
    }

    /**
     * evalAccuracy
     *
     * Method to take in 2 tag files and print out the percentage of tags that overlap between them
     * @param fileA
     * @param fileB
     */

    public static void evalAccuracy(String fileA, String fileB){
        BufferedReader inputA = null;
        BufferedReader inputB = null;
        try{
            //twp inputs for two files
            inputA = new BufferedReader(new FileReader(fileA));
            inputB = new BufferedReader(new FileReader(fileB));
            String strA = null;
            String strB = null;
            String[] tagsA = null;
            String[] tagsB = null;
            // count of total tags
            int countAll = 0;
            // count of correct tags
            int correct = 0;

            //set strings for the active line
            strA = inputA.readLine();
            strB = inputB.readLine();
            while(strA != null){
                // set array of strings to all of the words in the string
                tagsA = strA.split(" ");
                tagsB = strB.split(" ");
                for(int i=0; i < tagsA.length; i++){ 
                    // if tag from A is the same as tag from B
                    if(tagsA[i].equals(tagsB[i])){
                        // add one to correct counter
                        correct++;
                    }
                }
                countAll += tagsA.length; 
                //move active string to next
                strA = inputA.readLine();
                strB = inputB.readLine();
            }
            // print the statistics

            //name of file being tested
            System.out.println("Testing: " + fileA);
            //total
            System.out.println(countAll+" total tags.");
            // percentage correct
            System.out.println((float)correct / countAll *100+ "% of the tags are correct.");

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            //try closing the two files, ancd catch an exception if something goes wrong
            try{
                inputA.close();
                inputB.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    /**
     * viterbiTest
     *
     * Method to take in a hard-coded string and test it
     * @param ob map of observations
     * @param tr map of transitions
     * @param str the given string to be tested
     */
    public static void viterbiTest(HashMap<String, HashMap<String, Double>> ob, HashMap<String, HashMap<String, Double>> tr, String str){
	System.out.println(ViterbiTrace.toVitLine(str, tr, ob));
    }


    public static void main(String[] args) throws IOException{
	HashMap<String, HashMap<String, Double>> observations = Training.fileToObv("texts/brown-train-sentences.txt", "texts/brown-train-tags.txt");
	HashMap<String, HashMap<String, Double>> transitions = Training.fileToTrans("texts/brown-train-tags.txt");
	observations = Training.logProb(observations);
	transitions = Training.logProb(transitions);
	//viterbiTest(observations, transitions, "cave is beautiful ."); // N V ADJ
	//viterbiTest(observations, transitions, "we work for trains ."); // PRO V P N
	//viterbiTest(observations, transitions, "my dog is beautiful ."); // PRO N V ADJ
	//viterbiTest(observations, transitions, "we watch in the night ."); // PRO V P DET N    
        String tests = "texts/brown-test-sentences.txt", tags = "texts/brown-test-tags.txt";
	//System.out.println(observations);
	//System.out.println(transitions);
        String taggedFile = fileVit(tests, transitions, observations); // tag the file
        System.out.println("The file is tagged and save as " + taggedFile + ".");
        evalAccuracy(tags, taggedFile); // print the percentages
        VitConsole(transitions, observations); // console input tags
    }


}
