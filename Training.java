/**
@author Donia Tung, CS10, Dartmouth Fall 2018
@author Ray Jones, CS10, Dartmouth Fall 2018

Class of methods that "train" the computer to recognize parts of speech, given training files
*/

import java.util.*;
import java.io.*;

public class Training{

  //STATES = parts of speech tags
  //TRANSITIONS = tag to tag, with weights
  //OBSERVATIONS = tag to word, with weights

/**
* Method to create a Map keeping track of the number of times of each observation
*
* @return    a HashMap of observations in which each Key is a String of a word in the file,
*           and each Value is another Map, containing all of the parts of speech in the file as
*           Keys and the Integer of the number of times a given word is used as a given part of speech
*
* @param wordsPathName    location of file containing the total string of training sentence data.
* @param posPathName      location of file containing the parts of speech of every word in the sentence file
*/
  public static HashMap<String, HashMap<String, Double>> fileToObv(String wordsPathName, String posPathName) throws IOException{
    BufferedReader wordsInput = null;
    BufferedReader posInput = null;
    HashMap<String, HashMap<String, Double>> observations = new HashMap<String, HashMap<String, Double>>();
    try{
      posInput = new BufferedReader(new FileReader(posPathName));
      wordsInput = new BufferedReader(new FileReader(wordsPathName));
      String posLine = posInput.readLine();
      String wordsLine = wordsInput.readLine();
      while (wordsLine != null && posLine != null){
        wordsLine = wordsLine.toLowerCase();
        String[] wordsPerLine = wordsLine.split(" ");
        String[] posPerLine = posLine.split(" ");
        for (int i = 0; i < wordsPerLine.length; i ++){
          HashMap<String, Double> wordsAndCounts = new HashMap<String, Double>();
          wordsAndCounts = observations.get(posPerLine[i]);
          if (observations.containsKey(posPerLine[i]) && wordsAndCounts.containsKey(wordsPerLine[i]) ) {
              wordsAndCounts.put(wordsPerLine[i], wordsAndCounts.get(wordsPerLine[i]) + 1);
          }
          else{
            wordsAndCounts.put(wordsPerLine[i], 1.0);
          }
          observations.put(posPerLine[i], wordsAndCounts);
        }
        posLine = posInput.readLine();
        wordsLine = wordsInput.readLine();
      }
    }
    /*catch (Exception e){
      e.printStackTrace();
    }*/
    catch(IOException e){
      e.printStackTrace();
    }
    finally{
      wordsInput.close();
      posInput.close();
    }
    return observations;

  }

  /**
  * Method to create a Map keeping track of the number of times of each transition between parts of speech
  *
  * @return    a HashMap of transitions in which each Key is a part of speech in the file,
  *           and each Value is another Map, containing all of the parts of speech in the file as
  *           Keys and the Integer of the number of times a transition is made between the first part
  *           of speech and the second.
  *
  * @param pathName    location of file containing the parts of speech for the words in the training data
  */
  public static HashMap<String, HashMap<String, Double>> fileToTrans(String pathName) throws IOException{
    BufferedReader input = null;
    HashMap<String, HashMap<String, Double>> transitions = new HashMap<String, HashMap<String, Double>>();
    try{
      input = new BufferedReader(new FileReader(pathName));
      String line = input.readLine();
      while (line!= null){
        line = line.toLowerCase();
        String[] states = line.split(" ");
        for (int i = 0; i < states.length -1; i ++){
          String nextState = states[i+1];
          if (transitions.containsKey(states[i])){
            HashMap<String, Double> current = transitions.get(states[i]);
            if (current.containsKey(nextState)){
              current.put(nextState, current.get(nextState) + 1);
              transitions.put(states[i], current);
            }
            else{
              current.put(nextState, 1.0);
              transitions.put(states[i], current);
            }
          }
          else{
            HashMap<String, Double> val = new HashMap<String, Double>();
            val.put(nextState, 1.0);
            transitions.put(states[i], val);
          }
        }
      }
    }
    /*catch(Exception e){
      e.printStackTrace();
    }*/
    catch(IOException e){
      e.printStackTrace();
    }
    finally{
      input.close();
    }
    return transitions;
  }

  public static HashMap<String, HashMap<String, Double>> logProb(HashMap<String, HashMap<String, Double>> map){
    for (String key : map.keySet()){
      HashMap<String,Double> innerMap = map.get(key);
      int total = 0;
      for (String key2: innerMap.keySet()){
        total += innerMap.get(key2);
      }
      for (String key2: innerMap.keySet()){
        innerMap.put(key2, Math.log(innerMap.get(key2) / total));
      }
    }
    return map;
  }


}
