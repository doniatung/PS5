/**
@author Donia Tung, CS10, Dartmouth Fall 2018
@author Ray Jones, CS10, Dartmouth Fall 2018

Class of methods that "train" the computer to recognize parts of speech, given training files
*/



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
  public static Map<K, Map<K, V>> fileToObv(String wordsPathName, String posPathName){
    BufferedReader wordsInput = null;
    BufferedReader posInput = null;
    Map<String, Map<String, Integer>> observations = new HashMap<String, HashMap<String, Integer>>();
    try{
      posInput = new BufferedReader(new FileReader(posPathName));
      wordsInput = new BufferedReader(new FileReader(wordsPathName));
      String posLine = posLine.readLine();
      String wordsLine = wordsInput.readLine();
      while (wordsLine != null && posLine != null){
        wordsLine = wordsLine.toLowerCase();
        String[] wordsPerLine = wordsLine.split(" ");
        String[] posPerLine = posLine.split(" ");
        for (int i = 0; i < wordsPerLine.length; i ++){
          Map<String, Integer> wordsAndCounts = new HashMap<String, Integer>();
          wordsAndCounts = observations.get(posPerLine[i]);
          if (observations.hasKey(posPerLine[i]) && wordsAndCounts.hasKey(wordsPerLine[i]))){
              wordsAndCounts.put(wordsPerLine[i], wordsAndCounts.get(wordsPerLine[i]) + 1);
          }
          else{
            wordsAndCounts.put(wordsPerLine[i], 1);
          }
          observations.put(posPerLine[i], wordsAndCounts);=
        }
        posLine = posInput.readLine();
        wordsLine = wordsInput.readLine();
      }
      return observations;
    }
    catch (Exception e){
      e.printStackTrace();
    }
    finally{
      wordsInput.close();
      posInput.close();
    }
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
  public static Map<K, Map<K, V>> fileToTrans(String pathName){
    BufferedReader input = null;
    Map<String, Map<String, Integer>> transitions = new HashMap<String, HashMap<String, Integer>>();
    try{
      input = new BufferedReader(new FileReader(pathName));
      String line = input.readLine();
      while (line!= null){
        line = line.toLowerCase();
        String[] states = line.split(" ");
        for (int i = 0; i < states.length -1; i ++){
          if (transitions.hasKey(states[i])){
            String nextState = transitions.get(states[i]).get(states[i+1]);
            if (transitions.get(states[i].hasKey(states[i+1]))){
              transitions.get(states[i]).put(nextState, transitions.get(nextState) + 1);
            }
            else{
              transitions.get(states[i]).put(nextState, 1);
            }
          }
          else{
            HashMap<String, Integer> val = new HashMap<String, Integer>();
            val.put(nextState, 1);
            transitions.put(states[i], val);
          }
        }
      }
      return transitions;
    }
    catch(Exception e){
      e.printStackTrace();
    }
    finally{
      input.close();
    }
  }

  public static Map<K, Map<K, V>> logProb(HashMap<String, HashMap<String, Integer>> map){
    double prob = 0.0;
    for (String key : map){

    }


  }













}
