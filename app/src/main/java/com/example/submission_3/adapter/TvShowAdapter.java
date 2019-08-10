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
import com.example.submission_3.tvshowpackage.TVShow;

import java.util.List;

//Adapter TVShow
public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private Context context;
    private List<TVShow> tvData;

    public TvShowAdapter(Context context, List<TVShow> tvData) {
        this.context = context;
        this.tvData = tvData;
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
                .apply(new RequestOptions().override(300, 300))
                .into(viewHolder.img);

        viewHolder.txtDateStart.setText(tvData.get(viewHolder.getAdapterPosition()).getFirstAirDate());
        viewHolder.txtTitle.setText(tvData.get(viewHolder.getAdapterPosition()).getName());
        viewHolder.txtPopularity.setText(tvData.get(viewHolder.getAdapterPosition()).getPopularity().toString());
        viewHolder.txtLanguage.setText(tvData.get(viewHolder.getAdapterPosition()).getOriginalLanguage().toUpperCase());
        viewHolder.rating.setText(tvData.get(viewHolder.getAdapterPosition()).getVoteAverage().toString());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.EXTRA_DATA, tvData.get(viewHolder.getAdapterPosition()));
                detailIntent.putExtra("check", false);
                context.startActivity(detailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtDateStart;
        TextView txtLanguage;
        TextView txtTitle;
        TextView txtPopularity;
        TextView rating;
        ImageButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDateStart = itemView.findViewById(R.id.startDate_id);
            txtLanguage = itemView.findViewById(R.id.language_id);
            txtPopularity = itemView.findViewById(R.id.pop_id);
            txtTitle = itemView.findViewById(R.id.title_id);
            img = itemView.findViewById(R.id.img_id);
            rating = itemView.findViewById(R.id.rating_id);
            button = itemView.findViewById(R.id.button_id);
        }
    }
}
