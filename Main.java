import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main{
    public static void main(String[] args){
        Timetable s = new Timetable();
        try {
            File file = new File("me.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String data = scanner.nextLine();
                s.inputSessionData(data);
            }
            s.tidy();
            s.listalldata();
            scanner.close();
            
        } catch (FileNotFoundException e) {
            // TODO: handle exception
        }
        




        



        
    }
}