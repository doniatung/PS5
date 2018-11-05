/**
   @author Donia Tung, CS10, Dartmouth Fall 2018
   @author Ray Jones, CS10, Dartmouth Fall 2018

   Class to do Viterbi tagging
*/
import java.util.*;
import java.io.*;

public class ViterbiTrace {

    /**
     * toVitLine
     *
     * Method to run the Viterbi tagging procedure on one line
     * @param lineIn string of the line that is being worked on 
     * @param trans map of transitions
     * @param obs map of observations
     * @return string of Viterbi tags
     */

    public static String toVitLine(String lineIn, HashMap<String, HashMap<String, Double>> trans, HashMap<String, HashMap<String, Double>> observs){
	// default start value
	String startVal = "#";
	// unseen penalty of -100
	double unseen = -100.0;
	//initialize blank line to be rerturned
	String lineOut = "";
	String lastTag = "";
	//set highest score to negative infinity so that it gets beaten certainly
	double highScore = Double.NEGATIVE_INFINITY;
	String[] wordArray = lineIn.split(" ");
	List<Map<String,String>> backtracking = new ArrayList<Map<String,String>>();
	Stack<String> printStrings = new Stack<String>();
	Set<String> previousStates = new TreeSet<>();
	previousStates.add(startVal);
	Map<String, Double> previousScores = new TreeMap<>();

	previousScores.put(startVal, 0.0);
	// iterate through the words
	for (int j = 0; j<wordArray.length; j++) {
	    Set<String> nextStates = new TreeSet<>();
	    Map<String,Double> nextScores = new TreeMap<>();
	    Map<String,String> backPoint = new TreeMap<>();
	    double score;
	    String currMin = "";
	    for (String st : previousStates){
		if(trans.containsKey(st) && !trans.get(st).isEmpty()){
		    for (String tr : trans.get(st).keySet()){
			nextStates.add(tr);
			if(observs.containsKey(tr) && observs.get(tr).containsKey(wordArray[j])){

			    score = previousScores.get(st) + trans.get(st).get(tr) + observs.get(tr).get(wordArray[j]);
			}
			else {
			    score = previousScores.get(st) + trans.get(st).get(tr) + unseen;
			}
			if(!nextScores.containsKey(tr) || score > nextScores.get(tr)){
			    nextScores.put(tr, score);
			    backPoint.put(tr, st);
			    if(backtracking.size() > j){
				backtracking.remove(j);
			    }
			    backtracking.add(backPoint);
			}
		    }
		}
	    }
	    // move on to next scores and states
	    previousScores = nextScores;
	    previousStates = nextStates;
	}
	// iterate through previous scores, finding the highest score among them
	for(String sco : previousScores.keySet()){
	    if(previousScores.get(sco) > highScore){
		highScore = previousScores.get(sco);
		lastTag = sco;
	    }
	}

	printStrings.push(lastTag);
	// go backwards through the wordArray
	for(int i = wordArray.length - 1; i > 0; i--){
	    printStrings.push(backtracking.get(i).get(printStrings.peek()));
	}
	//pop everything in the stack, if it contains anything
	while(!printStrings.isEmpty()){
	    lineOut += printStrings.pop() + " ";
	}

	// return the tagged line
	return lineOut;
    }


}
