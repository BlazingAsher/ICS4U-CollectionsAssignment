/*
 * File: Question3.java
 * Author: David Hui
 * Description: Generates a list of movies and which students should watch them based on a recommendation list
 */
import java.io.*;
import java.util.*;

public class Question3 {
    public static void main(String[] args) throws IOException {
        // read the recommendation list
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("picks.txt")));
        Map<String, TreeSet<StudentName>> studentInfo = new HashMap<>();

        // get the movie that each student is recommended
        while(inFile.hasNextLine()){
            String[] suggestion = inFile.nextLine().split(",");
            // add the student to the list in the movie
            TreeSet<StudentName> temp = studentInfo.getOrDefault(suggestion[2], new TreeSet<>());

            temp.add(new StudentName(suggestion[0], suggestion[1]));

            studentInfo.put(suggestion[2], temp);
        }

        // prepare a PrintStream for the result file
        PrintStream dataWrite = new PrintStream(new File("recommends.txt"));

        // print the results to the file
        for(String movie : studentInfo.keySet()){
            dataWrite.println(movie);
            dataWrite.println(studentInfo.get(movie));
        }
        dataWrite.close();
    }
}

class StudentName implements Comparable<StudentName>{
    public final String first; // first name of the student
    public final String last; // last name of the student
    public StudentName(String first, String last){
        this.first = first;
        this.last = last;
    }

    @Override
    public int compareTo(StudentName o) {
        if(this.last.equals(o.last)){ // if last names are equal, sort by first name
            return this.first.compareTo(o.first);

        }
        else{ // sort by last name
            return this.last.compareTo(o.last);
        }
    }

    @Override
    public String toString() {
        return this.first + " " + this.last;
    }
}