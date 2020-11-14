package com.example.musicplayer.models;

import android.net.Uri;
import android.provider.MediaStore;

public class Artist extends BaseModel {

    @FieldInfo(columnName = MediaStore.Audio.Artists._ID)
    private int id;
    @FieldInfo(columnName = MediaStore.Audio.Artists.ARTIST)
    private String name;
    @FieldInfo(columnName = MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)
    private int numberOfAlbum;
    @FieldInfo(columnName = MediaStore.Audio.Artists.NUMBER_OF_TRACKS)
    private int numberOfTrack;

    @Override
    public Uri getContentUri() {
        return MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfAlbum() {
        return numberOfAlbum;
    }

    public int getNumberOfTrack() {
        return numberOfTrack;
    }
}
