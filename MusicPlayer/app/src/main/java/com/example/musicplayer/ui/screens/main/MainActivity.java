package com.example.musicplayer.ui.screens.main;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicplayer.R;
import com.example.musicplayer.databinding.ActivityMainBinding;
import com.example.musicplayer.service.MP3Service;
import com.example.musicplayer.ui.base.BaseActivity;
import com.example.musicplayer.ui.base.BaseFragment;
import com.example.musicplayer.ui.screens.album.AlbumFragment;
import com.example.musicplayer.ui.screens.artist.ArtistFragment;
import com.example.musicplayer.ui.screens.song.SongFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements BottomNavigationView.OnNavigationItemSelectedListener {

    private AlbumFragment fmAlbum = new AlbumFragment();
    private SongFragment fmSong = new SongFragment();
    private ArtistFragment fmArtist = new ArtistFragment();
    private MP3Service service;

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void init() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        requestPermission(permissions, new BaseActivity.PermissionListener() {
            @Override
            public void onResult(boolean isGranted) {
                if (isGranted) {
                    binding.bottomNav.setOnNavigationItemSelectedListener(MainActivity.this);
                    initFragment();
                    showFragment(fmSong);
                    Intent intent = new Intent(MainActivity.this,
                            MP3Service.class);
                    bindService(intent, connection, Context.BIND_AUTO_CREATE);
                } else {
                    finish();
                }
            }
        });
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            MP3Service.MP3Binder mp3Binder = (MP3Service.MP3Binder) binder;
            service = mp3Binder.getService();
            binding.controllerView.setService(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fmAlbum);
        transaction.add(R.id.container, fmSong);
        transaction.add(R.id.container, fmArtist);
        transaction.commit();
    }

    private void showFragment(BaseFragment fmShow) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.hide(fmAlbum);
        transaction.hide(fmSong);
        transaction.hide(fmArtist);

        transaction.show(fmShow);
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_song:
                showFragment(fmSong);
                break;
            case R.id.nav_artist:
                showFragment(fmArtist);
                break;
            case R.id.nav_album:
                showFragment(fmAlbum);
                break;
        }
        return true;
    }

    public MP3Service getService() {
        return service;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}