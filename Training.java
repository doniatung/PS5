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
* @param wordsPathName    location of file containing the total string of training sentence data.
* @param posPathName      location of file containing the parts of speech of every word in the sentence file
* @return    a HashMap of observations in which each Key is a String of a word in the file,
*           and each Value is another Map, containing all of the parts of speech in the file as
*           Keys and the Integer of the number of times a given word is used as a given part of speech
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
        //System.out.println(observations);
        wordsLine = wordsLine.toLowerCase();
        String[] wordsPerLine = wordsLine.split(" ");
        String[] posPerLine = posLine.split(" ");
        //System.out.println(wordsPerLine.length-1);
        if (observations.containsKey("#")){
          HashMap<String, Double> wnc = new HashMap<String, Double>();
          wnc = observations.get("#");
          if (wnc.containsKey(wordsPerLine[0])){
            Double num = wnc.get(wordsPerLine[0]) +1;
            wnc.put(wordsPerLine[0], num);
            observations.put("#", wnc);
          }
          else{
            wnc.put(wordsPerLine[0], 1.0);
            observations.put("#", wnc);
          }
        }
        else{
          HashMap<String, Double> map = new HashMap<String, Double>();
          map.put(wordsPerLine[0], 1.0);
          observations.put("#", map);
        }
        for (int i = 0; i < wordsPerLine.length-1; i ++){
          HashMap<String, Double> wordsAndCounts = new HashMap<String, Double>();
          //System.out.println(wordsPerLine[i]);
          //System.out.println(posPerLine[i]);
          //System.out.println(" pos per line: " + observations.get(posPerLine[i]));
          if (observations.containsKey(posPerLine[i])){
            //System.out.println("already there:" + observations.get(posPerLine[i]));
            wordsAndCounts = observations.get(posPerLine[i]);

            if (observations.get(posPerLine[i]).containsKey(wordsPerLine[i])){
              //System.out.println("words and counts before add: " + wordsAndCounts);
              Double num = wordsAndCounts.get(wordsPerLine[i]) + 1;
              //System.out.println(num);
              wordsAndCounts.put(wordsPerLine[i], num);
            }
            else{
              wordsAndCounts.put(wordsPerLine[i], 1.0);
              //System.out.println("with the new word: " + wordsAndCounts);
            }
          }
          else{
            //System.out.println("i : " + i);
            //System.out.println("the given word: " + wordsPerLine[i]);
            //System.out.println("the list: " + wordsAndCounts);
            wordsAndCounts.put(wordsPerLine[i], 1.0);
          }
          //System.out.println(wordsAndCounts);
          //System.out.println(observations);
          observations.put(posPerLine[i], wordsAndCounts);
          //System.out.println(" pos per line after: " + observations.get(posPerLine[i]));
        }
        posLine = posInput.readLine();
        wordsLine = wordsInput.readLine();
        //System.out.println(posLine);
        //System.out.println(wordsLine);
        //System.out.println("current state of observations: " + observations);
        //System.out.println("------------------------------------------");
        }
      }

    /*catch (Exception e){
      e.printStackTrace();
    }*/
    catch (IOException e){
      e.printStackTrace();
    }
    finally{
      wordsInput.close();
      posInput.close();
    }
    System.out.println(observations);
    return observations;

  }

  /**
  * Method to create a Map keeping track of the number of times of each transition between parts of speech
  *
  * @param pathName    location of file containing the parts of speech for the words in the training data
  * @return    a HashMap of transitions in which each Key is a part of speech in the file,
  *           and each Value is another Map, containing all of the parts of speech in the file as
  *           Keys and the Integer of the number of times a transition is made between the first part
  *           of speech and the second.
  */
  public static HashMap<String, HashMap<String, Double>> fileToTrans(String pathName) throws IOException{
    BufferedReader input = null;
    HashMap<String, HashMap<String, Double>> transitions = new HashMap<String, HashMap<String, Double>>();

    try{
      input = new BufferedReader(new FileReader(pathName));
      String line = input.readLine();
      while (line != null){
        line = line.toLowerCase();
        String[] states = line.split(" ");
        if (transitions.containsKey("#")){
              HashMap<String, Double> current = transitions.get("#");
            if (current.containsKey(states[0])){
              current.put(states[0], current.get(states[0]) + 1);
              transitions.put("#", current);
            }
            else{
              current.put(states[0], 1.0);
              transitions.put("#", current);
            }
          }
          else{
            HashMap<String, Double> val = new HashMap<String, Double>();
            val.put(states[0], 1.0);
            transitions.put("#", val);
          }
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
        line = input.readLine();
      }
    }
    /*catch(Exception e){
      e.printStackTrace();
    }*/
    catch (IOException e){
      e.printStackTrace();
    }
    finally{
      input.close();
    }
    return transitions;
  }

  /**
  * Method to convert what was just a simple count of instances of an observation or transitions
  * into a log base e probability
  *
  * @param map    the map of information to be converted into log probability
  * @return     the original map, with integers converted to log probability values
\  */
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
