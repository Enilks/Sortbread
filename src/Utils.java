public final class Utils { //may need to remove final, idk
    public Utils(){
        //default constructor
    }

    public String timeFormat(double seconds){ //Unfinished?
        String timeString = "";
        double s = seconds; //yes im that lazy i dont want to type out all 7 letters
        if(s>=31_536_000){ //year
            double yr = s/31_536_000;
            s = s % yr;
            timeString += (yr+" years, ");
        }
        if(s>=2_628_288){//month
            double mon = s/2_628_288;
            s = s % mon;
            timeString += (mon+" months, ");
        }
        if(s>86_400){//day
            double d = s/86_400;
            s = s % d;
            timeString += (d+" days");
        }
        if(s>3600){
            double h = s/3600;
            s = s % h;
            timeString += (h+":");
        }

        return timeString;
    }
}
