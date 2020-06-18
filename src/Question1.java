/*
 * File: Question1.java
 * Author: David Hui
 * Description: Determines the frequency of words in a text file
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Question1 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        // get the path to the file and read it
        System.out.println("Please enter the path to the text file: ");
        String filename = in.next();
        Scanner inFile = new Scanner(new BufferedReader(new FileReader(filename)));

        // map to keep track of frequencies
        Map<String, Integer> frequencies = new HashMap<>();
        int numWords = 0;

        // read the frequencies of the words (filter out all special symbols)
        while(inFile.hasNext()){
            String word = inFile.next();
            word = word.replaceAll("[^a-zA-Z0-9]+","").toLowerCase();
            // if it is blank, don't add it to the map
            if(word.strip().length() > 0){
                int cVal = frequencies.getOrDefault(word, 0);
                frequencies.put(word, cVal + 1);
                numWords ++;
            }

        }

        // create a treeset sorted in reverse order and add all the words
        SortedSet<WordEntry> percentSet = new TreeSet<>(Comparator.reverseOrder());
        for(String key : frequencies.keySet()){
            percentSet.add(new WordEntry(key, frequencies.get(key)/(double)numWords));
        }

        // iterate and print out the percentages
        for(WordEntry entry : percentSet){
            System.out.printf("%s - %.2f%%\n", entry.word, entry.percent*100);
        }
    }
}

class WordEntry implements Comparable<WordEntry>{
    public final String word; // the word
    public final double percent; // the frequency of the word
    public WordEntry(String word, double percent){
        this.word = word;
        this.percent = percent;
    }

    @Override
    public int compareTo(WordEntry o) {
        int cRes = Double.compare(this.percent, o.percent); // first compare by frequency
        if(cRes == 0){ // if they are equal, sort reverse-alphabetically
            return - this.word.compareTo(o.word);
        }
        return cRes;
    }
}