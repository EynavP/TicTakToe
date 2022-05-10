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

        DataBase db = DataBase.getDbInstance(this.getApplicationContext());
        winners = db.userDao().getTopTen();



        Thread t = new Thread(() -> {

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

        playAgainBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
            finish();
        });


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

}