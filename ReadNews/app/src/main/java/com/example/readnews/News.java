package com.example.readnews;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class News implements Serializable {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    @ColumnInfo
    private String desc;
    @SerializedName("publishedAt")
    @ColumnInfo
    private String pubDate;
    @SerializedName("urlToImage")
    @ColumnInfo
    private String urlToImage;
    @SerializedName("url")
    @ColumnInfo
    String url;
    @ColumnInfo
    private String isFavorited= "0";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(String isFavorited) {
        this.isFavorited = isFavorited;
    }
}
