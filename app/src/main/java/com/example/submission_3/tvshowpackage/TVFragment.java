package com.example.submission_3.tvshowpackage;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.submission_3.R;
import com.example.submission_3.adapter.TvShowAdapter;
import com.example.submission_3.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private MyViewModel myViewModel;
    private TvShowAdapter adapter;
    private ProgressBar progressBar;
    private ImageView errorImg;

    private SwipeRefreshLayout swipeRefreshLayout;
    //To fetch data
    private Observer<List<TVShow>> getTvs = new Observer<List<TVShow>>() {
        @Override
        public void onChanged(@Nullable List<TVShow> tvShows) {
            if (tvShows != null) {
                adapter = new TvShowAdapter(getContext(), tvShows);
                mRecyclerView.setAdapter(adapter);
                showLoading(false);
            }

        }
    };

    private Observer<List<TVShow>> getSearchTv = new Observer<List<TVShow>>() {
        @Override
        public void onChanged(@Nullable List<TVShow> tvShows) {
            if (tvShows.size() > 0) {
                adapter = new TvShowAdapter(getContext(), tvShows);
                mRecyclerView.setAdapter(adapter);
                errorImg.setVisibility(View.GONE);
            } else {
                errorImg.setVisibility(View.VISIBLE);
            }
        }
    };

    public TVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tv, container, false);
        mRecyclerView = v.findViewById(R.id.rv_tvshow);
        progressBar = v.findViewById(R.id.progressBar);
        swipeRefreshLayout = v.findViewById(R.id.sr_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        errorImg = v.findViewById(R.id.error_img);
        showLoading(true);
        return v;
    }
    private void loadMovies() {
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.getTvs(getActivity()).observe(getActivity(), getTvs);
    }

    //To show progressbar or not
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    loadSearch(newText);
                }
                return true;
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadMovies();
    }
    private void loadSearch(String query) {
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.getSearchTv(query).observe(getActivity(), getSearchTv);
    }


    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        loadMovies();
        showLoading(false);
    }
}
