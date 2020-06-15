package com.example.onceuponabook.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponabook.Adapters.ListRecyclerAdapter;
import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookCategoriesFragment extends Fragment {

    List<BookDTO> lstItem;

    public BookCategoriesFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_categories, container, false);

        //get a reference to recycler view
        RecyclerView recyclerView=rootView.findViewById(R.id.recycler_categories);

        //set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //the data for the recycler view
        lstItem=new ArrayList<>();
        lstItem.add(new BookDTO("Art & Photography","https://images.unsplash.com/photo-1541701494587-cb58502866ab?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=600&q=60"));
        lstItem.add(new BookDTO("Biography","https://static.digit.in/default/09ff826ec214c02674978d45487584b0e99ef69a.jpeg?tr=w-1200"));
        lstItem.add(new BookDTO("Children's Books","https://images.unsplash.com/photo-1487521916606-6ba43a72537c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=782&q=80"));
        lstItem.add(new BookDTO("Crafts & Hobbies","https://images.unsplash.com/photo-1504036648358-adaba3e9c023?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80"));
        lstItem.add(new BookDTO("Crime & Thriller","https://images.unsplash.com/photo-1548754218-e9ef33578d04?ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        lstItem.add(new BookDTO("Fiction","https://images.unsplash.com/photo-1590959651373-a3db0f38a961?ixlib=rb-1.2.1&auto=format&fit=crop&w=637&q=80"));
        lstItem.add(new BookDTO("Food & Drink","https://images.unsplash.com/photo-1496412705862-e0088f16f791?ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        lstItem.add(new BookDTO("Graphic Novels, Anime & Manga","https://images.unsplash.com/photo-1565991344790-ac620cb64b91?ixlib=rb-1.2.1&auto=format&fit=crop&w=693&q=80"));
        lstItem.add(new BookDTO("History & Archaeology","https://images.unsplash.com/photo-1505664194779-8beaceb93744?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80"));
        lstItem.add(new BookDTO("Mind, Body & Spirit","https://images.unsplash.com/photo-1526750054187-b02dbcdafab6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=847&q=80"));
        lstItem.add(new BookDTO("Science Fiction, Fantasy & Horror","https://images.unsplash.com/photo-1575566884706-d605dcdfab17?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=889&q=80"));

        //create an instance of ItemsRecyclerAdapter
        ListRecyclerAdapter myAdapter=new ListRecyclerAdapter(getActivity(),lstItem);

        //set adapter
        recyclerView.setAdapter(myAdapter);

        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;

    }

}
