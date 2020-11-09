package com.example.myfirebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfirebase.model.Book;
import com.example.myfirebase.recycleview.BookAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public FloatingActionButton floatingActionButton;
    private RecyclerView rvBooks;
    private BookAdapter bookAdapter;
    List<Book> mdata;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        floatingActionButton = v.findViewById(R.id.fab_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddActivity.class));
            }
        });
        initViews();
        setupBookAdapter();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initmdataBooks();

    }


    private void setupBookAdapter() {
        bookAdapter = new BookAdapter(mdata);
        rvBooks.setAdapter(bookAdapter);
        rvBooks.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initmdataBooks() {
        mdata = new ArrayList<>();
        mdata.add(new Book(R.drawable.book1));
        mdata.add(new Book(R.drawable.gatsby));
        mdata.add(new Book(R.drawable.gatsby2));
        mdata.add(new Book(R.drawable.nondesigner));
        mdata.add(new Book(R.drawable.thefault));
        mdata.add(new Book(R.drawable.themessy));
    }

    private void initViews() {
        rvBooks = v.findViewById(R.id.rv_book);
    }
}