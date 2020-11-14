package com.example.musicplayer.ui.screens.song;

import android.content.Context;

import com.example.musicplayer.models.Song;
import com.example.musicplayer.ui.base.BaseViewModel;
import com.example.musicplayer.utils.SystemData;

import java.util.ArrayList;

public class SongViewModel extends BaseViewModel {
    private ArrayList<Song> songs;

    public ArrayList<Song> getSong(Context context) {
        if (songs == null) {
            SystemData data = new SystemData(context);
            songs = data.getData(Song.class);
        }
        return songs;
    }

}
