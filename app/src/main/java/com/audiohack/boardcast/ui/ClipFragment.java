package com.audiohack.boardcast.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.audiohack.boardcast.R;
import com.audiohack.boardcast.model.Clip;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @since 2015.09.20
 */
public class ClipFragment extends Fragment {
    //
    // Constants
    //

    /**
     * Clip displayed in fragment.
     */
    private static final String ARGUMENT_CLIP = "clip";

    //
    // Fields
    //

    @Bind(R.id.poster)
    ImageView posterView;

    @Bind(R.id.transcript)
    TextView transcriptView;

    private Clip mClip;

    //
    // Factory method
    //

    /**
     * Factory method for creating a new ClipFragment instance.
     *
     * @param clip Clip to display.
     * @return A new ClipFragment instance.
     */
    public static ClipFragment newInstance(Clip clip) {
        if (clip == null) {
            throw new IllegalArgumentException("Clip passed to "
                    + ClipFragment.class.getName() + " cannot be null.");
        }
        final Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_CLIP, clip);
        final ClipFragment fragment = new ClipFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //
    // Constructors
    //

    /**
     * Default constructor.
     */
    public ClipFragment() {
    }

    //
    // Fragment lifecycle
    //


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClip = getArguments().getParcelable(ARGUMENT_CLIP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_clip_overlay, container, false);
        ButterKnife.bind(this, view);
        updateView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    //
    // UI updates
    //

    private void updateView(View view) {
        // Set background color from clip color.

        final int clipColorValue = ClipColorUtils.getClipColorValue(mClip);
        view.setBackgroundColor(clipColorValue);
        ClipColorUtils.setTextColorForClip(transcriptView, mClip);

        // Load the image.
        Glide.with(getActivity()).load(mClip.posterURL).crossFade().centerCrop().into(posterView);

        // Set the clip transcript text.
        transcriptView.setText(mClip.transcript);
    }
}
