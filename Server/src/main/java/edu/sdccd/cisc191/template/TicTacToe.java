package edu.sdccd.cisc191.template;

// import statements
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.*;

// The TicTacToe class extends the JavaFX Application
public class TicTacToe extends Application{

    // Making labels to display the score for players X and O
    // and the current player's turn
    private GameBoardLabel Xscore = new GameBoardLabel();
    private GameBoardLabel Oscore = new GameBoardLabel();
    private GameBoardLabel Turn = new GameBoardLabel();

    // Variables to store the number of wins for players X and O
    private int Xwins = 0; // Tracks the number of games X has won
    private int Owins = 0; // Tracks the number of games O has won


    public static Button[][] board = new Button[3][3];; //array representation of the game board
    private boolean x = true; // tracks whether it's X's or O'x turn
    public Button[][] buttons = new Button[3][3];


    boolean gameOver = false;

    /**
     * launches the javaFX applicatgion
     * @param args
     */
    public static void main(String[] args) {
        // launches the application
        launch(args);
    }

    /**
     * Method for updating the header
     */
    public void updateHeader() {
        // update labels
        // changes the text depending how many fishes or guesses are remaining in
        // the game
        Xscore.setText("X: " +  Xwins);
        Oscore.setText("O: " +  Owins);
        Turn.setText("Turn: " + getCurrentTurn());



    }

    /**
     *
     * @param primayStage the primary stage for this application, onto which
     * the application scene can be set. The primary stage will be embedded in
     * the browser if the application was launched as an applet.
     * Applications may create other stages, if needed, but they will not be
     * primary stages and will not be embedded in the browser.
     */
    public void start(Stage primayStage) {

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(event ->{
            restart();
        });
        gameOver = false;

        GameDataManager manager = new GameDataManager(this);
        // SAVE GAME BUTTON
        Button saveButton = new Button("Save Game");
        saveButton.setOnAction(event -> manager.saveGame());

        // LOAD GAME BUTTON
        Button loadButton = new Button("Load Game");
        loadButton.setOnAction(event -> manager.loadGame());

        GridPane grid = new GridPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(grid);
        HBox hbox = new HBox(Xscore, Oscore, Turn, restartButton, saveButton, loadButton);
        borderPane.setTop(hbox);
        x = !x;

        updateHeader();
        createGameGrid(grid);
        setScene(primayStage, borderPane);
    } // end start();

    /**
     * Sets the scene
     * @param primaryStage the primary stage of the game
     * @param borderPane the borderpane layout
     */
    private void setScene(Stage primaryStage, BorderPane borderPane)
    {
        Scene scene = new Scene(borderPane,470, 490);

        // Sets the title of the game window
        primaryStage.setTitle("Tic -  Tac - Toe");

        // Set the scene of the primary stage to the one containing the buttons
        primaryStage.setScene(scene);

        // Show the game window
        primaryStage.show();
    }

