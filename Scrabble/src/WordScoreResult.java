package Scrabble.src;

public class WordScoreResult {
    private String word;
    private Integer score;
    private String remainingChars;

    public WordScoreResult(String word, Integer score, String remainingChars) {
        this.word = word;
        this.score = score;
        this.remainingChars = remainingChars;
    }

    public String word() {
        return word;
    }

    public Integer score() {
        return score;
    }

    public String remainingChars() {
        return remainingChars;
    }

    @Override
    public String toString() {
        return String.format("%s (score %d, remaining [%s])", word, score, remainingChars);
    }
}