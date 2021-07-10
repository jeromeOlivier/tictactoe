package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();
        GameLogic logic = new GameLogic();
        
        printer.drawBoard(logic.getGame());
        
        logic.numericalInput();
    }
}

class GameLogic {
    private int[][] game = new int[3][3];
    
    public int[][] getGame() {
        return game;
    }
    
    public void setGame(int[][] game) {
        this.game = game;
    }
    
    public void numericalInput() {
        Scanner scanner = new Scanner(System.in);
        Printer printer = new Printer();
        
        System.out.print("Enter the coordinates: ");
        String[] c = scanner.nextLine().split("\\s");
        int row = 0, col = 0;
        try {
            row = Integer.parseInt(c[0]);
            col = Integer.parseInt(c[1]);
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            numericalInput();
        }
        if (row > 3 || col > 3 || row < 1 || col < 1) {
            System.out.println("Coordinates should be from 1 to 3!");
            numericalInput();
        } else if (Math.abs(game[row - 1][col - 1]) == 1) {
            System.out.println("This cell is occupied! Choose another one!");
            numericalInput();
        } else {
            game[row - 1][col - 1] = 1;
            printer.drawBoard(game);
        }
        
    }
    
    public int[] analyzeBoard(int[][] game) {
        int blank = 9, xScore = 0, yScore = 0, xCount = 0, yCount = 0, xDiagonal = 0, yDiagonal = 0;
        for (int i = 0; i < game.length; i++) {
            int rowTotal = 0, columnTotal = 0, diag01Total = 0, diag02Total = 0;
            for (int j = 0; j < game[i].length; j++) {
                if (game[i][j] != 0) {
                    --blank;
                }
                rowTotal = rowTotal + game[i][j];
                columnTotal = columnTotal + game[j][i];
                diag01Total = diag01Total + game[j][j];
                diag02Total = diag02Total + game[2 - j][j];
                if (rowTotal == 3
                        || columnTotal == 3
                        || diag01Total == 3
                        || diag02Total == 3) {
                    xScore++;
                    xDiagonal++;
                }
                if (rowTotal == -3
                        || columnTotal == -3
                        || diag01Total == -3
                        || diag02Total == -3) {
                    yScore++;
                    yDiagonal++;
                }
                if (game[i][j] == 1) {
                    ++xCount;
                } else if (game[i][j] == -1) {
                    ++yCount;
                }
            }
        }
        return new int[]{blank, xScore, yScore, xCount, yCount, xDiagonal, yDiagonal};
    }
}

class Printer {
    
    public void drawBoard(int[][] game) {
        System.out.println("---------");
        int increment = 0;
        int value;
        char mark;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 0 || j == 4) {
                    System.out.print("| ");
                } else {
                    value = game[i][j - 1];
                    mark = value == -1 ? 'O' : value == 1 ? 'X' : '_';
                    System.out.print(mark + " ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("---------");
    }
}