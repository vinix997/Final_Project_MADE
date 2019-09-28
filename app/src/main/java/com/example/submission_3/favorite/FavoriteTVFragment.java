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

import com.example.submission_3.ListTVGenre;
import com.example.submission_3.R;
import com.example.submission_3.adapter.TvShowAdapter;
import com.example.submission_3.tvshowpackage.TVShow;
import com.example.submission_3.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTVFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private TvShowAdapter adapter;
    private MyViewModel myViewModel;
    private List<TVShow> list = new ArrayList<>();
    private ImageView imageView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ListTVGenre> listGenres;
    private List<ListTVGenre> listgenres = new ArrayList<>();

    private Observer<List<ListTVGenre>> getListGenre = new Observer<List<ListTVGenre>>() {
        @Override
        public void onChanged(@Nullable List<ListTVGenre> listTVGenres) {
            listGenres = listTVGenres;
        }
    };

    private Observer<List<TVShow>> getFavTVShows = new Observer<List<TVShow>>() {
        @Override
        public void onChanged(@Nullable List<TVShow> tvShows) {
            if (tvShows.size() > 0) {
                adapter = new TvShowAdapter(getContext(), tvShows, listGenres);
                mRecyclerView.setAdapter(adapter);
                imageView.setVisibility(View.GONE);
            } else {
                adapter = new TvShowAdapter(getContext(), list, listgenres);
                mRecyclerView.setAdapter(adapter);
                imageView.setVisibility(View.VISIBLE);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tv, container, false);
        mRecyclerView = v.findViewById(R.id.rv_tvshow);
        imageView = v.findViewById(R.id.error_img);
        swipeRefreshLayout = v.findViewById(R.id.sr_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        return v;
    }

    private void loadFavoriteData() {
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.getFavListTv(getActivity()).observe(getActivity(), getFavTVShows);
    }
    private void loadGenres()
    {
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.getTVGenre().observe(getActivity(),getListGenre);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadGenres();
        loadFavoriteData();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        loadGenres();
        loadFavoriteData();

    }
}
