/* ORGANIZATION CHANGE IDEA
 * This idea is based on the ArrayV project which has each sort as its own .java
 * file, and each sort is organized into folders by category. Each sort (like a
 * Bubble Sort or something) seems to extend and @override a class known in that
 * program as "runSort". If the number of sorts I have gets super big, I could
 * try doing something like that...
 */

import java.util.Random;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Sorts{
    private static int swaps,comparisons,shuffles=0,sortAniStep;
    public static String lastSort,sortDesc,sortType,sortAniFrames;
    private static long elapsedTime;
    public static int srtCount;
    public static SortHistory sortLog;

    public static int[] bubble(int arr[]){
        swaps = 0;
        comparisons=0;
        lastSort = "Bubble Sort";
        sortType = "Exchange";
        sortDesc = "The most basic sorting algorithm. (more).";
        srtCount++;
        long startTime = System.nanoTime();
        
        for(int i= arr.length -1; i>0; i--){
            boolean swapped=false;
            for(int j=0; j<i; j++){
                comparisons++;
                if(arr[j] > arr[j+1]){
                    arr = swap(arr,j,j+1);
                    swapped=true;
                }
            }
            if (!swapped){
                break;
            }
        }

        elapsedTime = System.nanoTime() - startTime; //calculate sorting time
        return arr;
    }

    public static int[] cocktailShaker(int[] arr){
        //aka bidirectional bubble sort or ripple sort
        swaps = 0;comparisons=0;
        lastSort = "Cocktail Shaker Sort";
        sortType = "Exchange";
        sortDesc = "Also known as a 'bidirectional bubble sort,' a\n"+
                   "cocktail sort is an extension of a bubble sort\n"+
                   "because it operates in two directions. It improves\n"+
                   "bubble sort by more quickly moving items to the\n"+
                   "beginning of the list, but it provides only\n"+
                   "marginal performance improvements. (Average\n"+
                   "complexity: O(n^2).";
        srtCount++;
        long startTime = System.nanoTime();

        boolean swapped = true;
        int end = arr.length-1, start=0;

        while(swapped){
            swapped = false;

            for(int i = start; i<end; ++i){
                comparisons++;
                if(arr[i] > arr[i+1]){
                    swap(arr, i, i+1);
                    swapped = true;
                }
            }
            if(!swapped){break;}
            swapped = false;
            end--;
            
            for (int i=end-1; i>=start; i--){
                comparisons++;
                if(arr[i] > arr[i+1]){
                    swap(arr, i, i+1);
                    swapped = true;
                }
            }
            start++;
        }

        elapsedTime = System.nanoTime() - startTime;
        return arr;

    }

    public static int[] comb(int[] arr){
        lastSort = "Comb Sort";
        sortType = "Exchange";
        sortDesc = "A comb sort is a simple sorting algorithm that repeatedly\n"+
                   "divides an array into smaller subarrays, sorts them, then\n"+
                   "combines the sorted subarrays back together to form a sorted\n"+
                   "array. Average complexity: O(n*log(n)).";
        swaps=0;comparisons=0;
        srtCount++;
        long startTime = System.nanoTime();

        //yet to build lol

        elapsedTime = System.nanoTime() - startTime;
        return null;
    }

    public static int[] selection(int[] arr){
        lastSort = "Selection Sort";
        sortType = "Selection";
        sortDesc = "...";
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

    public static int[] selectionAlt(int[] arr){ 
        /*some wierd things: This sort does HALF the comparisons normal selection sort does. 
        However, sometimes, this sort ends up being SLOWER. Additionally, it had 500 swaps 
        when sorting an array of 500 (it should only have to do about 499, which is what the 
        normal s-sort does) */
        lastSort = "Selection Sort w/ Max";
        sortType = "Selection";
        sortDesc = "A selection sort which also moves from the end to the front,\n"+
                   "tracking the maximum value.";
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

    public static int[] circle(int[] arr){

        boolean swapped = true;
        int lo = 0;
        int hi = arr.length;

        while(recCircle(arr,0,arr.length)){
            ;
        }

        return arr;
    }

    public static boolean recCircle(int[] arr, int low, int high){
        boolean swapped = false;

        if (low == high){return false;}

        /* Storing upper and lower bounds
         * of list to be used later in the
         * recursive case */
        int lo = low, hi = high;
        while (lo<hi){
            if(arr[lo]>arr[hi]){
                swap(arr,lo,hi);
                swapped = true;
            }
            lo++;
            hi--;
        }

        //in case array is odd sized:
        if(lo == hi){
            if(arr[lo]>arr[hi+1]){
                swap(arr,low,hi+1);
                swapped = true;
            }
        }

        int mid = (high - low)/2;
        boolean firstHalf = recCircle(arr,low,low+mid);
        boolean secondHalf = recCircle(arr,low+mid+1,high);

        return swapped || firstHalf || secondHalf;
    }

    public static int[] insertion(int[] arr){
        lastSort = "Insertion Sort";
        sortType = "Insertion";
        sortDesc = "...";
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

    public static int[] bogosort(int[] arr){
        lastSort = "Bogosort";
        sortType = "Transcendental";
        sortDesc = "The worst sorting algorithm. Bogosort checks if the\n"+
                   "set is sorted. If not, it shuffles the set, then\n"+
                   "checks again. This repeats until the set is fully\n"+
                   "sorted. Bogosort has an average performance of O(n*n!).";
        swaps=0;comparisons=0;sortAniStep=0;
        srtCount++;
        buildSortAni();
        long startTime = System.nanoTime();

        while(!isSorted(arr)){
            sortAnimation();
            shuffle(arr);
        }

        elapsedTime = System.nanoTime() - startTime;
        System.out.println();
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
        DecimalFormat df = new DecimalFormat("#,###");
        if(lastSort == null){
            System.out.println("\nNo Sort Found.");
            App.petc();
            return;
        }
        int size = App.set.length;
        System.out.println("\n-----[SORT STATS]-----\nSort: "+lastSort+"\nType: "+sortType+"\nDescription:\n"+sortDesc+"\nSize: "+df.format(size)+"\nSwaps: "+df.format(swaps)+"\nComparisons: "+df.format(comparisons));
        if(shuffles != 0){
            System.out.println("Shuffles: "+df.format(shuffles));
        }
        System.out.println("Time: "+etToString());
        sortLog.logStats(swaps, comparisons, etToString());
        shuffles = 0;
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
        if(s>=60){ //if the time amounts to 1 or more seconds, save as seconds
            int m = (int) s/60;
            s = s-(m*60);
            if(m<10){
                etString = ("0"+m+":");
            }else{
                etString = (m+":");
            }
            DecimalFormat df = new DecimalFormat("##.###");
            etString = etString + df.format(s);
        }else if(s>=1){
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
            if(sortAniStep%1000000 ==0){
                int i = sortAniStep/1000000;
                System.out.print(sortAniFrames.substring(i,i+1));
            }
            sortAniStep++;
            if(sortAniStep%1000000 == 0){
                int i = sortAniStep/1000000;
                if(i+1 >= sortAniFrames.length()){
                    sortAniStep=0;
                    System.out.println();
                }
            }            
        }catch (Exception e){
            //Todo: catch any errors if AniFrames is not built.
            System.out.println("An Error.");
            e.getStackTrace();
        }
    }

    private static void buildSortAni(){
        sortAniFrames = "S...O...R...T...I...N...G......";
        for(int i=0; i<lastSort.length(); i++){
            char c = Character.toUpperCase(lastSort.charAt(i));
            sortAniFrames = (sortAniFrames + Character.toString(c) + "...");
        }
    }
}
