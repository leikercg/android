package com.example.hipotenochas;

public class GameLogic {
    private int[][] gameBoard;
    private int rows;
    private int cols;
    private int bombs;

    public GameLogic(int rows, int cols,int bombs){
        this.rows = rows;
        this.cols = cols;
        this.bombs = bombs;

        StartBoard(rows,cols,bombs);
    }

    private void StartBoard(int rows,int cols, int bombs){
        gameBoard = new int[rows][cols];

        int[] bombsPos = new int[bombs];
        for(int i = 0; i < bombs;i++){
            int value =  (int)(Math.random() * (cols*rows+1));
            if(!isOnArray(value,bombsPos)){
                bombsPos[i] = value;
            } else {
                i--;
            }
        }

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(isOnArray(i*rows+j,bombsPos)){
                    gameBoard[i][j] = -1;
                }
            }
        }

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(gameBoard[i][j] != -1){
                    gameBoard[i][j] = NearBombs(i,j);
                }
            }
        }

        for (int i = 0 ; i < 20; i++){
            String testo = "TestFor";
        }
    }

    private int NearBombs(int row, int col){
        int nearBombs = 0;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <=1 ;j++){
                if (((row + i >= 0) && (row + i < rows)) && ((col + j < cols) && (col + j >= 0))) {
                    if (gameBoard[row + i][col + j] == -1) {
                        nearBombs++;
                    }
                }
            }
        }

        return nearBombs;
    }

    public int[][] getGameBoard(){
        return gameBoard;
    }

    private boolean isOnArray(int value, int[] array){
        for (int element : array) {
            if (value == element) {
                return true;
            }
        }
        return false;
    }

    public void newGame(int newRows,int newCols, int newBombs){
        this.rows = newRows;
        this.cols = newCols;
        this.bombs = newBombs;

        StartBoard(rows,cols,bombs);
    }
}
