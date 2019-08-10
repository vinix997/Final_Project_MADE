package com.example.submission_3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission_3.DetailActivity;
import com.example.submission_3.R;
import com.example.submission_3.moviespackage.Movie;

import java.util.List;

//Adapter Movies
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_movies, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder viewHolder, int i) {
        String poster = "https://image.tmdb.org/t/p/w500" + movies.get(viewHolder.getAdapterPosition()).getPosterPath();

        Glide.with(context)
                .load(poster)
                .apply(new RequestOptions().override(300, 300))
                .into(viewHolder.img);
        viewHolder.txtTitle.setText(movies.get(viewHolder.getAdapterPosition()).getTitle());
        viewHolder.txtDateStart.setText(movies.get(viewHolder.getAdapterPosition()).getReleaseDate());
        viewHolder.rating.setText((movies.get(viewHolder.getAdapterPosition()).getVoteAverage()).toString());
        viewHolder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.EXTRA_DATA, movies.get(viewHolder.getAdapterPosition()));
                detailIntent.putExtra("check", true);
                context.startActivity(detailIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtDateStart;
        TextView rating;
        TextView txtTitle;
        ImageButton imgButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgButton = itemView.findViewById(R.id.button_id);
            rating = itemView.findViewById(R.id.rating_id);
            img = itemView.findViewById(R.id.img_id);
            txtDateStart = itemView.findViewById(R.id.date_id);
            txtTitle = itemView.findViewById(R.id.title_id);

        }
    }

}
