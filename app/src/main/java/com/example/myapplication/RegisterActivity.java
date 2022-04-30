package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText player1_name,player2_name;
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        player1_name=findViewById(R.id.Et_player1);
        player2_name=findViewById(R.id.et_player2);
        btnStart=findViewById(R.id.btnStart);
        btnStart.setOnClickListener(view -> {
            if(isValidNames()){
                Intent intent=new Intent(this,GameActivity.class);
                intent.putExtra("player1",player1_name.getText().toString());
                intent.putExtra("player2",player2_name.getText().toString());
                startActivity(intent);
            }
        });
        
    }

    private boolean isValidNames() {
        if(player1_name.getText().length()==0 || player2_name.getText().length()==0){
            Toast.makeText(this,"Must enter players names",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}