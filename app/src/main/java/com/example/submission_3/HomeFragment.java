package com.example.submission_3;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.submission_3.adapter.ViewAdapter;
import com.example.submission_3.moviespackage.MoviesFragment;
import com.example.submission_3.tvshowpackage.TVFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ViewAdapter viewAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPagerHome);
        tabLayout = view.findViewById(R.id.tl_home);

        setViewPager(viewPager);


    }

    private void setViewPager(ViewPager viewPager) {
        viewAdapter = new ViewAdapter(getChildFragmentManager());

        viewAdapter.addTabFragment(new MoviesFragment(), getString(R.string.movies));
        viewAdapter.addTabFragment(new TVFragment(), getString(R.string.tv_show));
        viewPager.setAdapter(viewAdapter);
    }

}
