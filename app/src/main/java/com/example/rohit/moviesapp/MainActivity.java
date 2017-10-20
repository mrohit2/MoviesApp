package com.example.rohit.moviesapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.movies_list_view);
        listView.setOnItemClickListener(this);


      new checkConnectionStatus().execute("https://api.themoviedb.org/3/movie/popular?api_key=48144881d5b6c11339b961aaa70b7ec4&language=en-US&page=1");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent =new Intent(this,MovieDetailActivity.class);
         intent.putExtra("MOVIES_DETAIL",(MoviesDetails)parent.getItemAtPosition(position));
        startActivity(intent);
    }


    class checkConnectionStatus extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... params) {
            URL url=null;

            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                InputStream inputStream =urlConnection.getInputStream();
                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));

                String s = bufferedReader.readLine();
                bufferedReader.close();

               return s;
            } catch (IOException e) {
                Log.e("Error",e.getMessage(),e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
              JSONObject  jsonObject = new JSONObject(s);

                ArrayList<MoviesDetails> moviesList = new ArrayList<>();


                JSONArray jsonArray =jsonObject.getJSONArray("results");

                if(jsonArray!=null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        MoviesDetails moviesDetails = new MoviesDetails();
                        moviesDetails.setOriginal_title(object.getString("original_title"));
                        moviesDetails.setVote_average(object.getDouble("vote_average"));
                        moviesDetails.setOverview(object.getString("overview"));
                        moviesDetails.setBackdrop_path(object.getString("backdrop_path"));
                        moviesDetails.setRelease_date(object.getString("release_date"));
                        moviesList.add(moviesDetails);


                    }
                    MovieArrayAdapter movieArrayAdapter=new MovieArrayAdapter(MainActivity.this,R.layout.movies_list,moviesList);
                    listView.setAdapter(movieArrayAdapter);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
