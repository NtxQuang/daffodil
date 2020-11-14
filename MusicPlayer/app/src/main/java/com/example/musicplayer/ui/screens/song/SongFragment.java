package com.example.musicplayer.ui.screens.song;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.musicplayer.R;

import com.example.musicplayer.databinding.FragmentSongBinding;
import com.example.musicplayer.models.Song;
import com.example.musicplayer.ui.base.BaseBindingAdapter;
import com.example.musicplayer.ui.base.BaseFragment;
import com.example.musicplayer.utils.MediaListener;
import com.example.musicplayer.ui.screens.main.MainActivity;

public class SongFragment extends BaseFragment<FragmentSongBinding, SongViewModel> implements MediaListener<Song> {

    private BaseBindingAdapter<Song> adapter;

    @Override
    protected Class<SongViewModel> getViewModelClass() {
        return SongViewModel.class;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_song;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseBindingAdapter<>(
                R.layout.item_song, getLayoutInflater());
        binding.setAdapter(adapter);
        adapter.setListener(this);
        adapter.setData(viewModel.getSong(getContext()));
    }

    @Override
    public void onItemMediaClicked(Song item) {
        MainActivity activity = (MainActivity) getActivity();
        activity.getService().setData(adapter.getData());
        Log.e("a,",adapter.getData().toString());
        activity.getService().getController()
                .create(adapter.getData().indexOf(item));
    }
}