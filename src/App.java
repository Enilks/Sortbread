import java.util.Scanner;
import java.io.Console;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.lang.StringIndexOutOfBoundsException;

/*Idea: Save file to store sets and settings */

public class App {
    public static int set[];
    final static String menuOpts[] = {"Bubble Sort",
                                      "Cocktail Shaker Sort",
                                      "Comb Sort",
                                      "Selection Sort",
                                      "Selection Sort w/ Max",
                                      "Insertion Sort",
                                      "Bogosort",
                                      "Set Menu",
                                      "Quit Program"};
    private static int sortedSet[];

    public static void main(String[] args) throws Exception {
        System.out.println(); //opening spacer
        defaultSet(); //create default set
        Sorts.sortLog = new SortHistory(); //sort history log.
        boolean runMenu = true;
        Scanner scan = new Scanner(System.in);
        while(runMenu){
            System.out.println("\n--------[MENU]--------");
            for(int i=0; i<menuOpts.length; i++){System.out.println("   "+(i+1)+". "+menuOpts[i]);} //print menu opts
            System.out.print("SELECT: ");
            int s=0; // selection variable

            while(true) { // Get user input
                String input = scan.nextLine();

                if(input.charAt(0) == '/') { // Check for menu commands
                    menuCommands(input);
                    break;
                }

                try {
                    s = Integer.parseInt(input);

                    if (s < 1 || s > menuOpts.length) {
                        System.out.print(
                        "You must input an integer associated with one of the listed menu\n"+
                        "options (i.e. '1' for \""+menuOpts[0]+"\", or '"+(menuOpts.length)+"' for \"Quit Program\").\n"+
                        "ENTER MENU SELECTION: ");
                    }
                } catch (NumberFormatException e) {
                    // in the future, detecting if a command was attempted and explaining what went wrong could be helpful
                    System.out.print(
                        "You must input an integer associated with one of the listed menu\n"+
                        "options (i.e. '1' for \""+menuOpts[0]+"\", or '"+(menuOpts.length)+"' for \"Quit Program\").\n"+
                        "ENTER MENU SELECTION: ");
                        continue;
                }
                break;
            }

            try {
                switch (menuOpts[s-1]){ // Menu
                    /* I should be able to optimize my menu commands to run sorts so I just need to write 
                    * "sortComplete(Sorts.bubble());" or whatever, but right now I don't feel like doing that.
                    */
                    case "Bubble Sort":
                        sortedSet = Sorts.bubble();
                        sortComplete(sortedSet);
                        break;
                    case "Cocktail Shaker Sort":
                        sortedSet = Sorts.selection();
                        sortComplete(sortedSet);
                        break;
                    case "Selection Sort":
                        sortedSet = Sorts.selection();
                        sortComplete(sortedSet);
                        break;
                    case "Selection Sort w/ Max":
                        sortedSet = Sorts.selectionAlt();
                        sortComplete(sortedSet);
                        break;
                    case "Insertion Sort":
                        sortedSet = Sorts.insertion();
                        sortComplete(sortedSet);
                        break;
                    case "Bogosort":
                        sortedSet = Sorts.bogosort();
                        sortComplete(sortedSet);
                        break;
                    case "Set Menu":
                        setMenu();
                        break;
                    case "Quit Program":
                        runMenu = false;
                        break;
                    default:
                        System.out.println("Selection unavailable.");
                        petc();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }
        }
        scan.close();
        System.exit(0);
    }

    private static boolean menuCommands(String command) {
        String s = command.substring(1);
        //System.out.println(); // spacer
        switch(s) {
            case "view":
                viewSet();
                break;
            case "shuffle":
                shuffleSet();
                break;
            case "help":
            case "h":
                System.out.println("\nTo Add: Command Help Screen");
                break;
            default:
                System.out.println("Unrecognized command " + s + ". Type /help to view a list of commands");
                return false;
        }
        return true;
    }

    

    private static void setMenu(){
        String setMenuOpts[] = {"View Set","New Set","Shuffle Set","Sorting Stats","Back"};
        
        boolean runMenu = true;
        Scanner scan = new Scanner(System.in);
        while(runMenu){
            System.out.println("\n--------[MENU]--------");
            for(int i=0; i<setMenuOpts.length; i++){System.out.println("   "+(i+1)+". "+setMenuOpts[i]);} //print menu opts
            System.out.print("SELECT: ");
            int s; //selection variable
            while(true){ //Get user input
                try {
                    s = scan.nextInt();
                    while(s < 1 || s > setMenuOpts.length){
                        System.out.print("Invalid Selection. Try Again: ");
                        s = scan.nextInt();
                    }
                }catch (InputMismatchException e){
                    System.out.print(
                        "You must input an integer associated with one of the listed menu\n"+
                        "options (i.e. '1' for \""+setMenuOpts[0]+"\", or '"+(setMenuOpts.length)+"' to return).\n"+
                        "ENTER MENU SELECTION: ");
                        continue;
                }
                break;
            }
            
            switch (setMenuOpts[s-1]){ // Menu
                case "View Set":
                    viewSet();
                    break;
                case "New Set":
                    generateSet();
                    break;
                case "Shuffle Set":
                    System.out.println(); //spacer
                    shuffleSet(); //shuffle set
                    break;
                case "Sorting Stats":
                    Sorts.printSortStats();
                    break;
                case "Back":
                    runMenu = false;
                    break;
                default:
                    System.out.println("Selection unavailable.");
                    petc();
            }
        }
        //scan.close();
        return;
    }

    private static void viewSet() { // TODO - make fancier
        System.out.println("\nSet -- Length "+set.length+": "+Arrays.toString(set));
        petc();
    }

    private static void defaultSet(){
        System.out.print("Building set");
        set = new int[128];
        int dts = 128/3;
        for (int i=0; i<128; i++){
            if(i==dts || i==dts*2 || i==dts*3){
                System.out.print(".");
            }
            set[i] = i;
        }
        System.out.println("Done!");
        shuffleSet(); //shuffle arrays
    }

    private static void generateSet(){
        /* If I add settings, I want to add an option to generate sets with non-consecutive integers (so it doesn't go [1,2,3,4,5,6,...],
        but something closer to [4,9,43,49,66,123,534,...]). */
        Scanner scan = new Scanner(System.in);
        System.out.print("Quantity of values to generate: ");
        int length = scan.nextInt();
        
        while(length<4 || length>4096){ //deny invalid quantities
            if (length == 0){ //cancel func
                scan.close();
                return;
            }else if (length<4){
                System.out.println("Quantity cannot be less than 4.");
            }else if (length>4096){
                System.out.println("Quantity cannot exceed 4096.");
            }
            System.out.print("Enter new quantity (Enter 0 to Cancel): ");
            length = scan.nextInt();
        }
        int dts = length/3;
        System.out.print("Building set");
        set = new int[length];
        for (int i=0; i<length; i++){
            if(i==dts || i==dts*2 || i==dts*3){
                System.out.print(".");
            }
            set[i] = i;
        }
        System.out.println("Done!");
        shuffleSet(); //shuffle set
        System.out.print("\nDisplay Set? (y/n): ");
        if(getYN()){
            System.out.println("Set: "+Arrays.toString(set));
            petc();

        }
        System.out.println(); //spacer
        //scan.close();
    }

    private static void shuffleSet(){ // I stole half this code heheh oh well
        /* Idea: Custom "shuffles;" reverse the array, swap every other integer, etc. */
        Random ran = new Random();
        int dts = set.length/3;
        System.out.print("Shuffling");
        for(int i=0; i<set.length; i++){
            if(i==dts || i==dts*2 || i==dts*3){
                /*This if-statement condition could be improved buuut
                my % didn't work out the first time and I'm too lazy to
                put effort into such a pointless cosmetic thing*/
                System.out.print("."); //just some goofy cosmetic dots
            }
            int randomIndexToSwap = ran.nextInt(set.length);
            int temp = set[randomIndexToSwap];
            set[randomIndexToSwap] = set[i];
            set[i] = temp;
        }
        System.out.println("Done!");
    }

    private static void sortComplete(int[] srtd){
        System.out.print("Sort Complete! View Set? (y/n): ");
        if(getYN()){
            System.out.println("Sorted Set: "+Arrays.toString(srtd));
            Sorts.printSortStats();
        }
        System.out.print("Save Set? (y/n): ");
        if(getYN()){
            set = srtd;
            System.out.println("Saved!");
        }
    }
    
    public static boolean getYN(){ //add try loop to catch exceptions
        char c;
        while(true){
            Scanner scan = new Scanner(System.in);
            try{
                c = Character.toLowerCase((scan.nextLine()).charAt(0));
                while(c != 'y' && c != 'n'){
                    System.out.print("Not a valid input. Try again (y/n): ");
                    c = Character.toLowerCase((scan.nextLine()).charAt(0));
                }
            }catch(StringIndexOutOfBoundsException e){
                System.out.print("An Error Occured. Please try again (y/n): ");
                continue;
            }
            //scan.close();
            break;
        }
        if(c == 'y'){
            return true;
        }else{
            return false;
        }
    }

    public static void petc() {
        System.out.print("Press Enter to Continue");
        Console console = System.console();
        if (console != null){
            console.readLine(); // Wait for Enter
        }else{
            System.out.println("System console is not available. Press Enter to continue...");
            try {
                System.in.read(); // Wait for Enter
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
