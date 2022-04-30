package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class FinalActivity extends AppCompatActivity {

    ArrayList<Winners> winners;
    RecyclerView recyclerView;
    TextView Tv_declaration_title;
    WinnerAdapter winnerAdapter;
    String winnerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Tv_declaration_title=findViewById(R.id.Tv_declaration_title);
        winners=new ArrayList<>();
        winnerName=getIntent().getStringExtra("win");
        recyclerView=findViewById(R.id.RecyclerView);

        if(winnerName.equals(getString(R.string.No_winner))){
            Tv_declaration_title.setText(getString(R.string.No_winner));
        }
        else{
            Tv_declaration_title.setText(winnerName+" Won!");
            String winnerSpeed=getIntent().getStringExtra("speed");
            AddToList(winnerName,winnerSpeed);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        winnerAdapter=new WinnerAdapter(this,winners);
        recyclerView.setAdapter(winnerAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void AddToList(String winnerName, String winnerSpeed) {
        if(winners.size()<10){
            winners.add(new Winners(winnerName,winnerSpeed));
        }
        else if(Integer.valueOf(winners.get(9).speed)>Integer.valueOf(winnerSpeed)){
            winners.get(9).playerName=winnerName;
            winners.get(9).speed=winnerSpeed;
        }
        Collections.sort(winners, new Comparator<Winners>() {
            @Override
            public int compare(Winners t2, Winners t1) {
                return Integer.valueOf(t2.getSpeed())-Integer.valueOf(t1.getSpeed());
            }
        });
    }
}