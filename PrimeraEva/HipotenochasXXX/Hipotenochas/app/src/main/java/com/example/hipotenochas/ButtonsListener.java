package com.example.hipotenochas;

import static com.example.hipotenochas.MainActivity.gameLayout;
import static com.example.hipotenochas.MainActivity.getRows;
import static com.example.hipotenochas.MainActivity.selectedCharaImg;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class ButtonsListener implements View.OnClickListener, View.OnLongClickListener {
    private int rowPos;
    private int colPos;
    private int[][] gameBoard;

    public ButtonsListener(int rowPos, int colPos, int[][] gameBoard) {
        this.rowPos = rowPos;
        this.colPos = colPos;
        this.gameBoard = gameBoard;
    }

    @Override
    public void onClick(View v) {
        if(!(v instanceof ImageButton)){
            Button btn = (Button) v;
            if(gameBoard[rowPos][colPos] == 0) {
                SearchAround(true, rowPos, colPos);
            } else {
                btn.setText(String.format("%d",gameBoard[rowPos][colPos]));
            }
        } else {
            ImageButton imageButton = (ImageButton) v;
            imageButton.setImageResource(R.drawable.nocturna_rescale);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(!(v instanceof ImageButton)){
            Button btn = (Button) v;
            btn.setTextSize(14);
        } else {
            ImageButton imageButton = (ImageButton) v;
            imageButton.setImageResource(selectedCharaImg);
        }

        return false;
    }

    private void SearchAround(boolean isRec, int row, int col){
        if (row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[0].length) {
            return;
        }

        Button btn = (Button) gameLayout.getChildAt(getRows()*row+col);

        if(btn.getText() != "" && isRec){
            return;
        }

        if(gameBoard[row][col] == -1){
            return;
        }

        btn.setText(String.format("%d",gameBoard[row][col]));
        if(gameBoard[row][col] != 0){
            return;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    SearchAround(true, row + i, col + j);
                }
            }
        }

    }
}
