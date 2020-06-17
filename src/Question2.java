import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Question2 {
    public static void main(String[] args) throws IOException {
        if(args.length < 1){
            System.out.println("Please specify the word as a command-line argument!");
            System.exit(0);
        }

        String wordOrig = args[0];
        String word = wordOrig.toLowerCase();

        Scanner inFile = new Scanner(new BufferedReader(new FileReader("dictionary.txt")));
        Set<String> words = new HashSet<>();

        while(inFile.hasNext()){
            words.add(inFile.next().toLowerCase());
        }

        boolean correct = words.contains(word);
        System.out.printf("\"%s\" is spelled %scorrectly.", wordOrig, correct ?  "": "in");

    }
}
