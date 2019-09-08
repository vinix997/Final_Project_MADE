package com.example.submission_3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission_3.moviespackage.Movie;
import com.example.submission_3.room.DBRoom;
import com.example.submission_3.tvshowpackage.TVShow;


//Activity to show detail of the movie
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";
    ImageView detailPhoto;
    TextView detailTitle;
    TextView detailDate;
    TextView detailDesc;
    TextView ratingDesc;
    TextView detailStartDate;
    TextView detailRating;
    TextView detailPopularity;
    TextView detailLanguage;
    ImageView detailBackdrop;
    Movie movie;
    TVShow tvShow;
    private Menu menu;
    private boolean favoriteChecker = false;
    private boolean check;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check = getIntent().getBooleanExtra("check", true);
        if (check) {
            showMovie();
        } else {
            showTV();
        }
        getFavorite();


    }

    private void showMovie() {
        setContentView(R.layout.detail_movies);
        movie = getIntent().getParcelableExtra(EXTRA_DATA);
        String poster = "https://image.tmdb.org/t/p/original" + movie.getPosterPath();
        detailPhoto = findViewById(R.id.img_id);
        Glide.with(this)
                .load(poster)
                .apply(new RequestOptions().override(300, 300))
                .into(detailPhoto);
        detailBackdrop = findViewById(R.id.backdrop_id);
        String backdrop = "https://image.tmdb.org/t/p/w780" + movie.getBackdropPath();
        Glide.with(this)
                .load(backdrop)
                .apply(new RequestOptions().override(300, 300))
                .into(detailBackdrop);
        detailTitle = findViewById(R.id.title_id);
        detailTitle.setText(movie.getTitle());
        detailDate = findViewById(R.id.date_id);
        String date = String.format(getResources().getString(R.string.date));
        System.out.println(date);
        detailDate.setText(date + " " + movie.getReleaseDate());
        detailDesc = findViewById(R.id.desc_id);
        String overview = String.format(getResources().getString(R.string.overview));
        detailDesc.setText(overview + "\n" + movie.getOverview());
        ratingDesc = findViewById(R.id.rating_id);
        String rating = String.format(getResources().getString(R.string.rating));
        ratingDesc.setText(rating + " " + movie.getVoteAverage().toString());
        getSupportActionBar().setTitle(movie.getTitle());

    }

    private void addToOrRemoveFromFav() {
        if (favoriteChecker) {
            if (check) {
                DBRoom.getInstance(this).movieDao().delMovie(movie);
                title = movie.getTitle();
            } else {
                DBRoom.getInstance(this).tvDao().delTv(tvShow);
                title = tvShow.getName();
            }
            Toast.makeText(getApplicationContext(), "Removed " + title + " from favorite", Toast.LENGTH_SHORT).show();

        } else {
            if (check) {
                DBRoom.getInstance(this).movieDao().addMovie(movie);
                title = movie.getTitle();
            } else {
                DBRoom.getInstance(this).tvDao().addTv(tvShow);
                title = tvShow.getName();
            }
            Toast.makeText(getApplicationContext(), "Added " + title + " to favorite", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeImage() {
        if (favoriteChecker)
            menu.getItem(0).setIcon(R.drawable.ic_star_black_24dp);
        else menu.getItem(0).setIcon(R.drawable.ic_star_blue_24dp);
    }

    private void showTV() {
        setContentView(R.layout.detail_tvshows);
        tvShow = getIntent().getParcelableExtra(EXTRA_DATA);

        detailPhoto = findViewById(R.id.img_id);
        String poster = "https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath();
        Glide.with(this)
                .load(poster)
                .apply(new RequestOptions().override(300, 300))
                .into(detailPhoto);
        detailBackdrop = findViewById(R.id.backdrop_id);
        String backdrop = "https://image.tmdb.org/t/p/original" + tvShow.getBackdropPath();
        Glide.with(this)
                .load(backdrop)
                .apply(new RequestOptions().override(300, 300))
                .into(detailBackdrop);
        detailTitle = findViewById(R.id.title_id);
        detailTitle.setText(tvShow.getName());
        detailPopularity = findViewById(R.id.pop_id);
        String popularity = String.format(getResources().getString(R.string.popularity));
        detailPopularity.setText(popularity + " " + tvShow.getPopularity().toString());
        detailRating = findViewById(R.id.rating_id);
        String rating = String.format(getResources().getString(R.string.rating));
        detailRating.setText(rating + " " + tvShow.getVoteAverage().toString());
        detailDesc = findViewById(R.id.desc_id);
        String overview = String.format(getResources().getString(R.string.overview));
        detailDesc.setText(overview + "\n" + tvShow.getOverview());
        detailStartDate = findViewById(R.id.startDate_id);
        String date = String.format(getResources().getString(R.string.fAirDate));
        detailStartDate.setText(date + " " + tvShow.getFirstAirDate());
        detailLanguage = findViewById(R.id.language_id);
        String lang = String.format(getResources().getString(R.string.oriLang));
        detailLanguage.setText(lang + " " + tvShow.getOriginalLanguage().toUpperCase());
        getSupportActionBar().setTitle(tvShow.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu item) {
        getMenuInflater().inflate(R.menu.fav_menu, item);
        menu = item;
        changeImage();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        addToOrRemoveFromFav();
        favoriteChecker = !favoriteChecker;
        changeImage();
        return super.onOptionsItemSelected(item);
    }

    private void getFavorite() {
        if (check) {
            favoriteChecker = DBRoom.getInstance(this)
                    .movieDao()
                    .findMovieById(movie.getId()) != null;
        } else {
            favoriteChecker = DBRoom.getInstance(this)
                    .tvDao()
                    .findTvById(tvShow.getId()) != null;
        }
    }


}
