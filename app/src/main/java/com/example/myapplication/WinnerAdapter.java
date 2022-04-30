package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WinnerAdapter extends RecyclerView.Adapter<WinnerAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Winners> winnersList;

    public WinnerAdapter(Context context, List<Winners> winnersList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.winnersList = winnersList;
    }

    @NonNull
    @Override
    public WinnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=layoutInflater.inflate(R.layout.recycler_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        String playerName=winnersList.get(i).getPlayerName();
        holder.playerName.setText(playerName);

        String Speed=winnersList.get(i).getSpeed();
        holder.Speed.setText(Speed);

        String playerRank=String.valueOf(i+1);
        holder.playerRank.setText(playerRank);
    }

    @Override
    public int getItemCount() {
        return winnersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView playerName,Speed,playerRank;

        public ViewHolder(@NonNull View itemview){
            super(itemview);
            playerName=itemview.findViewById(R.id.Tv_playerName);
            Speed=itemview.findViewById(R.id.Tv_playerSpeed);
            playerRank=itemview.findViewById(R.id.Tv_rank);

        }
    }

}
