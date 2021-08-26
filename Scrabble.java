import java.io.BufferedReader;
//import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Scrabble {
    List<String> dict = new ArrayList<>();
    HashMap<String, Integer> charValues = new HashMap<>();

    public String normalizeCase(String text) {
        return text.toUpperCase();
    }

    public void loadDictionary(String filename) {
        System.out.print(String.format("Loading dict from file %s ... ", filename));

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
            System.out.println(String.format("OK (%d lines)", cnt));

            dict = list;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);            
        }
    }

    public void loadCharValues(String filename) {
        System.out.print(String.format("Loading char values from file %s ... ", filename));

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            HashMap<String, Integer> map = new HashMap<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    Scanner input = new Scanner(line);

                    input.useDelimiter("");
                    String k = input.next(".");
                    
                    input.reset();
                    Integer v = input.nextInt();
                    
                    map.put(k, v);
                    input.close();
                }
            }

            br.close();
            charValues = map;
            System.out.println(String.format("OK: %s", map));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);            
        }
    }

    public Integer wordScore(String word) {
        Integer score = 0;
        for (Character c : word.toCharArray()) {
            // score++;    // TODO no real score computation, just a length of the word
            try {
                score += charValues.get(c.toString());
            } catch (NullPointerException e) {
                // missing definition for a given char
                score += 0;
            }
        }
        return score;
    }

    public WordScoreResult bestAvailableWord(String letters) {
        WordScoreResult ret = new WordScoreResult("", 0, letters);

        //dict.forEach();
        SEARCH_DICT:
        for (String wordFromDict : dict) {
            StringBuilder letters_copy = new StringBuilder(letters);
            Boolean wordOK = true;

            SEARCH_CHARS:
            for (Character charFromDictWord : wordFromDict.toCharArray()) {
                Integer pos;
                if ((pos = letters_copy.indexOf(charFromDictWord.toString())) >= 0) {
                    letters_copy.deleteCharAt(pos);
                } else {
                    wordOK = false;
                    break SEARCH_CHARS;
                }
            }

            if (wordOK) { // some of the chars can form a word
                Integer score = wordScore(wordFromDict);
                if (score > ret.score()) {
                    //System.out.println(String.format("Pro kombinaci [%s] mozna [%s], zbyva [%s] (score %d)", letters, wordFromDict, letters_copy, score));
                    ret = new WordScoreResult(wordFromDict, score, letters_copy.toString());
                }
            }  
        }

        return ret;
    }
}
