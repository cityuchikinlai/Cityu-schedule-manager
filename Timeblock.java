import java.util.ArrayList;
import java.util.PriorityQueue;

public class Timeblock {
    private Timetable timetable;
    private Timeslot timeslot;
    private PriorityQueue<Session> queue  = new PriorityQueue<>();

    Timeblock(Timetable tb,String day,String timeslot){
        timetable = tb;
        this.timeslot = Timeslot.stringToTimeslot(timeslot,day);
    }

    public Timeslot getTimeslot(){return timeslot;}

    public void addSession(Session s){
        queue.add(s);
    }

    public void deleteSession(Session session){
        queue.remove(session);
    }

    public void deleteAllSession(){
        java.util.Iterator<Session> iterator = queue.iterator();
        while (iterator.hasNext()){
            Session s = iterator.next();
            iterator.remove();
            s.getCourse().deleteSession(s);
        }
    }

    public boolean checkAvailability(){
        if (queue.size() == 0){
            timetable.deleteTimeblock(this);
            return true;
        }

        if (queue.peek().getNum() == 1){
            ArrayList<Session> reservedSessions = new ArrayList<>();
            Session temp = queue.poll();
            reservedSessions.add(temp);
            for (java.util.Iterator<Session> iterator = queue.iterator();iterator.hasNext(); ){
                Session s = iterator.next();
                if (s.getCourse().getName().equals(temp.getCourse().getName())){
                    reservedSessions.add(s);
                    iterator.remove();
                }
            }

            if(queue.size() != 0){
                if (queue.peek().getNum() ==1){
                    System.out.println(String.format("Error! %s %s and %s %s is crashed at %s", temp.getCourse().getName(),temp.getName(),
                    queue.peek().getCourse().getName(),queue.peek().getName(),timeslot.toString()));
                }else {
                    this.deleteAllSession();
                }
            } 

            for (Session s : reservedSessions){
                this.addSession(s);
            }
            return false;
        } else{
            return true;
        }
    }

    public void listSession(){
        for (Session s : queue){
            System.out.println(s.toString());
        }
    }

    
}
