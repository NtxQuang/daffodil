package com.example.musicplayer.ui.screens.album;

import android.content.Context;

import com.example.musicplayer.models.Album;
import com.example.musicplayer.ui.base.BaseViewModel;
import com.example.musicplayer.utils.SystemData;

import java.util.ArrayList;

public class AlbumViewModel extends BaseViewModel {
    private ArrayList<Album> albums;

    public ArrayList<Album> getAlbum(Context context) {
        if (albums == null) {
            SystemData data = new SystemData(context);
            albums = data.getData(Album.class);
        }
        return albums;
    }

}

