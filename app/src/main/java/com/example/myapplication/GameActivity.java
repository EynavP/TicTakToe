package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity  implements View.OnClickListener {

    TextView player1_name, player2_name;
    TextView timerText;
    Timer timer;
    TimerTask timerTask;
    Double time =0.0;

    private Button[] buttons = new Button[9];

    int[][] winPositions = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    int[] gamestate = {2,2,2,2,2,2,2,2,2};

    boolean activePlayer;
    int roundCount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timerText = findViewById(R.id.timer);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);

        player1_name = findViewById(R.id.tv_player1);
        player2_name = findViewById(R.id.tv_player2);
        player1_name.setText(getIntent().getStringExtra("player1"));
        player2_name.setText(getIntent().getStringExtra("player2"));

        for (int i=0;i < 9; i++)
        {
            String buttonID = "button"+i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        activePlayer = true;

    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals(""))
            return;
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int lastPressed = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));
        if(activePlayer)
        {
            ((Button)view).setText("X");
            ((Button)view).setTextColor(getResources().getColor(R.color.orange_lite));
            gamestate[lastPressed]=0;
        }
        else
        {
            ((Button)view).setText("O");
            ((Button)view).setTextColor(getResources().getColor(R.color.white));
            gamestate[lastPressed]=1;
        }
        roundCount++;
        if(checkWinner())
        {
            if(activePlayer){
                Log.i("win","Player 1 won");
            }
            else
                Log.i("win","Player 2 won");
        }
        else if(roundCount == 9){
            Log.i("won","No winner");

        }
        else
            activePlayer = !activePlayer;
    }

    public boolean checkWinner(){
        boolean winnerResult = false;
        for(int [] winnerPos : winPositions){
            if(gamestate[winnerPos[0]] == gamestate[winnerPos[1]] &&
                gamestate[winnerPos[1]] == gamestate[winnerPos[2]] &&
                gamestate[winnerPos[0]]!=2)
                return winnerResult = true;
        }
        return  winnerResult;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public String getTimerText()
    {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400 % 3600) % 60);
        int minutes = ((rounded % 86400 % 3600) / 60);
        String timeStr = String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
        return timeStr;
    }
}