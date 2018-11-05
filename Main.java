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

  public static void main(String[] args) throws IOException{
    HashMap<String, HashMap<String, Double>> observations = Training.fileToObv("texts/simple-train-sentences.txt", "texts/simple-train-tags.txt");
    HashMap<String, HashMap<String, Double>> transitions = Training.fileToTrans("texts/simple-train-tags.txt");
    observations = Training.logProb(observations);
    transitions = Training.logProb(transitions);
    //System.out.println(observations);
    //System.out.println(transitions);
    VitConsole(observations, transitions);

  }


}
