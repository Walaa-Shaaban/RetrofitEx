package com.example.walaa.retrofitapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.viewHolder>{

    private List<User> list_user;

    public ContentAdapter(List<User> list_user){
        this.list_user = list_user;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_single_layout, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        viewHolder.txtUsername.setText(list_user.get(i).getName());
        if (list_user.get(i).getImage() != "default") {
            Picasso.with(viewHolder.img.getContext())
                    .load("https://retrofit-walaashaaban.c9users.io/retrofit/" + list_user.get(i).getImage())
                    .error(R.drawable.profile)
                    .into(viewHolder.img);
        }
    }

    @Override
    public int getItemCount() {
        return list_user.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
        //
        ImageView img;
        TextView txtUsername;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.user_single_image);
            txtUsername = (TextView) itemView.findViewById(R.id.user_single_name);
        }
    }
}
