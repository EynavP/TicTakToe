package com.example.myapplication;

public class Winners {

    String playerName;
    String speed;

    public Winners(String playerName, String speed) {
        this.playerName = playerName;
        this.speed = speed;
    }

    public String getPlayerName() {return playerName;}

    public void setPlayerName(String playerName) {this.playerName = playerName;}

    public String getSpeed() {return speed;}

    public void setSpeed(String speed) {this.speed = speed;}
}
