package com.example.rohit.moviesapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rohit on 10/19/2017.
 */

public class MovieArrayAdapter extends ArrayAdapter {

   private List<MoviesDetails> moviesDetailList;
    private int resourse;
    private Context context;


    public MovieArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MoviesDetails> moviesDetailList) {
        super(context, resource, moviesDetailList);

        this.context=context;
        this.moviesDetailList = moviesDetailList;
        this.resourse=resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MoviesDetails details= moviesDetailList.get(position);


        View view=LayoutInflater.from(context).inflate(resourse,parent,false);

        TextView movies_name= (TextView) view.findViewById(R.id.textView);
        ImageView image= (ImageView) view.findViewById(R.id.imageView);

        movies_name.setText(details.getOriginal_title());


        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w500/"+details.getBackdrop_path())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .resize(60,60)
                .into(image);





        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return moviesDetailList.get(position);
    }
}
