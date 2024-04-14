import java.util.*;
import java.util.ArrayList;

public class Course {
    private String name;
    private ArrayList<Group> groupList = new ArrayList<>();
    private ArrayList<Session> lecture = new ArrayList<>();
    private ArrayList<Session> tutorial = new ArrayList<>();
    private ArrayList<Session> lab = new ArrayList<>();
    private ArrayList<Session> seminiar = new ArrayList<>();
    private ArrayList<Timeslot> sharedTimeslots = new ArrayList<>();
    private int lecturePerCourse = 1;
    private int tutorialPerCourse = 1;
    private int labPerCourse = 1;
    private int seminiarPerCourse = 1;

    Course(String name){this.name = name;}

    public String getName(){return name;}

    public Session addSession(String name,String day,String timeslot){
        String commonCode =  name.split("")[1];
        Session s = null;

        for (Group group : groupList){
            if (group.getCommonCode().equals(commonCode)){
                s = group.addSession(name,day,timeslot);
            }
        }

        if(s == null){
            Group newGroup = new Group(this,commonCode);
            groupList.add(newGroup);
            s = newGroup.addSession(name, day, timeslot);
        }

        String code =  name.split("")[0];
        int count = 1;
        switch (code) {
            case "C":
                for (Session temp : lecture){
                    if(temp.getName().equals(name)){
                        System.out.println(String.format("Session have two lecture %s %s",temp.getName(),name));
                        count +=1;
                    }
                    lecturePerCourse = count;
                }
                lecture.add(s);
                break;
            case "T":
                for (Session temp : tutorial){
                    if(temp.getName().equals(name)){
                        count +=1;
                    }
                    tutorialPerCourse = count;

                }
                tutorial.add(s);
                break;
            case "L":
                for (Session temp : lab){
                    if(temp.getName().equals(name)){
                        count +=1;
                    }
                    labPerCourse = count;

                }
                lab.add(s);
                break;
            case "S":
                for (Session temp : seminiar){
                    if(temp.getName().equals(name)){
                        count +=1;
                    }
                    seminiarPerCourse = count;
                }
                seminiar.add(s);
                break;
        }

        Timeslot newTimeslot = Timeslot.stringToTimeslot(timeslot,day);
        for(java.util.Iterator<Timeslot> iterator = sharedTimeslots.iterator();iterator.hasNext();){
            Timeslot savedTimeslot = iterator.next();
            if(Timeslot.crashedTimeslot(savedTimeslot,newTimeslot)){
                Timeslot longerTimeslot = Timeslot.longerTimeslot(savedTimeslot, newTimeslot);
                if (longerTimeslot != null && longerTimeslot.equals(newTimeslot)){
                    sharedTimeslots.remove(savedTimeslot);
                    sharedTimeslots.add(newTimeslot);
                    return s;
                }
            }
        }
        sharedTimeslots.add(newTimeslot);
        return s;
    }

    public void listGroup(){
        for (Group group : groupList){
            System.out.println(group.getCommonCode());
        }
    }

    public int getLectureNum(){
        int totalSessionCount = lecturePerCourse+labPerCourse+tutorialPerCourse+seminiarPerCourse;
        float averageSharedTimeslot = sharedTimeslots.size()/totalSessionCount;
        if (totalSessionCount == sharedTimeslots.size()){return 1;}

        if (lecture.size()>1){
            Session firstLecture = lecture.get(0);
            for (Session s : lecture){
                if (!Session.crashedSession(s, firstLecture)){
                    return lecture.size()/lecturePerCourse;
                }
            }
            return 1;
        }
        return lecture.size();
    }
    
    public int getTutorialNum(){
        int totalSessionCount = lecturePerCourse+labPerCourse+tutorialPerCourse+seminiarPerCourse;
        float averageSharedTimeslot = sharedTimeslots.size()/totalSessionCount;
        if (totalSessionCount == sharedTimeslots.size()){return 1;}

        if (tutorial.size()>1){
            Session firstTutorial = tutorial.get(0);
            for (Session s : tutorial){
                if (!Session.crashedSession(s, firstTutorial)){
                    return tutorial.size()/tutorialPerCourse;
                }
            }
            return 1;
        }

        return tutorial.size();
    }

    public int getLabNum(){
        int totalSessionCount = lecturePerCourse+labPerCourse+tutorialPerCourse+seminiarPerCourse;
        float averageSharedTimeslot = sharedTimeslots.size()/totalSessionCount;
        if (totalSessionCount == sharedTimeslots.size()){return 1;}

        if (lab.size()>1){
            Session firstLab = lab.get(0);
            for (Session s : lab){
                if (!Session.crashedSession(s, firstLab)){
                    return lab.size()/labPerCourse;
                }
            }
            return 1;
        }

        return lab.size();
    }

    public int getSeminarNum(){
        int totalSessionCount = lecturePerCourse+labPerCourse+tutorialPerCourse+seminiarPerCourse;
        float averageSharedTimeslot = sharedTimeslots.size()/totalSessionCount;
        if (totalSessionCount == sharedTimeslots.size()){return 1;}

        if (seminiar.size()>1){
            Session firstSeminiar = seminiar.get(0);
            for (Session s : seminiar){
                if (!Session.crashedSession(s, firstSeminiar)){
                    return seminiar.size()/seminiarPerCourse;
                }
            }
            return 1;
        }

        return seminiar.size();
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

        s.getGroup().deleteSession(s);
    }

    public void deleteGroup(Group g){groupList.remove(g);}

    public void addSessionNum(String code){
        switch (code) {
            case "C":
                lecturePerCourse += 1;
                break;
            case "T":
                tutorialPerCourse += 1;
                break;
            case "L":
                labPerCourse += 1;
                break;
            case "S":
                seminiarPerCourse +=1;
                break;
        }

    }


}
