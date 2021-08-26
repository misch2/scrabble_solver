import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scrabble scrabble = new Scrabble();
        scrabble.loadDictionary("czech_wordlist_nodia.txt");
        scrabble.loadCharValues("czech_value_nodia.txt");

        System.out.println("Jaka mas pismenka?");
        Scanner input = new Scanner(System.in);
        String letters = input.nextLine();
        input.close();
        
        letters = scrabble.normalizeCase(letters);
        System.out.println(scrabble.bestAvailableWord(letters));

    }
    
}