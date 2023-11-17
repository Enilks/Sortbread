/* ORGANIZATION CHANGE IDEA
 * This idea is based on the ArrayV project which has each sort as its own .java
 * file, and each sort is organized into folders by category. Each sort (like a
 * Bubble Sort or something) seems to extend and @override a class known in that
 * program as "runSort". If the number of sorts I have gets super big, I could
 * try doing something like that...
 */

import java.util.Random;

public class Sorts{
    private static int swaps,comparisons,shuffles=0,sortAniStep;
    public static String lastSort,sortAniFrames;
    private static long elapsedTime;
    public static int srtCount;
    public static SortHistory sortLog;

    public static int[] bubble(){
        int[] set = App.set;
        swaps = 0;
        comparisons=0;
        lastSort = "Bubble Sort";
        srtCount++;
        long startTime = System.nanoTime();
        
        for(int i= set.length -1; i>0; i--){
            boolean swapped=false;
            for(int j=0; j<i; j++){
                comparisons++;
                if(set[j] > set[j+1]){
                    set = swap(set,j,j+1);
                    swapped=true;
                }
            }
            if (!swapped){
                break;
            }
        }

        elapsedTime = System.nanoTime() - startTime; //calculate sorting time
        return set;
    }

    public static int[] selection(){
        int[] arr = App.set;
        lastSort = "Selection Sort";
        swaps=0;comparisons=0;
        srtCount++;
        long startTime = System.nanoTime();

        for(int i=0; i<arr.length-1; i++){
            int minCell = i;
            for(int j = i+1; j<arr.length; j++){
                comparisons++;
                if(arr[j]<arr[minCell]){
                    minCell = j;
                }
            }
            arr = swap(arr,i,minCell); //could add an if in case i is the lowest cell so I don't have to swap
        }

        elapsedTime = System.nanoTime() - startTime; //calculate sorting time
        return arr;
    }

    public static int[] selectionAlt(){ 
        /*some wierd things: This sort does HALF the comparisons normal selection sort does. 
        However, sometimes, this sort ends up being SLOWER. Additionally, it had 500 swaps 
        when sorting an array of 500 (it should only have to do about 499, which is what the 
        normal s-sort does) */
        int[] arr = App.set;
        lastSort = "Selection Sort w/ Max";
        swaps=0;comparisons=0;
        srtCount++;
        long startTime = System.nanoTime();

        for(int i=0,j=(arr.length-1); i<j; i++,j--){ //i increases to check min while j decreases to check the max
            int minC = i, maxC = i;
            for(int k=i; k<=j; k++){
                comparisons++;
                if(arr[k]<arr[minC]){
                    minC = k;
                }else if(arr[k]>arr[maxC]){
                    maxC = k;
                }
            }
            swap(arr, i, minC); //swap the min value
            swap(arr, j, maxC); //swap the max value
        }

        elapsedTime = System.nanoTime() - startTime; //calculate sorting time
        return arr;
    }

    public static int[] insertion(){
        int[] arr = App.set;
        lastSort = "Insertion Sort";
        swaps=0;comparisons=0;
        srtCount++;
        long startTime = System.nanoTime();

        for(int i=1; i<arr.length; i++){
            comparisons++;
            for(int j=i; j>0 && arr[j]<arr[j-1]; j--){
                comparisons++;
                swap(arr, j, j-1);
            }
        }

        elapsedTime = System.nanoTime() - startTime; //calculate sorting time
        return arr;
    }

    public static int[] bogosort(){
        int[] arr = App.set;
        lastSort = "Bogosort";
        swaps=0;comparisons=0;sortAniStep=0;
        srtCount++;
        buildSortAni();
        long startTime = System.nanoTime();

        while(!isSorted(arr)){
            sortAnimation();
            shuffle(arr);
        }

        elapsedTime = System.nanoTime() - startTime;
        return arr;
    }


    //--- OTHER FUNCTIONS ---\\\
    private static int[] swap(int[] set, int loc1, int loc2){
        swaps++;
        int temp = set[loc1];
        set[loc1] = set[loc2];
        set[loc2] = temp;

        return set;
    }

    public static void printSortStats(){
        if(lastSort == null){
            System.out.println("\nNo Sort Found.");
            App.petc();
            return;
        }
        int size = App.set.length;
        System.out.println("\n-----[SORT STATS]-----\nSort type: "+lastSort+"\nSize: "+size+"\nSwaps: "+swaps+"\nComparisons: "+comparisons);
        if(shuffles != 0){
            System.out.println("Shuffles: "+shuffles);
        }
        System.out.println("Time: "+etToString());
        sortLog.logStats(swaps, comparisons, etToString());
        App.petc();
        System.out.println("--------------------");
    }

    /* private static String[] getStatsString(){
        String sortStatsString[] = {lastSort,Integer.toString(swaps),Integer.toString(comparisons),etToString()}; //might add timestamps later
        return sortStatsString;
    } */

    private static String etToString(){
        String etString;
        double s = (double) elapsedTime / 1_000_000_000; //convert nanoseconds to seconds
        if(s>=1){ //if the time amounts to 1 or more seconds, save as seconds
            etString = (s+" s");
        }else{
            s = (double) elapsedTime / 1_000_000;
            etString = (s+" ms");
        }
        return etString;
    }

    private static int[] shuffle(int[] array){
        shuffles++;
        Random ran = new Random();
        for(int i=0; i<array.length; i++){
            swaps++;
            int randomIndexToSwap = ran.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
        return array;
    }

    private static boolean isSorted(int[] array){
        for(int i=1; i<array.length; i++){
            comparisons++;
            if(array[i]<array[i-1]){return false;}
        }
        return true;
    }

    private static void sortAnimation(){
        try{
            System.out.print(sortAniFrames.substring(sortAniStep,sortAniStep+1));
            sortAniStep++;
            if((sortAniStep+1)<sortAniFrames.length()){
                sortAniStep++;
            }else{
                sortAniStep=0;
                System.out.println();
            }
        }catch (Exception e){
            //Todo: catch any errors if AniFrames is not built.
            System.out.println("An Error.");
            e.getStackTrace();
        }
    }

    private static void buildSortAni(){
        sortAniFrames = "S...O...R...T...I...N...G.........";
        for(int i=0; i<lastSort.length(); i++){
            char c = Character.toUpperCase(lastSort.charAt(i));
            sortAniFrames = (sortAniFrames + Character.toString(c) + "...");
        }
    }
}