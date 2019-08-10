package com.example.submission_3.favorite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.submission_3.R;
import com.example.submission_3.adapter.MoviesAdapter;
import com.example.submission_3.moviespackage.Movie;
import com.example.submission_3.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private MoviesAdapter adapter;
    private MyViewModel myViewModel;
    private ImageView imgError;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Movie> listmovie = new ArrayList<>();
    private Observer<List<Movie>> getFavMovies = new Observer<List<Movie>>() {
        @Override
        public void onChanged(@Nullable List<Movie> movies) {
            if (movies.size() > 0) {
                adapter = new MoviesAdapter(getContext(), movies);
                mRecyclerView.setAdapter(adapter);
                imgError.setVisibility(View.GONE);
            } else {
                adapter = new MoviesAdapter(getContext(),listmovie);
                mRecyclerView.setAdapter(adapter);
                imgError.setVisibility(View.VISIBLE);
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies, container, false);
        mRecyclerView = v.findViewById(R.id.rv_movies);
        swipeRefreshLayout = v.findViewById(R.id.sr_layout);
        imgError = v.findViewById(R.id.error_img);
        swipeRefreshLayout.setOnRefreshListener(this);
        return v;
    }

    private void loadFavoriteData() {
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.getFavList(getActivity()).observe(getActivity(), getFavMovies);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        loadFavoriteData();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
       loadFavoriteData();
    }
}
