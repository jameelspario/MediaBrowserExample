package com.example.medialservice;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

import java.util.ArrayList;
import java.util.List;

public class MediaPlaybackService extends MediaBrowserServiceCompat {
    public static final String TAG = "MediaPlaybackService";
    private static final String MY_MEDIA_ROOT_ID = "media_root_id";
    private static final String MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id";

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a MediaSessionCompat
        mediaSession = new MediaSessionCompat(this, TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());

        // MySessionCallback() has methods that handle callbacks from a media controller
        mediaSession.setCallback(callback);

        // Set the session's token so that client activities can communicate with it.
        setSessionToken(mediaSession.getSessionToken());
    }


    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle bundle) {
        // (Optional) Control the level of access for the specified package name.
        // You'll need to write your own logic to do this.
        /*if (allowBrowsing(clientPackageName, clientUid)) {
            // Returns a root ID that clients can use with onLoadChildren() to retrieve
            // the content hierarchy.
            return new BrowserRoot(MY_MEDIA_ROOT_ID, null);
        } else {
            // Clients can connect, but this BrowserRoot is an empty hierachy
            // so onLoadChildren returns nothing. This disables the ability to browse for content.
            return new BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null);
        }*/
        return  null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentMediaId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        //  Browsing not allowed
        if (TextUtils.equals(MY_EMPTY_MEDIA_ROOT_ID, parentMediaId)) {
            result.sendResult(null);
            return;
        }

        // Assume for example that the music catalog is already loaded/cached.

        List<MediaBrowserCompat.MediaItem> mediaItems = new ArrayList<>();

        // Check if this is the root menu:
        if (MY_MEDIA_ROOT_ID.equals(parentMediaId)) {
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }
        result.sendResult(mediaItems);
    }

    private AudioFocusRequest audioFocusRequest;

    MediaSessionCompat.Callback callback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            super.onPlay();
            /*AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            // Request audio focus for playback, this registers the afChangeListener
            AudioAttributes attrs = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setOnAudioFocusChangeListener(afChangeListener)
                    .setAudioAttributes(attrs)
                    .build();
            int result = am.requestAudioFocus(audioFocusRequest);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                // Start the service
                startService(new Intent(this, MediaBrowserService.class));
                // Set the session active  (and update metadata and state)
                mediaSession.setActive(true);
                // start the player (custom call)
                player.start();
                // Register BECOME_NOISY BroadcastReceiver
                registerReceiver(myNoisyAudioStreamReceiver, intentFilter);
                // Put the service in the foreground, post notification
                startForeground(id, myPlayerNotification);
            }*/
        }

        @Override
        public void onStop() {
            super.onStop();
            /*AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            // Abandon audio focus
            am.abandonAudioFocusRequest(audioFocusRequest);
            unregisterReceiver(myNoisyAudioStreamReceiver);
            // Stop the service
            stopSelf();
            // Set the session inactive  (and update metadata and state)
            mediaSession.setActive(false);
            // stop the player (custom call)
            player.stop();
            // Take the service out of the foreground
            stopForeground(false);*/
        }

        @Override
        public void onPause() {
            super.onPause();
            /*AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            // Update metadata and state
            // pause the player (custom call)
            player.pause();
            // unregister BECOME_NOISY BroadcastReceiver
            unregisterReceiver(myNoisyAudioStreamReceiver);
            // Take the service out of the foreground, retain the notification
            stopForeground(false);*/
        }
    };

}
