package com.example.medialservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MediaBrowserCompat mediaBrowserCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaBrowserCompat = new MediaBrowserCompat(this,
                new ComponentName(this, MediaPlaybackService.class),
                connectionCallbacks, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaBrowserCompat.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // (see "stay in sync with the MediaSession")
        if (MediaControllerCompat.getMediaController(MainActivity.this) != null) {
            MediaControllerCompat.getMediaController(MainActivity.this).unregisterCallback(controllerCallback);
        }
        mediaBrowserCompat.disconnect();
    }

    private final MediaBrowserCompat.ConnectionCallback connectionCallbacks = new MediaBrowserCompat.ConnectionCallback() {
        @Override
        public void onConnected() {
            super.onConnected();
            // Get the token for the MediaSession
            MediaSessionCompat.Token token = mediaBrowserCompat.getSessionToken();

            // Create a MediaControllerCompat
            MediaControllerCompat mediaController =
                    new MediaControllerCompat(MainActivity.this, // Context
                            token);

            // Save the controller
            MediaControllerCompat.setMediaController(MainActivity.this, mediaController);

            // Finish building the UI
            buildTransportControls();
        }
    };

    MediaControllerCompat.Callback controllerCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
        }

        @Override
        public void onSessionDestroyed() {
            super.onSessionDestroyed();
            mediaBrowserCompat.disconnect();
        }
    };

    void buildTransportControls(){

        //click listener

        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(MainActivity.this);

        // Display the initial state
        MediaMetadataCompat metadata = mediaController.getMetadata();
        PlaybackStateCompat pbState = mediaController.getPlaybackState();

        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallback);
    }

    public void onClick(View view) {
        playPause();
    }
    void playPause(){
        int pbState = MediaControllerCompat.getMediaController(MainActivity.this).getPlaybackState().getState();
        if (pbState == PlaybackStateCompat.STATE_PLAYING) {
            MediaControllerCompat.getMediaController(MainActivity.this).getTransportControls().pause();
        } else {
            MediaControllerCompat.getMediaController(MainActivity.this).getTransportControls().play();
        }
    }



}