package com.example.movie_review_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.movie_review_app.adapter.MoviesAdapter;
import com.example.movie_review_app.api.Client;
import com.example.movie_review_app.api.Service;
import com.example.movie_review_app.model.Movie;
import com.example.movie_review_app.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesPage extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView recyclerView;
    private MoviesAdapter recyclerAdaptor;
    private List<Movie> moviesList;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    private AppCompatActivity activity = MoviesPage.this;
    public static final String LOG_TAG = MoviesAdapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviespage);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);




    }

    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;

    }


    private void initViews() {
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Movies...");
        pd.setCancelable(false);
        pd.show();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        moviesList = new ArrayList<>();
        recyclerAdaptor = new MoviesAdapter(getApplicationContext(), this.moviesList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdaptor);
        recyclerAdaptor.notifyDataSetChanged();


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.main_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(MoviesPage.this, "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        checkSortOrder();

    }

    private void initViews2() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        moviesList = new ArrayList<>();
        recyclerAdaptor = new MoviesAdapter(this, moviesList);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdaptor);
        recyclerAdaptor.notifyDataSetChanged();





}
    private void loadJSON() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "please obtain API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }

            Client client = new Client();
            Service apiservice =
                    Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiservice.getPopularMovies();
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                   List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MoviesPage.this,"Error Fetching Data!",Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }
    private void loadJSON1() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "please obtain API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }

            Client client = new Client();
            Service apiservice =
                    Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiservice.getTopRatedMovies();
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MoviesPage.this,"Error Fetching Data!",Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }
 //  private void loadJSON2() {

//        try {
//            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
//                Toast.makeText(getApplicationContext(), "please obtain API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
//                pd.dismiss();
//                return;
 //       }
//
//            Client client = new Client();
//            Service apiservice =
//                    Client.getClient().create(Service.class);
//            Call<MoviesResponse> call = apiservice.getLatestMovies();
//            call.enqueue(new Callback<MoviesResponse>() {
//                @Override
//                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                    List<Movie> movies = response.body().getResults();
//                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
//                    recyclerView.smoothScrollToPosition(0);
//                    if (swipeContainer.isRefreshing()) {
//                        swipeContainer.setRefreshing(false);
//                    }
//                    pd.dismiss();
//                }
//
//                @Override
//                public void onFailure(Call<MoviesResponse> call, Throwable t) {
//                    Log.d("Error", t.getMessage());
//                    Toast.makeText(MoviesPage.this,"Error Fetching Data!",Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//        } catch (Exception e) {
//            Log.d("Error", e.getMessage());
//            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//
//        }
//
//    }

    private void loadJSON3() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "please obtain API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }

            Client client = new Client();
            Service apiservice =
                    Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiservice.getNowPlayingMovies();
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MoviesPage.this,"Error Fetching Data!",Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }
    private void loadJSON4() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "please obtain API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }

            Client client = new Client();
            Service apiservice =
                    Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiservice.getSouthMovies();
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MoviesPage.this,"Error Fetching Data!",Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
       Log.d(LOG_TAG, "Preferences updated");
       checkSortOrder();

    }

    private void checkSortOrder(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.prefSortOrderKey),
                this.getString(R.string.prefMostPopular)
        );
        if (sortOrder.equals(this.getString(R.string.prefMostPopular))) {
            Log.d(LOG_TAG, "Sorting by most popular");
            loadJSON();
        }else if (sortOrder.equals(this.getString(R.string.favorites))){
            Log.d(LOG_TAG, "Sorting by favorites");
            initViews2();
        }else {
            Log.d(LOG_TAG, "Sorting by vote average");
            loadJSON1();
        }
        if (sortOrder.equals("Latest Movies")){
            Log.d(LOG_TAG, "Sorting by release dates");
//            loadJSON2();
        }
        if (sortOrder.equals("Best Of Oscars")){
           loadJSON3();
        }
        if (sortOrder.equals("South Movies")){
            loadJSON4();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
       if (moviesList.isEmpty()){
            checkSortOrder();
        } else {
        }
    }


        }
















