package com.example.movie_review_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movie_review_app.adapter.MoviesAdapter;
import com.example.movie_review_app.api.Client;
import com.example.movie_review_app.api.Service;
import com.example.movie_review_app.model.Movie;
import com.example.movie_review_app.model.MoviesResponse;
import com.google.android.material.navigation.NavigationView;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity activity = HomeActivity.this;

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /*----------------------Navigation Drawer Menu-------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        loadPopular();
        loadTopRated();
        loadNowPlaying();


    }

    private void loadPopular() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getPopularMovies();
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            MoviesAdapter firstAdapter = new MoviesAdapter(getApplicationContext(), movies);
                            MultiSnapRecyclerView firstRecyclerView = (MultiSnapRecyclerView) findViewById(R.id.first_recycler_view);
                            LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            firstRecyclerView.setLayoutManager(firstManager);
                            firstRecyclerView.setAdapter(firstAdapter);
                        }
                    }

                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Error fetching trailer data", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTopRated() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }

            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getTopRatedMovies();
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            List<Movie> movies = response.body().getResults();
                            MoviesAdapter secondAdapter = new MoviesAdapter(getApplicationContext(), movies);
                            MultiSnapRecyclerView secondRecyclerView = (MultiSnapRecyclerView) findViewById(R.id.second_recycler_view);
                            LinearLayoutManager secondManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            secondRecyclerView.setLayoutManager(secondManager);
                            secondRecyclerView.setAdapter(secondAdapter);
                        }
                    }

                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Error fetching trailer data", Toast.LENGTH_SHORT).show();
                }

            });


        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void loadNowPlaying(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
            } else {
                Client Client = new Client();
                Service apiService = Client.getClient().create(Service.class);
                Call<MoviesResponse> call = apiService.getNowPlayingMovies();
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Movie> movies = response.body().getResults();
                                MoviesAdapter fourthAdapter = new MoviesAdapter(getApplicationContext(), movies);
                                MultiSnapRecyclerView fourthRecyclerView = (MultiSnapRecyclerView) findViewById(R.id.fourth_recycler_view);
                                LinearLayoutManager fourthManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                               fourthRecyclerView.setLayoutManager(fourthManager);
                                fourthRecyclerView.setAdapter(fourthAdapter);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                        Toast.makeText(getApplicationContext(), "Error fetching trailer data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }


        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent i = new Intent(HomeActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case  R.id.nav_share:
            Toast.makeText(this,"Share", Toast.LENGTH_SHORT).show();
            break;
            case R.id.nav_logout:
                Intent intent = new Intent(HomeActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}





