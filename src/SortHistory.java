import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SortHistory {
    private File sortLog;
    private String filePath;
    
    /* IDEAS
     * Make this class so it EXTENDS sorts? Maybe that will be better.
     * Make this a more general log - the log should include stuff like the creation of sets, maybe even including the set?
     */

    public SortHistory(){
        try {
            sortLog = new File("src/resources/Sorting-History.txt");
            filePath = sortLog.getAbsolutePath();
            if (sortLog.createNewFile()) {
                System.out.println("Sorting log created. Located in "+sortLog.getAbsolutePath());
            }else{
                System.out.println("A sorting log already exists. ("+sortLog.getAbsolutePath()+")");
                //add something to make it so that either new stuff is just appended or it creates a file with an altered name, i.e. Sorting-History-1 or smth.
            }
            filePath = sortLog.getAbsolutePath();
            //FileWriter write = new FileWriter(filePath,true);
            //write.write("---TEST---");
        } catch (IOException e) {
            System.out.println("An Error Occured (IO Exception)");
            e.printStackTrace();
        }
    }

    public void logStats(int swaps, int comparisons, String etString){
        try {
            //BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));
            FileWriter writer = new FileWriter(filePath, true); //creates a file writer which will append to the file
            String s = ("Sort #: "+Sorts.srtCount+"  --  "+Sorts.lastSort+
                        "\nSwaps: "+swaps+
                        "\nComparisons: "+comparisons+
                        "\nTime: "+etString+
                        "\n------------------------------------\n\n");
            writer.write(s);
            //file write complete
        }catch (IOException e){
            System.out.println("An Error Occured (IO Exception)");
            e.getStackTrace();
        }catch (Exception e){
            System.out.println("Exception Triggered!");
            //deal with whatever other exceptions might occur
        }
    }
}
