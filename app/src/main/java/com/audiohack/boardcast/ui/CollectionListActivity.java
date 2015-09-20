package com.audiohack.boardcast.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.audiohack.boardcast.BoardCastIntents;
import com.audiohack.boardcast.R;
import com.audiohack.boardcast.api.BoardCastAPI;
import com.audiohack.boardcast.model.Clip;
import com.audiohack.boardcast.model.Collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;

public class CollectionListActivity extends AppCompatActivity {
    //
    // Constants
    //

    private static final String BOARD_CAST_API_BASE_URL = "https://agile-sands-5998.herokuapp.com/api/v1";

    //
    // Fields
    //

    @Bind(R.id.app_bar)
    Toolbar appBar;

    @Bind(R.id.user_image)
    ImageView userImageView;

    @Bind(R.id.user_description)
    TextView userDescriptionView;

    @Bind(R.id.collection_list)
    RecyclerView collectionRecycler;

    private CollectionListAdapter mAdapter;

    private BoardCastAPI mAPI;
    private Call<Collection> mCallInProgress;

    //
    // Activity callbacks
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);

        ButterKnife.bind(this);

        // Set up app bar.
        setSupportActionBar(appBar);

        // Set up user view.
        userImageView.setImageResource(R.drawable.placeholder_user);
        userDescriptionView.setText("Ashley is 21, Junior in College. She is studying Political Science and is interested in Women’s Rights issues and American Pop Culture (contemporary music, celebrities, fashion). She’s a social person from a major urban city. She consumes most of her media on her phone. She was raised in a Christian home but is now spiritually ambiguous.");

        // Set up board view.
        collectionRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CollectionListAdapter(this);
        collectionRecycler.setAdapter(mAdapter);

        // API setup and initial call.
//        final Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Collection.class, new CollectionJsonDeserializer()).create();
//        final Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BOARD_CAST_API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        mAPI = retrofit.create(BoardCastAPI.class);
//        mCallInProgress = mAPI.getCollection(12345, 1);
//        mCallInProgress.enqueue(new Callback<Collection>() {
//            @Override
//            public void onResponse(Response<Collection> response) {
//                final Collection[] collections = {response.body()};
//                mAdapter.setCollections(Arrays.asList(collections));
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Toast.makeText(CollectionListActivity.this,
//                        "Error retrieving test collection.", Toast.LENGTH_LONG).show();
//            }
//        });


        // TODO Nuke this. Test data only.
        final LinkedList<Clip> testClips = new LinkedList<>();
        testClips.add(new Clip(
                566,
                "The Land of Make Believe",
                "http://www.thisamericanlife.org/radio-archives/episode/566/the-land-of-make-believe",
                Collections.singletonList("http://hackathon-audio.thisamericanlife.org/audio/566/566.mp3"),
                "http://files.thisamericanlife.org/sites/default/files/episodes/566-square.jpg",
                959f, 967f,
                "I think that was when I first realized, like, not everyone had ships in their backyards.",
                "#362021"
        ));
        testClips.add(new Clip(
                565,
                "Lower 9 + 10",
                "http://www.thisamericanlife.org/radio-archives/episode/565/lower-9-10",
                Collections.singletonList("http://hackathon-audio.thisamericanlife.org/audio/565/565.mp3"),
                "http://files.thisamericanlife.org/sites/default/files/episodes/565_0.jpg",
                1001f, 1008f,
                "OK. I hate daydreaming. Do you have nightmares?",
                "#5F383A"
        ));
        testClips.add(new Clip(
                564,
                "Too Soon?",
                "http://www.thisamericanlife.org/radio-archives/episode/564/too-soon",
                Collections.singletonList("http://hackathon-audio.thisamericanlife.org/audio/564/564.mp3"),
                "http://files.thisamericanlife.org/sites/default/files/episodes/562.jpg",
                259f, 269f,
                "And I looked at that, and I thought, that's funny. I don't remember that glass having blood on it before I punched through it.",
                "#885053"
        ));
        testClips.add(new Clip(
                563,
                "The Problem We All Live With - Part Two",
                "http://www.thisamericanlife.org/radio-archives/episode/563/the-problem-we-all-live-with-part-two",
                Collections.singletonList("http://hackathon-audio.thisamericanlife.org/audio/563/563.mp3"),
                "http://files.thisamericanlife.org/sites/default/files/episodes/563.jpg",
                261f, 279f,
                "So this is a thing that happens with segregation. Once you get around people who you haven't been around before, you become just super aware of their race and your race.",
                "#AC8487"
        ));
        final Collection[] collections = {new Collection(1, "My Awesome Clips", testClips)};
        mAdapter.setCollections(Arrays.asList(collections));
    }

    @Override
    protected void onStop() {
        super.onStop();

//        mCallInProgress.cancel();
    }

    //
    // Inner classes
    //

    static class CollectionListAdapter extends RecyclerView.Adapter<CollectionViewHolder> {

        private LayoutInflater mInflater;
        private List<Collection> mCollections;

        public CollectionListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mCollections = Collections.emptyList();
        }

        public void setCollections(List<Collection> collections) {
            mCollections = collections;
            notifyDataSetChanged();
        }

        @Override
        public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CollectionViewHolder(
                    mInflater.inflate(R.layout.recycler_item_collection, parent, false));
        }

        @Override
        public void onBindViewHolder(CollectionViewHolder holder, int position) {
            holder.setCollection(mCollections.get(position));
        }

        @Override
        public int getItemCount() {
            return mCollections.size();
        }
    }

    static class CollectionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        //
        // Fields
        //

        @Bind(R.id.title)
        TextView titleView;

        private Collection mCollection;

        //
        // Constructors
        //

        public CollectionViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void setCollection(Collection collection) {
            mCollection = collection;
            titleView.setText(mCollection.title);
        }

        //
        // View.OnClickListener implementation
        //

        @Override
        public void onClick(View v) {
            final Context context = itemView.getContext();
            context.startActivity(BoardCastIntents.newBoardIntent(context, mCollection));
        }
    }
}
