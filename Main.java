import java.util.*;
import java.io.*;


public class Main{

    public static void VitConsole(HashMap<String, HashMap<String, Double>> ob, HashMap<String, HashMap<String, Double>> tr){
        Scanner scan = new Scanner(System.in);
        System.out.println("Type a sentence and view the tagged result:");
        String lineIn = scan.nextLine();
        String lineOut = ViterbiTrace.toVitLine(lineIn, tr, ob);
        System.out.println(lineOut);
    }

    public static void viterbiTest(HashMap<String, HashMap<String, Double>> ob, HashMap<String, HashMap<String, Double>> tr, String str){
      System.out.println(ViterbiTrace.toVitLine(str, tr, ob));
    }

  public static void main(String[] args) throws IOException{
    HashMap<String, HashMap<String, Double>> observations = Training.fileToObv("texts/simple-train-sentences.txt", "texts/simple-train-tags.txt");
    HashMap<String, HashMap<String, Double>> transitions = Training.fileToTrans("texts/simple-train-tags.txt");
    observations = Training.logProb(observations);
    transitions = Training.logProb(transitions);
    //viterbiTest(observations, transitions, "cave is beautiful ."); // N V ADJ
    //viterbiTest(observations, transitions, "we work for trains ."); // PRO V P N
    //viterbiTest(observations, transitions, "my dog is beautiful ."); // PRO N V ADJ
    //viterbiTest(observations, transitions, "we watch in the night ."); // PRO V P DET N

    VitConsole(observations, transitions);

  }


}
