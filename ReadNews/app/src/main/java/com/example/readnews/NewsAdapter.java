package com.example.readnews;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private newsListener listener;

    private List<News> listNews = new ArrayList<>();
    private LayoutInflater inflater;

    public void setOnItemNewsClicked(newsListener listener){
        this.listener= listener;
    }

    public NewsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public List<News> getListNews() {
        return listNews;
    }

    public void setListNews(List<News> listNews) {
        this.listNews = listNews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_news, parent, false);
        return new NewsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.bindView(listNews.get(position));
        if (listener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemNewsClicked(listNews.get(position));
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemNewsLongClicked(listNews.get(position), position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listNews == null ? 0 : listNews.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        private ImageView imgImage;
        private TextView txtTitle;
        private TextView txtDes;
        private TextView txtPubDate;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.img_imeNews);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDes = itemView.findViewById(R.id.txt_desc);
            txtPubDate = itemView.findViewById(R.id.txt_pubDate);

        }

        public void bindView(News news) {
            txtTitle.setText(news.getTitle());
            txtDes.setText(news.getDesc());
            txtPubDate.setText(news.getPubDate());
            Glide.with(imgImage)
                    .load(news.getUrlToImage())
                    .into(imgImage);
        }
    }

    public interface newsListener{
        void onItemNewsClicked(News news);
        void onItemNewsLongClicked(News news, int position);
    }
}