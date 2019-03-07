import com.sbt.javaschool.lesson3.StringCollections;

public class Main {
    public static void main(String[] args){
        String fileName = "D://JavaSBTGit//SBT//Lesson3//src//input.txt";
        StringCollections myStringCollect = new StringCollections(fileName);
        //System.out.println(myStringCollect.countDifferentWords());
        //System.out.print(myStringCollect.sortedDifferentWords());
        System.out.print(myStringCollect.frequencyDifferentWords());
        //System.out.println(myStringCollect.reverseString());
        //System.out.println(myStringCollect.stringFromFile());
    }
}