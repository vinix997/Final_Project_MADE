package com.example.submission_3.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission_3.DetailActivity;
import com.example.submission_3.ListTVGenre;
import com.example.submission_3.R;
import com.example.submission_3.tvshowpackage.TVShow;

import java.util.ArrayList;
import java.util.List;

//Adapter TVShow
public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private Context context;
    private List<TVShow> tvData;
    private List<ListTVGenre> allGenres;
    private boolean genreExist;
    public TvShowAdapter(Context context, List<TVShow> tvData, List<ListTVGenre> allGenres) {
        this.context = context;
        this.tvData = tvData;
        this.allGenres = allGenres;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_tvshows, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.ViewHolder viewHolder, int i) {
        String poster = "https://image.tmdb.org/t/p/w500" + tvData.get(viewHolder.getAdapterPosition()).getPosterPath();
        Glide.with(context)
                .load(poster)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(25)))
                .into(viewHolder.img);

        viewHolder.txtDateStart.setText(tvData.get(viewHolder.getAdapterPosition()).getFirstAirDate().split("-")[0]);
        viewHolder.txtTitle.setText(tvData.get(viewHolder.getAdapterPosition()).getName());
        double score = tvData.get(viewHolder.getAdapterPosition()).getVoteAverage() * 10;
        viewHolder.ratingBar.setRating((float) ((score * 5) / 100));
        viewHolder.rating.setText(tvData.get(viewHolder.getAdapterPosition()).getVoteAverage().toString());
        if(tvData.get(viewHolder.getAdapterPosition()).getGenreIds()==null)
        {
            viewHolder.txtGenre.setText(tvData.get(viewHolder.getAdapterPosition()).getGenres_string());
            genreExist = false;
        }
        else {
            viewHolder.txtGenre.setText(getGenres(tvData.get(viewHolder.getAdapterPosition()).getGenreIds()));
            genreExist = true;
        }
        viewHolder.cardView.setOnClickListener((View v) ->
        {
            Intent detailIntent = new Intent(context, DetailActivity.class);
            if(genreExist)
            {
                detailIntent.putExtra(DetailActivity.GENRE_DATA, getGenres(tvData.get(viewHolder.getAdapterPosition()).getGenreIds()));
            }
            else
            {
                detailIntent.putExtra(DetailActivity.GENRE_DATA, tvData.get(viewHolder.getAdapterPosition()).getGenres_string());
            }
            detailIntent.putExtra(DetailActivity.EXTRA_DATA, tvData.get(viewHolder.getAdapterPosition()));
            detailIntent.putExtra("check", false);
            context.startActivity(detailIntent);
        });


    }
    private String getGenres(List<Integer> genreIds) {

        List<String> tvGenres = new ArrayList<>();
        for (Integer genreId : genreIds)
        {
            for (ListTVGenre genre : allGenres)
            {
                if (genre.getId().equals(genreId))
                {
                    tvGenres.add(genre.getName());
                    break;
                }
            }
        }
        return TextUtils.join(", ", tvGenres);
    }
    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtDateStart;
        TextView txtTitle;
        TextView rating;
        RatingBar ratingBar;
        TextView txtGenre;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDateStart = itemView.findViewById(R.id.startDate_id);
            cardView = itemView.findViewById(R.id.tv_cv);
            txtTitle = itemView.findViewById(R.id.title_id);
            img = itemView.findViewById(R.id.img_id);
            rating = itemView.findViewById(R.id.rating_tv);
            txtGenre = itemView.findViewById(R.id.genres_id);
            ratingBar = itemView.findViewById(R.id.rating_id);
        }
    }
}
