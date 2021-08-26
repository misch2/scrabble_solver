import java.io.BufferedReader;
//import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Scrabble {
    List<String> dict;

    public String normalizeCase(String text) {
        return text.toUpperCase();
    }

    public void loadDictionary(String filename) {
        System.out.print(String.format("Loading file %s ... ", filename));

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            List<String> list = new ArrayList<>();
            int cnt = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    list.add(normalizeCase(line));
                    cnt++;
                }
            }

            br.close();
            System.out.println(String.format("file loaded OK (%d lines)", cnt));

            dict = list;
        } catch (Exception e) {
            System.err.println(String.format("Dictionary file load failed: %s", e));
            System.exit(1);            
        }
    }

    public Integer wordScore(String word) {
        Integer score = 0;
        for (char c : word.toCharArray()) {
            score++;    // TODO no real score computation, just a length of the word
        }
        return score;
    }

    public List<String> availableWords(String letters) {
        List<String> ret = new ArrayList<>();
        int lastScore = 0;

        //dict.forEach();
        SEARCH_DICT:
        for (String wordFromDict : dict) {
            StringBuilder letters_copy = new StringBuilder(letters);
            Boolean wordOK = true;

            SEARCH_CHARS:
            for (Character charFromDictWord : wordFromDict.toCharArray()) {
                int pos;
                if ((pos = letters_copy.indexOf(charFromDictWord.toString())) >= 0) {
                    letters_copy.deleteCharAt(pos);
                } else {
                    wordOK = false;
                    break SEARCH_CHARS;
                }
            }

            if (wordOK) {
                int score = wordScore(wordFromDict);
                if (score > lastScore) {
                    System.out.println(String.format("Pro kombinaci [%s] mozna [%s], zbyva [%s] (score %d)", letters, wordFromDict, letters_copy, score));
                    lastScore = score;
                }
            }
                
        }

        
        return ret;
    }
}
