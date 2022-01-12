package org.openjfx.tictactoe;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author sonya
 */
public class TheGame extends Application {

    private char currentTurn = 'X';

    @Override
    public void start(Stage stage) throws Exception {
        
        // Draw the main pane:
        
        BorderPane mainFrame = new BorderPane();
        
        Label turnMsg = new Label("Turn: " + String.valueOf(this.currentTurn));
        turnMsg.setFont(Font.font("Monospaced", 40));
        mainFrame.setTop(turnMsg);
        
        
        // Draw the 3X3 grid pane:
        
        GridPane layout = new GridPane();
        
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        
        
        // Draw the buttons and initialize them:
        
        ArrayList<ArrayList<Button>> buttons = new ArrayList<>();
        
        for (int i = 0; i < 3; i ++) {
            ArrayList<Button> b = new ArrayList<>();
            for (int j = 0; j < 3; j ++) {
                b.add(new Button(""));
                b.get(j).setFont(Font.font("Monospaced", 40));
                b.get(j).setMinSize(100, 100);
                layout.add(b.get(j), i, j);
            }
            buttons.add(b);
        }
        
        
        // Activate the buttons on click:
        
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                Button buttonPressed = buttons.get(i).get(j);
                buttons.get(i).get(j).setOnMouseClicked((event) -> {
                    String b = buttonPressed.getText();
                    if (b.equals("")) {
                        buttonPressed.setText(String.valueOf(this.currentTurn));
                        updateTurn(turnMsg, buttons);
                    }
                });
            }
        }
        
        mainFrame.setCenter(layout);
        
        // Create the main view and add the high level layout:
        
        Scene view = new Scene(mainFrame, 500, 600);

        
        // Show the application:
        
        stage.setScene(view);
        stage.show();
        
    }

    
    /**
     * Updates the label with "Turn: X/O" and checks for end of game
     * @param turnMsg
     * @param buttons 
     */
    private void updateTurn(Label turnMsg, ArrayList<ArrayList<Button>> buttons) {
        String won = checkForWins(buttons);
        if (won.equals("")) {
            this.currentTurn = (this.currentTurn == 'X' ? 'O' : 'X');
            turnMsg.setText("Turn: " + String.valueOf(this.currentTurn));
            return;
        } else if (won.equals("D")) {
            turnMsg.setText("Draw Game!");
        } else {
            turnMsg.setText(won + " WON!");
        }
        
        // Now deactivate the buttons on click:
        
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                buttons.get(i).get(j).setOnMouseClicked((event) -> {});
            }
        }
    }
    
    /**
     * 
     * @return "": game has not ended yet
     * @return "X": X won the game
     * @return "O": O won the game
     * @return "D": Draw game
     */
    private String checkForWins(ArrayList<ArrayList<Button>> buttons) {
        
        // Check rows
        for (int i = 0; i < 3; i ++) {
            if (buttons.get(i).get(0).getText().equals(buttons.get(i).get(1).getText()) 
                    && buttons.get(i).get(0).getText().equals(buttons.get(i).get(2).getText())) {
                return "" + buttons.get(i).get(0).getText();
            }
        }
        
        // Check columns
        for (int i = 0; i < 3; i ++) {
            if (buttons.get(0).get(i).getText().equals(buttons.get(1).get(i).getText()) 
                    && buttons.get(0).get(i).getText().equals(buttons.get(2).get(i).getText())) {
                return "" + buttons.get(0).get(i).getText();
            }
        }
        
        // Check diagonaly
        if (buttons.get(0).get(0).getText().equals(buttons.get(1).get(1).getText()) 
                && buttons.get(0).get(0).getText().equals(buttons.get(2).get(2).getText())
                ||
                buttons.get(0).get(2).getText().equals(buttons.get(1).get(1).getText()) 
                && buttons.get(0).get(2).getText().equals(buttons.get(2).get(0).getText())) {
            return "" + buttons.get(1).get(1).getText();
        }
        
        // If everything else is OK, check if all values are entered, 
        // if not, then the game is not over yet
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                if (buttons.get(i).get(j).getText().equals("")) {
                    return "";
                }
            }
        }
        
        // If all are entered, then it's a draw game
        return "D";
    }
    
}

