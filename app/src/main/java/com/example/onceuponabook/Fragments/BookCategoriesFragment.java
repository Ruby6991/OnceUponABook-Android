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
        lstItem.add(new BookDTO("Art & Photography","https://i.pinimg.com/564x/fe/fc/e1/fefce1c8250e7679cf45486b5bbd2d45.jpg"));
        lstItem.add(new BookDTO("Biography","https://i.pinimg.com/564x/6b/89/de/6b89de8d4ff4512702e8941031067617.jpg"));
        lstItem.add(new BookDTO("Children's Books","https://i.pinimg.com/564x/ef/96/e7/ef96e7377f168d72c8ab1b5be3185e77.jpg"));
        lstItem.add(new BookDTO("Crafts & Hobbies","https://i.pinimg.com/564x/ca/54/e6/ca54e62ec5bde58006fd91a959d9c472.jpg"));
        lstItem.add(new BookDTO("Crime & Thriller","https://i.pinimg.com/564x/e5/aa/e6/e5aae6177ef46954621e3e7c827c9c93.jpg"));
        lstItem.add(new BookDTO("Fiction","https://i.pinimg.com/564x/c0/86/70/c08670fa037abf12e6ae883a658c32d5.jpg"));
        lstItem.add(new BookDTO("Food & Drink","https://i.pinimg.com/564x/87/f5/85/87f585fca09a9e9e4da8bb39358b45f8.jpg"));
        lstItem.add(new BookDTO("Graphic Novels, Anime & Manga","https://i.pinimg.com/564x/87/f5/85/87f585fca09a9e9e4da8bb39358b45f8.jpg"));
        lstItem.add(new BookDTO("History & Archaeology","https://i.pinimg.com/564x/87/f5/85/87f585fca09a9e9e4da8bb39358b45f8.jpg"));
        lstItem.add(new BookDTO("Mind, Body & Spirit","https://i.pinimg.com/564x/87/f5/85/87f585fca09a9e9e4da8bb39358b45f8.jpg"));
        lstItem.add(new BookDTO("Science Fiction, Fantasy & Horror","https://i.pinimg.com/564x/87/f5/85/87f585fca09a9e9e4da8bb39358b45f8.jpg"));

        //create an instance of ItemsRecyclerAdapter
        ListRecyclerAdapter myAdapter=new ListRecyclerAdapter(getActivity(),lstItem);

        //set adapter
        recyclerView.setAdapter(myAdapter);

        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;

    }

}
