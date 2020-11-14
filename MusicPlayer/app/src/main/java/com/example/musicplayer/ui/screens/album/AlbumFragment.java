package com.example.musicplayer.ui.screens.album;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.musicplayer.R;
import com.example.musicplayer.databinding.FragmentAlbumBinding;
import com.example.musicplayer.models.Album;
import com.example.musicplayer.ui.base.BaseBindingAdapter;
import com.example.musicplayer.ui.base.BaseFragment;

import static java.security.AccessController.getContext;

public class AlbumFragment extends BaseFragment<FragmentAlbumBinding, AlbumViewModel> {

    private BaseBindingAdapter<Album> adapter;

    @Override
    protected Class<AlbumViewModel> getViewModelClass() {
        return AlbumViewModel.class;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_album;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseBindingAdapter<>(
                R.layout.item_album, getLayoutInflater());
        binding.setAdapter(adapter);
        adapter.setData(viewModel.getAlbum(getContext()));
    }
}
