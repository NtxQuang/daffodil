package com.example.readnews.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.readnews.MainActivity;
import com.example.readnews.MyWebView;
import com.example.readnews.News;
import com.example.readnews.NewsAdapter;
import com.example.readnews.R;
import com.example.readnews.dao.AppDataBase;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavedFragment extends Fragment implements NewsAdapter.newsListener {

    private RecyclerView rvViewSaved;
    private NewsAdapter adapter;
    private AppDataBase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = getContext();
        db = AppDataBase.getInstance(context);
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvViewSaved = getActivity().findViewById(R.id.rv_viewSaved);
        if (adapter == null) {
            adapter = new NewsAdapter(getLayoutInflater());
        }
        rvViewSaved.setAdapter(adapter);
        adapter.setOnItemNewsClicked(this);
        loadData();
    }

    public void loadData() {
        adapter.setListNews(db.newsDao().getAll());
    }


    @Override
    public void onItemNewsClicked(News news) {
        Intent webViewIntent = new Intent(getContext(), MyWebView.class);
        String url = news.getUrl();
        webViewIntent.putExtra(NewsFragment.EXTRA_URL, url);
        startActivity(webViewIntent);
    }

    @Override
    public void onItemNewsLongClicked(News news, int position) {
        MainActivity main= (MainActivity) getActivity();
        PopupMenu popup = new PopupMenu(getContext(), rvViewSaved.getChildAt(position));
        popup.inflate(R.menu.popup);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    default:
                        return false;
                    case R.id.popup_delete:
                        AlertDialog dialog = new AlertDialog.Builder(getContext()).setMessage("Do you want to delete?")
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                })
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AppDataBase.getInstance(getContext()).newsDao().delete(news);
                                        loadData();
                                        main.getFmFavorite().loadData();
                                    }
                                }).create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();

                        String path= "storage/emulated/0/ReadNews/https:"+news.getUrl().substring(7)+".html";

                        File f= new File(path);
                        f.delete();

                        return true;
                    case R.id.popup_favorited:
                        news.setIsFavorited("1");
                        db.newsDao().update(news);
                        main.getFmFavorite().loadData();
                        loadData();
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).
                                setMessage("Added to Favorited!").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        return true;
                }

            }
        });
        popup.show();
    }

}
