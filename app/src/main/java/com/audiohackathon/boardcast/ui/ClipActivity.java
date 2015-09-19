package com.audiohackathon.boardcast.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.audiohackathon.boardcast.BoardCastIntents;
import com.audiohackathon.boardcast.R;
import com.audiohackathon.boardcast.model.Clip;
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

        final Clip clip = (Clip) (savedInstanceState != null
                ? savedInstanceState.getParcelable(STATE_CLIP)
                : getIntent().getParcelableExtra(BoardCastIntents.EXTRA_CLIP)
        );
        if (clip != null) {
            displayClip(clip);
        }
    }

    //
    // UI updates
    //

    private void displayClip(Clip clip) {
        titleView.setText(clip.transcript);
        Glide.with(this).load(clip.posterUrl).crossFade().into(posterView);
    }
}
