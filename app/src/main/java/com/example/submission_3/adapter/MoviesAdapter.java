package com.example.submission_3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission_3.DetailActivity;
import com.example.submission_3.ListMovieGenre;
import com.example.submission_3.R;
import com.example.submission_3.moviespackage.Movie;

import java.util.ArrayList;
import java.util.List;

//Adapter Movies
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movies;
    private List<ListMovieGenre> allGenres;
    public MoviesAdapter(Context context, List<Movie> movies, List<ListMovieGenre> allGenres) {
        this.context = context;
        this.movies = movies;
        this.allGenres = allGenres;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_movies, viewGroup, false);
        return new ViewHolder(v);

    }
    private boolean genreExist;
    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder viewHolder, int i) {
        String poster = "https://image.tmdb.org/t/p/w500" + movies.get(viewHolder.getAdapterPosition()).getPosterPath();

        Glide.with(context)
                .load(poster)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(25)))
                .into(viewHolder.img);
        viewHolder.txtTitle.setText(movies.get(viewHolder.getAdapterPosition()).getTitle());
        viewHolder.txtDateStart.setText(movies.get(viewHolder.getAdapterPosition()).getReleaseDate().split("-")[0]);

        double score = movies.get(viewHolder.getAdapterPosition()).getVoteAverage() * 10;
        viewHolder.ratingBar.setRating((float) ((score * 5) / 100));
        viewHolder.rating.setText((movies.get(viewHolder.getAdapterPosition()).getVoteAverage()).toString());
        if(movies.get(viewHolder.getAdapterPosition()).getGenre()==null) {
            viewHolder.txtGenres.setText(movies.get(viewHolder.getAdapterPosition()).getGenres_string());
            genreExist = false;
        }
        else{
            viewHolder.txtGenres.setText(getGenres(movies.get(viewHolder.getAdapterPosition()).getGenre()));
            genreExist = true;
        }
        viewHolder.cardView.setOnClickListener((View v) ->{
            Intent detailIntent = new Intent(context, DetailActivity.class);
            if(genreExist) {
                detailIntent.putExtra(DetailActivity.GENRE_DATA, getGenres(movies.get(viewHolder.getAdapterPosition()).getGenre()));
            }
            else{
                Toast.makeText(context, movies.get(viewHolder.getAdapterPosition()).getGenres_string(), Toast.LENGTH_SHORT).show();
                detailIntent.putExtra(DetailActivity.GENRE_DATA, movies.get(viewHolder.getAdapterPosition()).getGenres_string());
            }
            detailIntent.putExtra(DetailActivity.EXTRA_DATA, movies.get(viewHolder.getAdapterPosition()));
            detailIntent.putExtra("check", true);
            context.startActivity(detailIntent);
        });



    }

    private String getGenres(List<Integer> genreIds) {

        List<String> movieGenres = new ArrayList<>();
        for (Integer genreId : genreIds)
        {
            for (ListMovieGenre genre : allGenres)
            {
                if (genre.getId().equals(genreId))
                {
                    movieGenres.add(genre.getName());
                    break;
                }
            }
        }
        return TextUtils.join(", ", movieGenres);
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
        TextView txtGenres;
        RatingBar ratingBar;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.rating_id);
            rating = itemView.findViewById(R.id.rating_tv);
            img = itemView.findViewById(R.id.img_id);
            txtDateStart = itemView.findViewById(R.id.date_id);
            txtTitle = itemView.findViewById(R.id.title_id);
            txtGenres = itemView.findViewById(R.id.genre_id);
            cardView = itemView.findViewById(R.id.movie_cv);
        }
    }

}
