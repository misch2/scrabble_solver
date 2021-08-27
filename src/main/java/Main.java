package main.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scrabble scrabble = new Scrabble();
        scrabble.loadAllFromFolder("cz_nodia");

        System.out.println("Which letters do you have?");
        Scanner input = new Scanner(System.in);
        String letters = input.nextLine();
        input.close();
        
        letters = scrabble.normalizeCase(letters);
        System.out.println("Best set of words:");
        for (WordScoreResult res : scrabble.bestAvailableWordList(letters)) {
            System.out.println(String.format(" - %s (%d pt)", res.getWord(), res.getScore()));
        }
        //String.format("Best set of words: %s", scrabble.bestAvailableWordList(letters)));
    }  
}