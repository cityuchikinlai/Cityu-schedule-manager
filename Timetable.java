import java.util.ArrayList;



public class Timetable {
    private ArrayList<Timeblock> Occuiped = new ArrayList<>();
    private ArrayList<Timeblock> allTimeblocks = new ArrayList<>();
    private ArrayList<Course> allCourses = new ArrayList<>();

    public void inputSessionData(String s){
        String courseName = s.split(" ")[0];
        String sessionName = s.split(" ")[1];
        String day = s.split(" ")[2];
        String timeslot = s.split(" ")[3];
        int startTime = Timeslot.stringToTimeslot(timeslot,day).getStartTime();
        int endTime = Timeslot.stringToTimeslot(timeslot,day).getEndTime();

        Course course = null;
        if (allCourses.size() != 0){
            for (Course c : allCourses){
                if (c.getName().equals(courseName)){
                    course = c;
                    break;
                }
            }
        }
        if (course == null){
            course = new Course(courseName);
            allCourses.add(course);
        }

        Session session = course.addSession(sessionName,day,timeslot);

        for (int i = startTime;i<endTime;i++){
            Timeblock timeblock = null;
            String splitTimeslotStr = String.format("%d:00-%d:50", i,i);
            Timeslot splitTimeslot = Timeslot.stringToTimeslot(splitTimeslotStr, day);
            if (allTimeblocks.size() != 0){
                for (Timeblock tb : allTimeblocks){   
                    if(Timeslot.equals(splitTimeslot,tb.getTimeslot()) ){
                        timeblock = tb;
                        break;
                    }
                }
            }
            if (timeblock == null){
                timeblock = new Timeblock(this,day, splitTimeslotStr);
                allTimeblocks.add(timeblock);
            }

            
            timeblock.addSession(session);
            session.addTimeblock(timeblock);

        }
        
    }

    public void tidy(){
        boolean alltidy;
        do{
            alltidy = true;
            
            for (java.util.Iterator<Timeblock> iterator = allTimeblocks.iterator();iterator.hasNext(); ){
                Timeblock tb = iterator.next();
                if(!tb.checkAvailability()){
                    alltidy = false;
                    Occuiped.add(tb);
                    iterator.remove();
                }
                
            }
        }while(!alltidy);

        
    }

    public void listalldata(){
        for (Timeblock tb : allTimeblocks){
            System.out.println(String.format("Timeblock at %s",tb.getTimeslot().toString()));
        }
        for (Course c : allCourses){
            System.out.println(c.getName());
        }
        for (Timeblock tb : Occuiped){
            System.out.println(String.format("Occupied Timeblock at %s",tb.getTimeslot().toString()));
            tb.listSession();
        }
    }

    public void deleteTimeblock(Timeblock tb){
        allTimeblocks.remove(tb);
        Occuiped.remove(tb);
    }
    
    
}
