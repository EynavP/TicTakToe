package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.db.DataBase;
import com.example.myapplication.db.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FinalActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView Tv_declaration_title;
    WinnerAdapter winnerAdapter;
    String winnerName;
    List<User> winners;
    Button playAgainBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Tv_declaration_title=findViewById(R.id.Tv_declaration_title);
        winnerName=getIntent().getStringExtra("win");
        recyclerView=findViewById(R.id.RecyclerView);
        playAgainBtn = findViewById(R.id.play_again_btn);
        progressBar = findViewById(R.id.progressBar);

        playAgainBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        Thread t = new Thread(() -> {
            DataBase db = DataBase.getDbInstance(this.getApplicationContext());
            winners = db.userDao().getAllUsers();
            sortWinners(winners);


            if(!winnerName.equals(getString(R.string.No_winner))){
                String winnerSpeed=getIntent().getStringExtra("speed");
                if(winners.size() <=9)
                {
                    User user = new User();
                    user.user_name = winnerName;
                    user.time = winnerSpeed;
                    db.userDao().insertUser(user);
                    sortWinners(winners);
                }
                else
                {
                    if(compareTime(winners.get(9).time, winnerSpeed) == 1 ){
                        db.userDao().delete(winners.get(9));
                        User user = new User();
                        user.user_name = winnerName;
                        user.time = winnerSpeed;
                        db.userDao().insertUser(user);
                        sortWinners(winners);
                    }
                }
            }

            this.runOnUiThread(() -> {

                if(winnerName.equals(getString(R.string.No_winner))){
                    Tv_declaration_title.setText(getString(R.string.No_winner));
                }
                else {
                    Tv_declaration_title.setText(winnerName + " Won!");
                }

                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                winnerAdapter=new WinnerAdapter(this,winners);
                recyclerView.setAdapter(winnerAdapter);
            });
        });
        t.start();





    }

    @Override
    public void onResume(){
        super.onResume();
        ProgressBar progressBar;
        progressBar = findViewById(R.id.progressBar);

        if(progressBar.getVisibility() != View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void sortWinners(List<User> winners)
    {
        Collections.sort(winners, new Comparator<User>() {
            @Override
            public int compare(User user, User t1) {
                return compareTime(user.time, t1.time);
            }
        });
    }

    public int compareTime(String t1, String t2)
    {
        SimpleDateFormat parser = new SimpleDateFormat("HH : mm");
        Date user1 = null;
        Date user2 = null;
        try {
            user1 = parser.parse(t1);
            user2 = parser.parse(t2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (user1.after(user2)) {
            return 1;
        } else if (user1.equals(user2))
            return 0;
        else
            return -1;
    }

}