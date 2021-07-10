package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Printer print = new Printer();
        Game game = new Game();
        
        print.board(game.fetch());
        game.play();
    }
}

class Game {
    Scanner scanner = new Scanner(System.in);
    Printer print = new Printer();
    private int[][] game = new int[3][3];
    int player = 1;
    
    public int[][] fetch() {
        return game;
    }
    
    public void play() {
        print.requestCoordinates();
        String[] c = scanner.nextLine().split("\\s");
        int row = 0, col = 0;
        try {
            row = Integer.parseInt(c[0]);
            col = Integer.parseInt(c[1]);
        } catch (NumberFormatException e) {
            print.requestNumbersOnly();
            play();
        }
        if (row > 3 || col > 3 || row < 1 || col < 1) {
            print.requestStayWithinRange();
            play();
        } else if (Math.abs(game[row - 1][col - 1]) == 1) {
            print.requestUnusedCoordinates();
            play();
        } else {
            game[row - 1][col - 1] = player;
            print.board(game);
            int numberOfPlays = 0;
            for (int i = 0; i < game.length; i++) {
                int rowTotal = 0, columnTotal = 0, diag01Total = 0, diag02Total = 0;
                for (int j = 0; j < game[i].length; j++) {
                    rowTotal = rowTotal + game[i][j];
                    columnTotal = columnTotal + game[j][i];
                    diag01Total = diag01Total + game[j][j];
                    diag02Total = diag02Total + game[2 - j][j];
                    if (game[i][j] != 0) {++numberOfPlays;}
                }
                if (Math.abs(rowTotal) == 3
                        || Math.abs(columnTotal) == 3
                        || Math.abs(diag01Total) == 3
                        || Math.abs(diag02Total) == 3) {
                    print.declareWinner(player);
                    return;
                }
            }
            if (numberOfPlays == 9) {
                print.declareDraw();
                return;
            }
            player = player == 1 ? -1 : 1;
            play();
        }
    }
}

class Printer {
    
    public void board(int[][] game) {
        System.out.println("---------");
        int increment = 0;
        int value;
        char mark;
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 0 || j == 4) {
                    System.out.print("| ");
                } else {
                    value = game[i - 1][j - 1];
                    mark = value == -1 ? 'O' : value == 1 ? 'X' : ' ';
                    System.out.print(mark + " ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("---------");
    }
    
    public void requestCoordinates() {
        System.out.print("Enter the coordinates: ");
    }
    
    public void requestNumbersOnly() {
        System.out.println("You should enter numbers!");
    }
    
    public void requestStayWithinRange() {
        System.out.println("Coordinates should be from 1 to 3!");
    }
    
    public void requestUnusedCoordinates() {
        System.out.println("This cell is occupied! Choose another one!");
    }
    
    public void declareWinner(int player) {
        System.out.println(player == 1 ? "X wins" : "O wins");
    }
    
    public void declareDraw() {
        System.out.println("Draw");
    }
}