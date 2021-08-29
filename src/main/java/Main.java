package main.java;

import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.scene.input.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.application.*;
import javafx.stage.*;

public class Main extends Application {
    // public constant String resourcePath = "main/resources/";
    private static Scrabble scrabble;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main/resources/mainscreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private TextField srcLetters;

    @FXML
    private ListView<String> dstList;

    @FXML 
    void liveSolve() {
        solve();
    }

    @FXML
    void solve() {
        String letters = srcLetters.getText();
        
        letters = scrabble.normalizeCase(letters);
        // srcLetters.setText(letters); // didn't behave nicely, this would always reset cursor position

        List<WordScoreResult> list = scrabble.bestAvailableWordList(letters);
        dstList.getItems().clear();
        if (list.isEmpty()) {
            dstList.getItems().add(" - no solution for this input");
        } else {
            String remaining = "";
            for (WordScoreResult res : list) {
                dstList.getItems().add(String.format(" - %s (%d pt)", res.getWord(), res.getScore()));
                remaining = res.getRemainingChars();
            }
            dstList.getItems().add("Remaining charactes: " + remaining);
       }
    }

    public static void main(String[] args) {
        scrabble = new Scrabble();
        scrabble.loadAllFromFolder("cz_dia");

        Application.launch(args);
        System.exit(0);

        Locale.setDefault(Locale.forLanguageTag("cs-CZ"));
    }
}