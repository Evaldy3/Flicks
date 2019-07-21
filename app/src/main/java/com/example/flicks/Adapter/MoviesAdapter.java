package com.example.flicks.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;
import com.example.flicks.DetailActivity;
import com.example.flicks.R;
import com.example.flicks.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MoviesAdapter extends  RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("smile","onCreateViewHolder");
       View view= LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("smile","onBindViewHolder" + position);
        Movie movie = movies.get(position);
        // Bind the movie data into the view holder
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ConstraintLayout container;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);

        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl = movie.getPosterPath();
            // Reference the backdrop path if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
            }

            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop

            Glide.with(context)
                    .load(imageUrl)
                    .apply(new RequestOptions().transform(new RoundedCornersTransformation(radius, margin)))
                    .into(ivPoster);


            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });

        }
    }
}
