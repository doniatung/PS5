public class Main{


  public static void main(String[] args){
    HashMap<String, HashMap<String, Integer>> observations = Training.fileToObv("text/simple-test-sentences", "simple-test-tags");
    HashMap<String, HashMap<String, Integer>> transitions = Training.fileToTrans("text/simple-test-tags");
    System.out.println(observations);
    System.out.println(transitions);
  }


}
