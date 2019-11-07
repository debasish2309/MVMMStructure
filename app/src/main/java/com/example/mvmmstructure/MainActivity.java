package com.example.mvmmstructure;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvmmstructure.adapters.RecyclerAdapter;
import com.example.mvmmstructure.models.NicePlace;
import com.example.mvmmstructure.viewmodels.MainActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static  final String TAG = "Main Activity";
    private FloatingActionButton mfab;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progressbar);

        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        //Retrive data from Repository through ViewModel
        mMainActivityViewModel.init();

        mMainActivityViewModel.getNicePlaces().observe(this, new Observer<List<NicePlace>>() {
            @Override
            public void onChanged(List<NicePlace> nicePlaces) {
                mAdapter.notifyDataSetChanged();
            }
        });

        mMainActivityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    showProgressBar();
                }
                else{
                    hideProgressBar();
                    mRecyclerView.smoothScrollToPosition(mMainActivityViewModel.getNicePlaces().getValue().size()-1);
                }
            }
        });

        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivityViewModel.addNewValue(
                        new NicePlace(
                                "https://i.imgur.com/ZcLLrkY.jpg",
                                "Washington"
                        )
                );
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView(){
        mAdapter = new RecyclerAdapter(mMainActivityViewModel.getNicePlaces().getValue(),this);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }
}
