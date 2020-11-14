package com.example.musicplayer.models;

import android.net.Uri;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {
    public abstract Uri getContentUri();
}
