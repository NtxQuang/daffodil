package com.example.musicplayer.ui.screens.artist;

import android.content.Context;

import com.example.musicplayer.models.Artist;
import com.example.musicplayer.ui.base.BaseViewModel;
import com.example.musicplayer.utils.SystemData;

import java.util.ArrayList;

public class ArtistViewModel extends BaseViewModel {
    private ArrayList<Artist> artists;

    public ArrayList<Artist> getArtist(Context context) {
        if (artists == null) {
            SystemData data = new SystemData(context);
            artists = data.getData(Artist.class);
        }
        return artists;
    }

}
