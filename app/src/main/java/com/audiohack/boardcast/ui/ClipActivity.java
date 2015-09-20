package com.audiohack.boardcast.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.audiohack.boardcast.BoardCastIntents;
import com.audiohack.boardcast.R;
import com.audiohack.boardcast.model.Clip;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClipActivity extends AppCompatActivity {
    //
    // Constants
    //

    /**
     * The clip last displayed.
     */
    private static final String STATE_CLIP = "clip";

    //
    // Fields
    //

    @Bind(R.id.app_bar)
    Toolbar appBar;

    @Bind(R.id.poster)
    ImageView posterView;

    @Bind(R.id.title)
    TextView titleView;

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
        final Clip clip = (Clip) (savedInstanceState != null
                ? savedInstanceState.getParcelable(STATE_CLIP)
                : getIntent().getParcelableExtra(BoardCastIntents.EXTRA_CLIP)
        );

        // Verify that we have a clip. Not having one is an exceptional situation.
        if (clip == null) {
            throw new IllegalStateException("Should not have a null clip inside "
                    + getClass().getName() );
        }

        displayClip(clip);
    }

    //
    // UI updates
    //

    private void displayClip(Clip clip) {
        titleView.setText(clip.transcript);
        Glide.with(this).load(clip.posterUrl).crossFade().into(posterView);
    }
}
