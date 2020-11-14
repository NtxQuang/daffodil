package com.example.readnews.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readnews.MainActivity;
import com.example.readnews.MyWebView;
import com.example.readnews.News;
import com.example.readnews.NewsAdapter;
import com.example.readnews.NewsDownloadManager;
import com.example.readnews.NewsResponse;
import com.example.readnews.R;
import com.example.readnews.api.ApiBuilder;
import com.example.readnews.dao.AppDataBase;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment implements Callback<NewsResponse>, NewsAdapter.newsListener {

    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_DESC = "EXTRA_DESC";
    public static final String EXTRA_PUBDATE = "EXTRA_PUBDATE";
    public static final String EXTRA_URLTOIMAGE = "EXTRA_URLTOIMAGE";
    private RecyclerView rvViewNews;
    private NewsAdapter adapter;
    private TextView txtNoResult;

    public static boolean netIsConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {

            return false;
        }
        return true;
    }

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        rvViewNews = getActivity().findViewById(R.id.rv_viewNews);
        txtNoResult = getActivity().findViewById(R.id.txt_noResult);
        if (adapter == null) {
            adapter = new NewsAdapter(getLayoutInflater());
        }
        rvViewNews.setAdapter(adapter);
        super.onActivityCreated(savedInstanceState);
        adapter.setOnItemNewsClicked(this);
    }


    @Override
    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
        progressDialog.dismiss();
        NewsResponse newsResponse = response.body();
        adapter.setListNews(newsResponse.getArrNews());
        if (adapter.getListNews().isEmpty()) {
            txtNoResult.setText("No Result");
        }

    }

    @Override
    public void onFailure(Call<NewsResponse> call, Throwable t) {
        Toast.makeText(getContext(), "Search Failed!", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    public void search(String key) {
        txtNoResult.setText("");
        ApiBuilder.getInstance().getNews(key, "3a4fcb761f9c45a184b7462e05e99e61").enqueue(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onItemNewsClicked(News news) {
        Intent webViewIntent= new Intent(getContext(), MyWebView.class);
        String url = news.getUrl();
        webViewIntent.putExtra(EXTRA_URL,url);
        startActivity(webViewIntent);
    }

    @Override
    public void onItemNewsLongClicked(News news, int position) {

        if (netIsConnected(getContext())){
            String path= "storage/emulated/0/ReadNews/https:"+news.getUrl().substring(7)+".html";

            File f= new File(path);
            if(f.exists()){
                Toast.makeText(getContext(), "News Existed!", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog= new ProgressDialog(getContext());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Downloading...");
                progressDialog.show();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NewsDownloadManager.download(news.getUrl(), news);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj= news;
                        handler.sendMessage(msg);
                    }
                });
                t.start();
            }
        } else {
            Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }
        
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                News news= (News) msg.obj;
                progressDialog.dismiss();
                AppDataBase.getInstance(getContext()).newsDao().insert(news);
                MainActivity main = (MainActivity) getActivity();
                main.getFmSaved().loadData();

                AlertDialog dialog = new AlertDialog.Builder(getContext()).
                        setMessage("Saved!\n"+news.getUrl()).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }
    };

}
