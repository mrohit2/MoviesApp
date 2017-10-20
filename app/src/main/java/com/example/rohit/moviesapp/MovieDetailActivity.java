package com.example.rohit.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView image;
  TextView title_view,reslease_date_view,rating_view,overview_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_layout);

        image= (ImageView) findViewById(R.id.poster_view);
        title_view= (TextView) findViewById(R.id.title_view);
        reslease_date_view= (TextView) findViewById(R.id.reslease_date_view);
        rating_view= (TextView) findViewById(R.id.rating_view);
        overview_textView= (TextView) findViewById(R.id.overview_textView);

        MoviesDetails details= (MoviesDetails) getIntent().getSerializableExtra("MOVIES_DETAIL");

        if(details!=null){
            Picasso.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"+details.getBackdrop_path())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(image);
            title_view.setText(details.getOriginal_title());
            reslease_date_view.setText(details.getRelease_date());
            rating_view.setText(Double.toString(details.getVote_average()));
            overview_textView.setText(details.getOverview());


        }
    }
}
