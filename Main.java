import java.util.*;
import java.io.*;


public class Main{

  public static void main(String[] args) throws IOException{
    HashMap<String, HashMap<String, Double>> observations = Training.fileToObv("texts/simple-train-sentences.txt", "texts/simple-train-tags.txt");
    HashMap<String, HashMap<String, Double>> transitions = Training.fileToTrans("texts/simple-train-tags.txt");
    System.out.println(observations);
    System.out.println(transitions);
  }


}
