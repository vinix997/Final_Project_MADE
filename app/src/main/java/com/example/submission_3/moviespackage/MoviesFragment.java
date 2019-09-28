package com.example.submission_3.moviespackage;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.submission_3.ListMovieGenre;
import com.example.submission_3.R;
import com.example.submission_3.adapter.MoviesAdapter;
import com.example.submission_3.viewmodel.MyViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private MoviesAdapter adapter;
    private MyViewModel myViewModel;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ListMovieGenre> listGenres;
    //To fetch data
    private Observer<List<ListMovieGenre>> getGenres = new Observer<List<ListMovieGenre>>() {
       @Override
       public void onChanged(@Nullable List<ListMovieGenre> listMovieGenres) {
           listGenres = listMovieGenres;
       }
   };
    private Observer<List<Movie>> getMovies = new Observer<List<Movie>>() {
        @Override
        public void onChanged(@Nullable List<Movie> movies) {
            if (movies.size()>0) {

                adapter = new MoviesAdapter(getContext(), movies, listGenres);
                mRecyclerView.setAdapter(adapter);

                showLoading(false);
            }

        }
    };
    private Observer<List<Movie>> getSearch = new Observer<List<Movie>>() {
        @Override
        public void onChanged(@Nullable List<Movie> list) {
            if (list.size() > 0) {
                adapter = new MoviesAdapter(getContext(), list, listGenres);
                mRecyclerView.setAdapter(adapter);
               // imageView.setVisibility(View.GONE);
            } else {
              //  imageView.setVisibility(View.VISIBLE);
            }
        }
    };

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies, container, false);
        mRecyclerView = v.findViewById(R.id.rv_movies);
        progressBar = v.findViewById(R.id.progressBar);
     //   imageView = v.findViewById(R.id.error_img);
        swipeRefreshLayout = v.findViewById(R.id.sr_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        showLoading(true);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadGenre();
        loadMovies();
    }

    private void loadMovies() {

            myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
            myViewModel.getMovies(getActivity()).observe(getActivity(), getMovies);

    }

    private void loadSearch(String query) {
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.getSearch(query).observe(getActivity(), getSearch);
    }

    private void loadGenre()
    {
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.getGenreList().observe(getActivity() ,getGenres);
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
                if (newText.isEmpty() == false)
                    loadSearch(newText);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        loadGenre();
        loadMovies();
        showLoading(false);
    }
}




