package main.java;

import java.io.BufferedReader;
import java.io.InputStream;
//import java.io.File;
//import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.InputStreamReader;

public class Scrabble {
    List<String> dict = new ArrayList<>();
    HashMap<String, Integer> charValues = new HashMap<>();

    public String normalizeCase(String text) {
        return text.toUpperCase();
    }

    public void loadAllFromFolder(String foldername) {
        loadDictionary(String.format("main/resources/wordlist/%s/wordlist.txt", foldername));
        loadCharValues(String.format("main/resources/wordlist/%s/letter_values.txt", foldername));
    }

    private InputStream getResourceAsStream(String filename) {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }

    public void loadDictionary(String filename) {
        System.out.print(String.format("Loading dict from file %s ... ", filename));

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getResourceAsStream(filename)));

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
            BufferedReader br = new BufferedReader(new InputStreamReader(getResourceAsStream(filename)));

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
            try {
                score += charValues.get(c.toString());
            } catch (NullPointerException e) {
                // missing definition for a given char, just ignore it and count as 0
                // score += 0;
            }
        }
        return score;
    }

    // returns a single best word together with its score and with remaining unused letters
    public WordScoreResult bestAvailableWord(String letters) {
        WordScoreResult ret = new WordScoreResult("", 0, letters);

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

    public List<WordScoreResult> bestAvailableWordList(String letters) {
        List<WordScoreResult> ret = new ArrayList<>();
        Integer totalScore = 0;

        while (true) {    
            WordScoreResult singleResult = bestAvailableWord(letters);
            if (singleResult.score() == 0) {
                break;
            }

            ret.add(singleResult);
            
            totalScore += singleResult.score();
            letters = singleResult.remainingChars();
        };

        return ret;
    }
}
