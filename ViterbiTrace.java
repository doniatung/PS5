import java.util.*;
import java.io.*;

public class ViterbiTrace {


	public static String toVitLine(String lineIn, HashMap<String, HashMap<String, Double>> trans, HashMap<String, HashMap<String, Double>> observs){
		String startVal = "#";		
		double unseen = -10.0;
		String lineOut = "";
		String lastTag = "";
		double highScore = Double.NEGATIVE_INFINITY;

		Stack<String> printStrings = new Stack<String>();

		List<Map<String,String>> backtracking = new ArrayList<Map<String,String>>();
		String[] wordArray = lineIn.split(" ");
		//System.out.println(wordArray[2]);

		Set<String> previousStates = new TreeSet<>();
		previousStates.add(startVal);
		Map<String, Double> previousScores = new TreeMap<>();

		previousScores.put(startVal, 0.0);
//	System.out.println(wordArray.length);
		for (int j = 0; j<wordArray.length; j++) {
			Set<String> nextStates = new TreeSet<>();
			Map<String,Double> nextScores = new TreeMap<>();
			Map<String,String> backPoint = new TreeMap<>();
			double score;
//			System.out.println(previousStates);
			for (String st : previousStates){
//						System.out.println(st);

//						System.out.println("before1");
				if(trans.containsKey(st) && !trans.get(st).isEmpty()){
//						System.out.println("before2");

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
						//System.out.println(backPoint);
//						System.out.println("before3");
						if(backtracking.size() > j){
							backtracking.remove(j);
						}

						backtracking.add(backPoint);
//						System.out.println(backtracking);
//						System.out.println("after");
						
					}

					}
				}
			}
previousScores = nextScores;
previousStates = nextStates;

		}
for(String sco : previousScores.keySet()){
	if(previousScores.get(sco) > highScore){
		highScore = previousScores.get(sco);
		lastTag = sco;
	}
}

printStrings.push(lastTag);
for(int i = wordArray.length - 1; i > 0; i--){
	printStrings.push(backtracking.get(i).get(printStrings.peek()));
}

while(!printStrings.isEmpty()){
	lineOut += printStrings.pop() + " ";
}


		return lineOut;
	}


}