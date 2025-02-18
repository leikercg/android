package com.example.hipotenochas;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    GameLogic gl;
    private static int rows = 8;
    private static int columns = 8;
    private static int hipotenochas = 10;

    public static int selectedCharaImg = R.drawable.abstracta_rescale;

    public static GridLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.informatica_rescale);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        gameLayout = findViewById(R.id.boardLayout);

        gl = new GameLogic(rows,columns,hipotenochas);
        int[][] gameBoard = gl.getGameBoard();
        CreateGameLayout(gameBoard);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    public void ResetGame(MenuItem menuItem){
        if(gl != null){
            gl.newGame(rows,columns,hipotenochas);
            int[][] gameBoard = gl.getGameBoard();
            CreateGameLayout(gameBoard);
        }
    }

    public void showInstructionsDialog(MenuItem menuItem){
        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(this);
        diaBuilder.setMessage(R.string.instructions_text);
        diaBuilder.setTitle(R.string.instructions);
        diaBuilder.setNeutralButton("OK",null);

        AlertDialog dialog = diaBuilder.create();
        dialog.show();
    }

    public void showCharaSelector(MenuItem menuItem){
        SelectCharacterDialog charaDialog = new SelectCharacterDialog(this);
        charaDialog.show();
    }

    public void ShowDiffSelector(MenuItem menuItem){
        DifficultyDialog difficultyDialog = new DifficultyDialog(this);
        difficultyDialog.show();
    }

    public void TestToast(View view){
        String text = String.format("rows: %d,columns: %d,hipotenochas: %d",rows,columns,hipotenochas);
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    public static void setColumns(int newColumns) {
        columns = newColumns;
    }

    public static void setRows(int newRows) {
        rows = newRows;
    }

    public static int getRows(){
        return rows;
    }

    public static void setHipotenochas(int newHipotenochas) {
        hipotenochas = newHipotenochas;
    }

    public static void setSelectedCharaImg(int hipotenocha) {
        selectedCharaImg = hipotenocha;
    }

    private void CreateGameLayout(int[][] gameBoard){
        gameLayout.removeAllViews();
        Point point = new Point();
        Display screenDisplay = getWindowManager().getDefaultDisplay();
        screenDisplay.getSize(point);
        int screenWidth = point.x;
        int screenHeight = point.y;

        gameLayout.setRowCount(rows);
        gameLayout.setColumnCount(columns);

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(screenWidth/columns,screenHeight/(rows+2));
                ButtonsListener listener = new ButtonsListener(i,j,gameBoard);
                if(gameBoard[i][j] > -1){
                    Button btn = new Button(this);
                    btn.setText("");
                    btn.setOnClickListener(listener);
                    btn.setOnLongClickListener(listener);
                    btn.setLayoutParams(lp);
                    gameLayout.addView(btn);
                } else {
                    ImageButton btn = new ImageButton(this);
                    btn.setLayoutParams(lp);
                    btn.setOnClickListener(listener);
                    btn.setOnLongClickListener(listener);
                    gameLayout.addView(btn);
                }
            }
        }
    }
}