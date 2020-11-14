package com.example.musicplayer.utils;

import com.example.musicplayer.models.BaseModel;
import com.example.musicplayer.ui.base.BaseBindingAdapter;

public interface MediaListener<T extends BaseModel>
        extends BaseBindingAdapter.BaseBindingListener {
    void onItemMediaClicked(T item);
}