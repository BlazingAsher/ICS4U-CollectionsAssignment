import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Question1 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the path to the text file: ");
        String filename = in.next();
        Scanner inFile = new Scanner(new BufferedReader(new FileReader(filename)));

        Map<String, Integer> frequencies = new HashMap<>();
        int numWords = 0;

        while(inFile.hasNext()){
            String word = inFile.next();
            word = word.replaceAll("[^a-zA-Z0-9]+","").toLowerCase();
            if(word.strip().length() > 0){
                int cVal = frequencies.getOrDefault(word, 0);
                frequencies.put(word, cVal + 1);
                numWords ++;
            }

        }

        Set<WordEntry> percentSet = new TreeSet<>(Comparator.reverseOrder());
        for(String key : frequencies.keySet()){
            percentSet.add(new WordEntry(key, frequencies.get(key)/(double)numWords));
        }

        for(WordEntry entry : percentSet){
            System.out.printf("%s - %f%%", entry.word, entry.percent*100);
        }
    }
}

class WordEntry implements Comparable<WordEntry>{
    public final String word;
    public final double percent;
    public WordEntry(String word, double percent){
        this.word = word;
        this.percent = percent;
    }

    @Override
    public int compareTo(WordEntry o) {
        int cRes = Double.compare(this.percent, o.percent);
        if(cRes == 0){
            return - this.word.compareTo(o.word);
        }
        return cRes;
    }
}