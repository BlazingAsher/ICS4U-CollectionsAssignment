import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Question3 {
    public static void main(String[] args) throws IOException {
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("picks.txt")));
        Map<String, TreeSet<StudentName>> studentInfo = new HashMap<>();

        while(inFile.hasNextLine()){
            String[] suggestion = inFile.nextLine().split(",");
            TreeSet<StudentName> temp = studentInfo.getOrDefault(suggestion[2], new TreeSet<>());

            temp.add(new StudentName(suggestion[0], suggestion[1]));

            studentInfo.put(suggestion[2], temp);
        }

        for(String movie : studentInfo.keySet()){
            System.out.println(movie);
            System.out.println(studentInfo.get(movie));
        }
    }
}

class StudentName implements Comparable<StudentName>{
    public final String first;
    public final String last;
    public StudentName(String first, String last){
        this.first = first;
        this.last = last;
    }

    @Override
    public int compareTo(StudentName o) {
        if(this.last.equals(o.last)){
            return this.first.compareTo(o.first);

        }
        else{
            return this.last.compareTo(o.last);
        }
    }

    @Override
    public String toString() {
        return this.first + " " + this.last;
    }
}