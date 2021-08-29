package main.java;

import java.util.*;
import java.io.*;
import org.apache.commons.codec.binary.Hex;

public class Main {
    public static void main(String[] args) {
        Locale locale = Locale.forLanguageTag("cs-CZ");
        System.out.println("locale: " + locale);

        try {
            OutputStreamWriter oswDef = new OutputStreamWriter(System.out);
            System.out.println("Implicitni kodovani konzole: " + oswDef.getEncoding());

            /* IBM852 je výstupní kódování češtiny v DOSovém okénku */
            OutputStreamWriter osw = new OutputStreamWriter(System.out, "IBM852");
            System.out.println("Nastavene kodovani konzole:  " + osw.getEncoding());

            String l = new Scanner(System.in, "IBM852").nextLine();
            System.out.println("1: len=" + l.length() + ", str=" + Hex.encodeHexString(l.getBytes()));

            l = System.console().readLine();
            System.out.println("2: len=" + l.length() + ", str=" + Hex.encodeHexString(l.getBytes()));

            Scrabble scrabble = new Scrabble();
            scrabble.loadAllFromFolder("cz_nodia");

            System.out.println("Which letters do you have?");
            String letters = new BufferedReader(new InputStreamReader(System.in, "UTF-8")).readLine();
            System.out.println(letters);

            letters = scrabble.normalizeCase(letters);
            System.out.println(String.format("Best set of words for a set of [%s]:", letters));
            List<WordScoreResult> list = scrabble.bestAvailableWordList(letters);
            if (list.isEmpty()) {
                System.out.println(" - no solution for this input");
            } else {
                for (WordScoreResult res : list) {
                    System.out.println(String.format(" - %s (%d pt)", res.getWord(), res.getScore()));
                }
            }
            // String.format("Best set of words: %s",
            // scrabble.bestAvailableWordList(letters)));
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}