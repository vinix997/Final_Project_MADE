package com.example.submission_3;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission_3.moviespackage.Movie;
import com.example.submission_3.room.DBRoom;
import com.example.submission_3.tvshowpackage.TVShow;


//Activity to show detail of the movie
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";
    public static final String GENRE_DATA = "genre_data";
    public static final String GENRE_LIST = "genre_list";
    ImageView detailPhoto;
    TextView detailTitle;
    TextView detailDate;
    TextView detailDesc;
    TextView ratingDesc;
    TextView detailStartDate;
    TextView detailRating;
    TextView detailLanguage;
    ImageView detailBackdrop;
    TextView detailGenre;
    RatingBar detailBar;
    Movie movie;
    VideoView videoView;
    TVShow tvShow;
    String genre;
    private Menu menu;
    private boolean favoriteChecker = false;
    private boolean check;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        genre = getIntent().getStringExtra(GENRE_DATA);
        detailGenre = findViewById(R.id.genre_id);
        detailGenre.setText("Genres: "+ genre);
        detailBar = findViewById(R.id.rating_id);
        double score = movie.getVoteAverage() * 10;
        detailBar.setRating((float) (score *5 )/100);
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
        ratingDesc = findViewById(R.id.rating_tv);
        ratingDesc.setText(movie.getVoteAverage().toString());
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
                movie.setGenres_string(genre);
                DBRoom.getInstance(this).movieDao().addMovie(movie);
                title = movie.getTitle();
            } else {
                tvShow.setGenres_string(genre);
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
        genre = getIntent().getStringExtra(GENRE_DATA);
        tvShow = getIntent().getParcelableExtra(EXTRA_DATA);
        detailGenre = findViewById(R.id.genre_id);
        detailBar = findViewById(R.id.rating_id);
        double score = tvShow.getVoteAverage() * 10;
        detailBar.setRating((float) (score * 5) /100);

        detailGenre.setText("Genres: "+ genre);
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
        detailRating = findViewById(R.id.rating_tv);
        detailRating.setText(tvShow.getVoteAverage().toString());
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_fav:
            addToOrRemoveFromFav();
            favoriteChecker = !favoriteChecker;
            changeImage();
            break;
            case android.R.id.home:
                finish();
                break;

        }
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