    /**
     * Creates the 3x3 tic tac toe grid.
     * @param grid a GridPane layout used to display the buttons
     */
    private void createGameGrid(GridPane grid)
    {
        // nested for loop to create a 3X3 grid of buttons
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 3; column++){
                GameBoardButton button = new GameBoardButton(row, column, this);


                // size of each button
                button.setMinSize(160,150);
                // set the font size of the text to 50
                button.setStyle("-fx-font-size: 50;");

                // Create final variables for row and column
                final int r = row;
                final int c = column;

                // Sets the action event for when the button is clicked
                button.setOnAction(event -> {
                    button.handleButtonClick();
                    TicTacToe.board[r][c] = button;
                    // stores the clicked button in the corresponding position
                    //buttons[r][c] = button;
                });
                grid.add(button,column,row);
                buttons[row][column]=button;
            }
        }

    }

    public void restart() {
        // iterating through each row and column
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");  // Clear the text on each button
                buttons[row][col].setDisable(false); // Re-enable the button
            }
        }
        x = true;
        // Calling the updateHeader method
        updateHeader();
    }

    /**  Method for knowing whose turn it is
     *
     * @return
     */

    public String getCurrentTurn(){
        String turn;
        if(x){
            turn="X";
        }
        else{
            turn="0";
        }
        return turn;
    }

    /** This method switches the current player's turn.
     *  The variable 'x' represents whether it's X's turn.
     *   If 'x' is true, it's X's turn, and if false, it's O's turn.
     *   The method toggles the value of 'x', so the turn is switched
     *   between X and O after each call.
     */
    public void SwitchTurn(){

        x=!x; // tggle the value of "x"
    }

    /**
     * Method for disabling the buttons on the game board
     */
    public void disableBoard() {

        // Iterate through each row of the 3x3 grid
        for (int row = 0; row < 3; row++) {
            // For each row, iterate through each column
            for (int col = 0; col < 3; col++) {
                // Disable the button at the current row and column which makes it
                // unclickable
                buttons[row][col].setDisable(true);
            }
        }
    }

    /**
     * Method for checking who won
     */
    public void Check(){
        int count=0;


        //rows
        // iterates over each row
        for(int row = 0; row < 3; row++){
            // If the first button of the row is empty, it skips the check for that row using
            // continue
            if(buttons[row][0].getText().equals("")){
                continue;
            }
            // Checks if all the buttons in the row have the same value
            if(buttons[row][0].getText().equals(buttons[row][1].getText()) && buttons[row][0].getText().equals(buttons[row][2].getText())){
                // If they do, then the board is disabled
                disableBoard();
                // The current player's win count is incremented
                if(getCurrentTurn().equals("X")){
                    Xwins++;
                }else{
                    Owins++;
                }
                // prints the result
                System.out.println(getCurrentTurn()+ " wins");
                return;
            }
        }


        //columns
        // iterates over each column
        for(int col = 0; col < 3; col++){
            // skips the check if the first button in the column is empty
            if(buttons[0][col].getText().equals("")){
                continue;
            }
            // Checks if all buttons in that column have the same text. If true, it declares a win
            if(buttons[0][col].getText().equals(buttons[1][col].getText()) && buttons[0][col].getText().equals(buttons[2][col].getText())){
                // Disables the board
                disableBoard();
                if(getCurrentTurn().equals("X")){
                    Xwins++;
                }else{
                    Owins++;
                }
                // Prints the winner
                System.out.println(getCurrentTurn()+ " wins");
                return;
            }
        }


        //diagonal 1 ( top left to bottom right)
        if(buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[0][0].getText().equals(buttons[2][2].getText()) && !(buttons[0][0].getText().equals(""))){

            // Disables the board
            disableBoard();
            if(getCurrentTurn().equals("X")){
                // Updates the win count
                Xwins++;
            }else{
                Owins++;
            }
            // Prints the winner
            System.out.println(getCurrentTurn()+ " wins");
            return;
        }


        //diagonal 2 ( top right to bottom left)
        if(buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[0][2].getText().equals(buttons[2][0].getText()) && !(buttons[0][2].getText().equals(""))){
            // Disables the board
            disableBoard();
            if(getCurrentTurn().equals("X")){
                Xwins++;
            }else{
                Owins++;
            }
            // Prints the winner
            System.out.println(getCurrentTurn()+ " wins");
            return;
        }


        //tie
        // Loops through the entire gameboard
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(!(buttons[i][j].getText().equals(""))){
                    count++;

                }
            }
        }
        // if all buttons are filled, it means no empty spots remain
        if(count==9){
            System.out.println("tie");
            disableBoard();
        }
    }


    // Getter methods to access game state from GameFileManager
    public Button[][] getButtons() {
        return buttons;
    }

    public boolean getTurn() {
        return x;
    }

    public int getXWins() {
        return Xwins;
    }

    public int getOWins() {
        return Owins;
    }

    public void setTurn(boolean turn) {
        this.x = turn;
    }

    public void setXWins(int xWins) {
        this.Xwins = xWins;
    }

    public void setOWins(int oWins) {
        this.Owins = oWins;
    }
}



