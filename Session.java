import java.util.*;
import java.util.ArrayList;

public class Session implements Comparable<Session>{
    private Group group;
    private String name;
    private Timeslot timeslot;
    private Course course;
    private ArrayList<Timeblock> associatedTimeblocks = new ArrayList<>();

    Session(Group group,String name,String day,String timeslot){
        this.group = group;
        this.name = name;
        this.timeslot = Timeslot.stringToTimeslot(timeslot,day);
        course = group.getCourse();
    }

    public void addTimeblock(Timeblock tb){associatedTimeblocks.add(tb);}

    public Group getGroup(){return group;}
    public String getName(){return name;}
    public Timeslot getTimeslot(){return timeslot;}
    public Course getCourse(){return course;}


    public static boolean crashedSession(Session a, Session b){
        return Timeslot.crashedTimeslot(a.getTimeslot(),b.getTimeslot());
    }

    public static boolean equals(Session a,Session b){return (a.getName().equals(b.getName()) && 
        a.getCourse().getName().equals(b.getCourse().getName()) && 
        Timeslot.equals(a.getTimeslot(),b.getTimeslot()));}

    public int getNum(){
        String code =  name.split("")[0];
        switch (code) {
            case "C":
                return course.getLectureNum();
            case "T":
                return course.getTutorialNum();
            case "L":
                return course.getLabNum();
            case "S":
                return course.getSeminarNum();
        }
        return -1;
        
    }

    public void selfDestroy(){
        for (Timeblock tb : associatedTimeblocks){
            tb.deleteSession(this);
        }
    }

    public String toString(){return String.format("%s %s %s",timeslot.toString(),course.getName(),name);}

    @Override
    public int compareTo(Session a) {
        if (this.getNum() < a.getNum()){return -1;}
        if (this.getNum() == a.getNum()){return 0;}
        if (this.getNum() > a.getNum()){return 1;}
        return 1;
    }
   

    
}
