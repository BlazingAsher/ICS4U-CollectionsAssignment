/*
 * File: Question2.java
 * Author: David Hui
 * Description: Determines whether a given word is correctly spelled based on a text dictionary
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Question2 {
    public static void main(String[] args) throws IOException {
        // check that there is an argument
        if(args.length < 1){
            System.out.println("Please specify the word as a command-line argument!");
            System.exit(0);
        }

        // get the word and change it to lower case for the dictionary
        String wordOrig = args[0];
        String word = wordOrig.toLowerCase();

        // read the file and store all the words in the HashSet
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("dictionary.txt")));
        Set<String> words = new HashSet<>();

        while(inFile.hasNext()){
            words.add(inFile.next().toLowerCase());
        }

        // check if the HashSet contains the word
        boolean correct = words.contains(word);
        System.out.printf("\"%s\" is spelled %scorrectly.", wordOrig, correct ?  "": "in");

    }
}
