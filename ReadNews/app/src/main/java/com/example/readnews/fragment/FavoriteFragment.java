package com.example.readnews.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.readnews.MyWebView;
import com.example.readnews.News;
import com.example.readnews.NewsAdapter;
import com.example.readnews.R;
import com.example.readnews.dao.AppDataBase;

public class FavoriteFragment extends Fragment implements NewsAdapter.newsListener {
    private NewsAdapter adapter;
    private RecyclerView rvViewFav;
    private AppDataBase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = getContext();
        db = AppDataBase.getInstance(context);
        return inflater.inflate(R.layout.fragment_favorite, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvViewFav = getActivity().findViewById(R.id.rv_FavView);
        if (adapter == null) {
            adapter = new NewsAdapter(getLayoutInflater());
        }
        rvViewFav.setAdapter(adapter);
        adapter.setOnItemNewsClicked(this);
        loadData();
    }

    public void loadData() {
        adapter.setListNews(db.newsDao().getFavNews("1"));
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
        PopupMenu popup = new PopupMenu(getContext(), rvViewFav.getChildAt(position));
        popup.inflate(R.menu.popup1);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.popup1_delete) {
                    AlertDialog dialog = new AlertDialog.Builder(getContext()).setMessage("Do you want to delete?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    news.setIsFavorited("0");
                                    db.newsDao().update(news);
                                    loadData();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }).create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }
}
