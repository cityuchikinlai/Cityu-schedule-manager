import java.util.*;
import java.util.ArrayList;

public class Timeslot{
    private int startTime;
    private int endTime;
    private String day;

    Timeslot(int s,int e,String d){
        startTime = s;
        endTime = e;
        day = d;
    }

    public void setStartTime(int v){startTime = v;}

    public int getStartTime(){return startTime;}
    public int getEndTime(){return endTime;}
    public String getDay(){return day;}

    public String toString(){return String.format("%s %d:00-%d:50", day, startTime, endTime-1);}


    public static Timeslot stringToTimeslot(String s,String day){
        String[] arrOfStr = s.split("-");
        int Period_startTime = Integer.parseInt(arrOfStr[0].split (":")[0]);
        int Period_endTime = Integer.parseInt(arrOfStr[1].split (":")[0])+1;
        return new Timeslot(Period_startTime,Period_endTime,day);
    }

    public static boolean crashedTimeslot(Timeslot a,Timeslot b){
        if (!(a.getDay().equals(b.getDay()))){return false;}
        if (a.getStartTime() == b.getStartTime()){return true;}
        if (a.getEndTime() == b.getEndTime()){return true;}
        if (a.getStartTime() > b.getStartTime() && a.getStartTime() < b.getEndTime()){return true;}
        if (b.getStartTime() > a.getStartTime() && b.getStartTime() < b.getEndTime()){return true;}
        return false;
    }

    public static Timeslot longerTimeslot(Timeslot a,Timeslot b){
        int timeA = a.getEndTime()-a.getStartTime();
        int timeB = b.getEndTime()-b.getStartTime();
        if (timeB > timeA){return b;}
        if (timeA > timeB){return a;}
        return null;
    }

    public static boolean equals(Timeslot a, Timeslot b){return (a.getDay().equals(b.getDay()) &&
        a.getStartTime() == b.getStartTime() && a.getEndTime() == b.getEndTime());}
};