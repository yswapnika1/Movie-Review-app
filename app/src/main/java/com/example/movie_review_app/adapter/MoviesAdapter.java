package com.example.movie_review_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_review_app.DetailActivity;
import com.example.movie_review_app.R;
import com.example.movie_review_app.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;



    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
        private Context mContext;
        private List<Movie> movieList;


        public MoviesAdapter(Context mContext, List<Movie> movieList) {
            this.mContext = mContext;
            this.movieList = movieList;
        }
        @Override
        public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
                  View view = LayoutInflater.from(viewGroup.getContext())
                          .inflate(R.layout.movie_card,viewGroup,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, int i){
             viewHolder.title.setText(movieList.get(i).getOriginalTitle() );
             String vote = Double.toString(movieList.get(i).getVoteAverage());
             viewHolder.userrating.setText(vote);
             String url = "https://image.tmdb.org/t/p/w500" + movieList.get(i).getPosterPath();
             Log.e("LINK",url);
             Picasso.get()
                     .load(url)
                     .placeholder(R.drawable.load)
                     .into(viewHolder.thumbnail);
        }
        @Override
        public int getItemCount(){
            return movieList.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView title, userrating;
            public ImageView thumbnail;
            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                userrating = (TextView) view.findViewById(R.id.userrating);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();

                        Toast.makeText(v.getContext(), "You Clicked " + pos, Toast.LENGTH_SHORT).show();
                        if (pos >= 0){
                            Movie clickedDataItem = movieList.get(pos);
                            Intent intent = new Intent(mContext, DetailActivity.class);
                            intent.putExtra("original_title", clickedDataItem.getOriginalTitle());
                            intent.putExtra("poster_path", clickedDataItem.getPosterPath());
                            intent.putExtra("overview",clickedDataItem.getOverview());
                            intent.putExtra("vote_average",Double.toString(clickedDataItem.getVoteAverage()));
                            intent.putExtra("release_date",clickedDataItem.getReleaseDate());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                            Toast.makeText(v.getContext(), "You Clicked" + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }



    }
