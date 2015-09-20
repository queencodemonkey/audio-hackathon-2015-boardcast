package com.audiohack.boardcast.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.audiohack.boardcast.BoardCastException;
import com.audiohack.boardcast.model.Clip;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @since 2015.09.19
 */
public class PlayerFragment extends Fragment implements MediaPlayer.OnPreparedListener {

    //
    // Constants
    //

    /**
     * Clips to play.
     */
    private static final String ARGUMENT_CLIPS = "clips";

    /**
     * Index of currently playing clip.
     */
    private static final String STATE_INDEX = "index";

    /**
     * Play position of currently playing clip.
     */
    private static final String STATE_POSITION = "position";

    /**
     * Maximum clip play duration: 10 seconds.
     */
    private static final long CLIP_PLAY_DURATION_MAX_MS = 10000;

    //
    // Fields
    //

    private MediaPlayer mPlayer;
    private boolean mPreparing;
    private boolean mReady;
    private boolean mPlaying;

    private List<Clip> mClips;

    private Clip mCurrentClip;
    private int mCurrentClipIndex;
    private int mCurrentClipPosition;

    private PlayerListener mListener;

    private Handler mHandler;
    private Runnable mStopRunnable = new Runnable() {
        @Override
        public void run() {
            stopPlaying();
            if (incrementClip()) {
                prepareClip();
            }
        }
    };

    //
    // Factory method
    //

    /**
     * Factory method for creating a new PlayerFragment instance.
     *
     * @return A new PlayerFragment instance.
     */
    public static PlayerFragment newInstance(List<Clip> clips) {
        final Bundle args = new Bundle();
        final Clip[] clipArray = new Clip[clips.size()];
        clips.toArray(clipArray);
        args.putParcelableArray(ARGUMENT_CLIPS, clipArray);
        final PlayerFragment fragment = new PlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //
    // Constructors
    //

    /**
     * Default constructor.
     */
    public PlayerFragment() {
        mHandler = new Handler();
    }

    //
    // Getters/Setters
    //

    public boolean isPlaying() {
        return mPlaying;
    }

    //
    // Fragment lifecycle
    //


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof PlayerListener) {
            mListener = (PlayerListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement PlayerListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Parcelable[] parcelableArray = getArguments().getParcelableArray(ARGUMENT_CLIPS);
        if (parcelableArray == null || parcelableArray.length == 0) {
            throw new IllegalArgumentException("Cannot start player with null/empty clips.");
        }
        final int clipCount = parcelableArray.length;
        final Clip[] clipArray = new Clip[clipCount];
        for (int i = 0; i < clipCount; i++) {
            clipArray[i] = (Clip) parcelableArray[i];
        }
        mClips = Arrays.asList(clipArray);
        if (savedInstanceState != null) {
            mCurrentClipIndex = savedInstanceState.getInt(STATE_INDEX);
            mCurrentClipPosition = savedInstanceState.getInt(STATE_INDEX);
        }
        mCurrentClip = mClips.get(mCurrentClipIndex);
    }

    @Override
    public void onStart() {
        super.onStart();

        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(this);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onPause() {
        super.onPause();

        stopPlaying();
        mPlayer.release();
        mHandler.removeCallbacks(mStopRunnable);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(STATE_INDEX, mCurrentClipIndex);
        outState.putInt(STATE_POSITION, mCurrentClipPosition);
    }

    //
    // Playback
    //

    private boolean incrementClip() {
        mCurrentClipIndex++;
        if (mCurrentClipIndex < mClips.size()) {
            mCurrentClip = mClips.get(mCurrentClipIndex);
            return true;
        }
        // If the end of the clip list is reached, return `false` so that we do not start playback
        // but reset the index.
        mPlayer.reset();
        mReady = false;
        mCurrentClipIndex = 0;
        mCurrentClip = mClips.get(mCurrentClipIndex);
        return false;
    }

    private void prepareClip() {
        mPlayer.reset();
        mReady = false;
        try {
            mPlayer.setDataSource(getContext(), Uri.parse(mCurrentClip.audioUrls.get(0)));
        } catch (IOException e) {
            throw new BoardCastException("Had trouble setting data source for clip \""
                    + mCurrentClip.id + "\" @ " + mCurrentClip.audioUrls.get(0));
        }
        mPreparing = true;
        mPlayer.prepareAsync();
    }

    public void startPlaying() {
        if (!mReady) {
            if (!mPreparing) {
                prepareClip();
            }
            return;
        }
        mListener.onPlayerStart(mCurrentClip);
        mPlayer.start();
        mPlaying = true;
    }

    public void stopPlaying() {
        if (!mPlaying) {
            return;
        }
        mListener.onPlayerStop();
        mPlayer.stop();
        mPlaying = false;
    }

    public void pausePlaying() {
        if (!mPlaying) {
            return;
        }
        mListener.onPlayerPause();
        mPlayer.pause();
        mPlaying = false;
    }

    //
    // MediaPlayer.OnPreparedListener implementation
    //

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPreparing = false;
        mReady = true;
        // Convert clip start time to milliseconds and seek to it.
        mp.seekTo((int) Math.floor(mCurrentClip.timeIn * 1000));

        // Start playback.
        startPlaying();

        // Schedule handler to stop clip.
        final long duration = (long) Math.ceil(mCurrentClip.timeOut - mCurrentClip.timeIn) * 1000;
        mHandler.postDelayed(mStopRunnable, duration > 0 ? duration : CLIP_PLAY_DURATION_MAX_MS);
    }

    //
    // Interface definitions
    //

    public interface PlayerListener {
        void onPlayerStart(Clip clip);

        void onPlayerPause();

        void onPlayerStop();
    }
}
