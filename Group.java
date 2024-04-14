import java.util.*;
import java.util.ArrayList;

public class Group {
    private Course course;
    private String commonCode;
    private ArrayList<Session> lecture = new ArrayList<>();
    private ArrayList<Session> tutorial = new ArrayList<>();;
    private ArrayList<Session> lab = new ArrayList<>();;
    private ArrayList<Session> seminiar = new ArrayList<>();;

    Group(Course course,String code){
        this.course = course;
        commonCode = code;
    }

    public Course getCourse(){return course;}
    public String getCommonCode(){return commonCode;}

    public Session addSession(String name,String day,String time){
        Session s = new Session(this, name, day, time);
        String code =  name.split("")[0];
        switch (code) {
            case "C":
                lecture.add(s);
                break;
            case "T":
                tutorial.add(s);
                break;
            case "L":
                lab.add(s);
                break;
            case "S":
                seminiar.add(s);
                break;
        }
        return s;
    }

    public int getNum(){
        int num = 100;
        if (lecture.size() != 0){num = lecture.size();}
        if (tutorial.size() != 0 && tutorial.size() < num){num = tutorial.size();}
        if (lab.size() != 0 && lab.size() < num){num = lab.size();}
        if (seminiar.size() != 0 && seminiar.size() < num){num = seminiar.size();}
        return num;
    }

    public void deleteSession(Session s){
        String code =  s.getName().split("")[0];
        switch (code) {
            case "C":
                lecture.remove(s);
                break;
            case "T":
                tutorial.remove(s);
                break;
            case "L":
                lab.remove(s);
                break;
            case "S":
                seminiar.remove(s);
                break;
        }
        s.selfDestroy();
        if(lecture.size()+tutorial.size()+lab.size()+seminiar.size() == 0){this.selfDestroy();}

    }

    public void selfDestroy(){course.deleteGroup(this);}
    


    

    
}
