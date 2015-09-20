package com.audiohack.boardcast.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.audiohack.boardcast.BoardCastIntents;
import com.audiohack.boardcast.R;
import com.audiohack.boardcast.audio.PlayerFragment;
import com.audiohack.boardcast.model.Clip;
import com.bumptech.glide.Glide;

import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClipActivity extends AppCompatActivity implements PlayerFragment.PlayerListener {
    //
    // Constants
    //

    /**
     * Fragment tag for player.
     */
    private static final String FRAGMENT_TAG_PLAYER = "player";

    /**
     * The clip last displayed.
     */
    private static final String STATE_CLIP = "clip";

    //
    // Fields
    //

    @Bind(R.id.app_bar)
    Toolbar appBar;

    @Bind(R.id.clip_layout)
    View clipLayout;

    @Bind(R.id.poster)
    ImageView posterView;

    @Bind(R.id.title)
    TextView titleView;

    @Bind(R.id.transcript)
    TextView transcriptView;

    @Bind(R.id.play_button)
    FloatingActionButton playButton;

    private PlayerFragment mPlayerFragment;
    private Clip mClip;

    //
    // Activity callbacks
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);

        ButterKnife.bind(this);

        // Set up app bar.
        setSupportActionBar(appBar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Grab clip to display from Intent or from saved state.
        mClip = (Clip) (savedInstanceState != null
                ? savedInstanceState.getParcelable(STATE_CLIP)
                : getIntent().getParcelableExtra(BoardCastIntents.EXTRA_CLIP)
        );

        // Verify that we have a clip. Not having one is an exceptional situation.
        if (mClip == null) {
            throw new IllegalStateException("Should not have a null clip inside "
                    + getClass().getName());
        }

        // Update views to show clip information.
        updateViews();

        // Set up player.
        if (savedInstanceState == null) {
            mPlayerFragment = PlayerFragment.newInstance(Collections.singletonList(mClip), true);
            getSupportFragmentManager().beginTransaction()
                    .add(mPlayerFragment, FRAGMENT_TAG_PLAYER)
                    .commit();
        } else {
            mPlayerFragment = (PlayerFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_TAG_PLAYER);
        }
    }

    //
    // UI updates
    //

    private void updateViews() {
        titleView.setText(mClip.showTitle);
        transcriptView.setText(
                String.format(getString(R.string.clip_transcript_format), mClip.transcript));
        Glide.with(this).load(mClip.posterURL).crossFade().into(posterView);
    }

    //
    // Listeners
    //

    @OnClick(R.id.clip_layout)
    void onClipLayoutClick() {
        startActivity(BoardCastIntents.newViewURLIntent(this, mClip.showURL));
    }

    @OnClick(R.id.play_button)
    void onPlayClick() {
        if (mPlayerFragment.isPlaying()) {
            mPlayerFragment.pausePlaying();
        } else {
            mPlayerFragment.startPlaying();
        }
    }

    //
    // PlayerFragment.PlayerListener implementation
    //

    @Override
    public void onPlayerStart(Clip clip) {
        playButton.setImageResource(android.R.drawable.ic_media_pause);
    }

    @Override
    public void onPlayerPause() {
        playButton.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void onPlayerStop() {
        playButton.setImageResource(android.R.drawable.ic_media_play);
    }

}
