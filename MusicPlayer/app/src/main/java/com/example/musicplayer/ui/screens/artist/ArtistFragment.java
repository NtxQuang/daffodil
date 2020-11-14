package com.example.musicplayer.ui.screens.artist;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.musicplayer.R;
import com.example.musicplayer.databinding.FragmentArtistBinding;
import com.example.musicplayer.models.Artist;
import com.example.musicplayer.ui.base.BaseBindingAdapter;
import com.example.musicplayer.ui.base.BaseFragment;

import static java.security.AccessController.getContext;

public class ArtistFragment extends BaseFragment<FragmentArtistBinding, ArtistViewModel> {

    private BaseBindingAdapter<Artist> adapter;

    @Override
    protected Class<ArtistViewModel> getViewModelClass() {
        return ArtistViewModel.class;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_artist;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseBindingAdapter<>(
                R.layout.item_artist, getLayoutInflater());
        binding.setAdapter(adapter);
        adapter.setData(viewModel.getArtist(getContext()));
    }
}
