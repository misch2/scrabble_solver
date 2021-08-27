package main.java;

public class WordScoreResult {
    private String word;
    private Integer score;
    private String remainingChars;

    public WordScoreResult(String word, Integer score, String remainingChars) {
        this.word = word;
        this.score = score;
        this.remainingChars = remainingChars;
    }

    public String getWord() {
        return this.word;
    }

    public Integer getScore() {
        return this.score;
    }

    public String getRemainingChars() {
        return this.remainingChars;
    }

    @Override
    public String toString() {
        return String.format("%s (score %d, remaining [%s])", word, score, remainingChars);
    }
}