package com.gurusvasti.thereader;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<homedata> lstinfo;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.Home_recyclerView);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(),lstinfo);
        myrecyclerview.setLayoutManager((new LinearLayoutManager((getActivity()))));
        myrecyclerview.setAdapter(recyclerAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstinfo = new   ArrayList<>();
        lstinfo.add(new homedata("This is am item number 1 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 2 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 3 foe this topic we should " +
                "This is am item number 6 foe this topic we should try to understand" +
                "This is am item number 6 foe this topic we should try to understand" +
                "This is am item number 6 foe this topic we should try to understand try to understand "));
        lstinfo.add(new homedata("This is am item number 4 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 5 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 6 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 7 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 8 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 1 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 2 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 3 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 4 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 5 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 6 foe this topic we should try to understand" +
                "This is am item number 6 foe this topic we should try to understand" +
                "This is am item number 6 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 7 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 8 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 1 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 2 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 3 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 4 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 5 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 6 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 7 foe this topic we should try to understand "));
        lstinfo.add(new homedata("This is am item number 8 foe this topic we should try to understand "));


    }
}
