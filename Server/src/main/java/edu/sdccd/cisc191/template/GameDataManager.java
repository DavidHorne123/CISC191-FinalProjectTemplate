package edu.sdccd.cisc191.template;

import java.io.*;

public class GameDataManager {
    private TicTacToe game;

    public GameDataManager(TicTacToe game) {
        this.game = game;
    }

    /**
     * Saves the current state of the game
     */
    public void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tic_tac_toe_save.dat"))) {
            String[][] boardState = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    boardState[i][j] = game.getButtons()[i][j].getText();
                }
            }
            out.writeObject(boardState);
            out.writeBoolean(game.getTurn());
            out.writeInt(game.getXWins());
            out.writeInt(game.getOWins());
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }

    /**
     * Loads the current state of the game.
     */
    public void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("tic_tac_toe_save.dat"))) {
            String[][] boardState = (String[][]) in.readObject();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    game.getButtons()[i][j].setText(boardState[i][j]);
                }
            }
            game.setTurn(in.readBoolean());
            game.setXWins(in.readInt());
            game.setOWins(in.readInt());
            game.updateHeader();
            game.Check();
            System.out.println("Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading the game: " + e.getMessage());
        }
    }
}
