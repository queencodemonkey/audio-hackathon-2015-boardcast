package com.audiohackathon.boardcast.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.audiohackathon.boardcast.BoardCastIntents;
import com.audiohackathon.boardcast.R;
import com.audiohackathon.boardcast.api.BoardCastAPI;
import com.audiohackathon.boardcast.model.Clip;
import com.audiohackathon.boardcast.model.Collection;
import com.audiohackathon.boardcast.model.Podcast;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollectionListActivity extends AppCompatActivity {
    @Bind(R.id.collection_list)
    RecyclerView collectionRecycler;

    private CollectionListAdapter mAdapter;
    private BoardCastAPI mAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);

        ButterKnife.bind(this);

        // TODO Hook this up to API.
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("")
//                .build();
//        mAPI = retrofit.create(BoardCastAPI.class);

        // Set up board view.
        collectionRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CollectionListAdapter(this);
        collectionRecycler.setAdapter(mAdapter);


        // TODO Nuke this. Test data only.
        LinkedList<Clip> testClips = new LinkedList<>();
        testClips.add(new Clip(
                565,
                "http://hackathon-audio.thisamericanlife.org/audio/565/565.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/565_0.jpg",
                61.22f, 91.0f,
                "He created stuff that never even appeared in the books.",
                new Podcast(
                        "The Land of Make Believe",
                        "http://hackathon-audio.thisamericanlife.org/audio/565/565.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/565_0.jpg"
                )
        ));
        testClips.add(new Clip(
                566,
                "http://hackathon-audio.thisamericanlife.org/audio/566/566.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/566-square.jpg",
                33.44f, 50f,
                "And they heard an explosion, and then the water start coming up the streets.",
                new Podcast(
                        "Lower 9 + 10",
                        "http://hackathon-audio.thisamericanlife.org/audio/566/566.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/566-small.jpg"
                )
        ));
        testClips.add(new Clip(
                564,
                "http://hackathon-audio.thisamericanlife.org/audio/564/564.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/562.jpg",
                22f, 25.4f,
                "When Jordan was going into his senior year of high school in small town Utah.",
                new Podcast(
                        "Too Soon?",
                        "http://hackathon-audio.thisamericanlife.org/audio/564/564.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/562.jpg"
                )
        ));
        testClips.add(new Clip(
                563,
                "http://hackathon-audio.thisamericanlife.org/audio/563/563.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/563.jpg",
                41.4f, 52f,
                "He created stuff that never even appeared in the books.",
                new Podcast(
                        "The Problem We All Live With - Part Two",
                        "http://hackathon-audio.thisamericanlife.org/audio/563/563.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/563.jpg"
                )
        ));
        testClips.add(new Clip(
                565,
                "http://hackathon-audio.thisamericanlife.org/audio/565/565.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/565_0.jpg",
                61.22f, 91.0f,
                "He created stuff that never even appeared in the books.",
                new Podcast(
                        "The Land of Make Believe",
                        "http://hackathon-audio.thisamericanlife.org/audio/565/565.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/565_0.jpg"
                )
        ));
        testClips.add(new Clip(
                566,
                "http://hackathon-audio.thisamericanlife.org/audio/566/566.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/566-square.jpg",
                33.44f, 50f,
                "And they heard an explosion, and then the water start coming up the streets.",
                new Podcast(
                        "Lower 9 + 10",
                        "http://hackathon-audio.thisamericanlife.org/audio/566/566.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/566-small.jpg"
                )
        ));
        testClips.add(new Clip(
                564,
                "http://hackathon-audio.thisamericanlife.org/audio/564/564.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/562.jpg",
                22f, 25.4f,
                "When Jordan was going into his senior year of high school in small town Utah.",
                new Podcast(
                        "Too Soon?",
                        "http://hackathon-audio.thisamericanlife.org/audio/564/564.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/562.jpg"
                )
        ));
        testClips.add(new Clip(
                563,
                "http://hackathon-audio.thisamericanlife.org/audio/563/563.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/563.jpg",
                41.4f, 52f,
                "He created stuff that never even appeared in the books.",
                new Podcast(
                        "The Problem We All Live With - Part Two",
                        "http://hackathon-audio.thisamericanlife.org/audio/563/563.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/563.jpg"
                )
        ));
        testClips.add(new Clip(
                565,
                "http://hackathon-audio.thisamericanlife.org/audio/565/565.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/565_0.jpg",
                61.22f, 91.0f,
                "He created stuff that never even appeared in the books.",
                new Podcast(
                        "The Land of Make Believe",
                        "http://hackathon-audio.thisamericanlife.org/audio/565/565.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/565_0.jpg"
                )
        ));
        testClips.add(new Clip(
                566,
                "http://hackathon-audio.thisamericanlife.org/audio/566/566.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/566-square.jpg",
                33.44f, 50f,
                "And they heard an explosion, and then the water start coming up the streets.",
                new Podcast(
                        "Lower 9 + 10",
                        "http://hackathon-audio.thisamericanlife.org/audio/566/566.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/566-small.jpg"
                )
        ));
        testClips.add(new Clip(
                564,
                "http://hackathon-audio.thisamericanlife.org/audio/564/564.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/562.jpg",
                22f, 25.4f,
                "When Jordan was going into his senior year of high school in small town Utah.",
                new Podcast(
                        "Too Soon?",
                        "http://hackathon-audio.thisamericanlife.org/audio/564/564.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/562.jpg"
                )
        ));
        testClips.add(new Clip(
                563,
                "http://hackathon-audio.thisamericanlife.org/audio/563/563.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/563.jpg",
                41.4f, 52f,
                "He created stuff that never even appeared in the books.",
                new Podcast(
                        "The Problem We All Live With - Part Two",
                        "http://hackathon-audio.thisamericanlife.org/audio/563/563.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/563.jpg"
                )
        ));
        testClips.add(new Clip(
                565,
                "http://hackathon-audio.thisamericanlife.org/audio/565/565.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/565_0.jpg",
                61.22f, 91.0f,
                "He created stuff that never even appeared in the books.",
                new Podcast(
                        "The Land of Make Believe",
                        "http://hackathon-audio.thisamericanlife.org/audio/565/565.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/565_0.jpg"
                )
        ));
        testClips.add(new Clip(
                566,
                "http://hackathon-audio.thisamericanlife.org/audio/566/566.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/566-square.jpg",
                33.44f, 50f,
                "And they heard an explosion, and then the water start coming up the streets.",
                new Podcast(
                        "Lower 9 + 10",
                        "http://hackathon-audio.thisamericanlife.org/audio/566/566.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/566-small.jpg"
                )
        ));
        testClips.add(new Clip(
                564,
                "http://hackathon-audio.thisamericanlife.org/audio/564/564.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/562.jpg",
                22f, 25.4f,
                "When Jordan was going into his senior year of high school in small town Utah.",
                new Podcast(
                        "Too Soon?",
                        "http://hackathon-audio.thisamericanlife.org/audio/564/564.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/562.jpg"
                )
        ));
        testClips.add(new Clip(
                563,
                "http://hackathon-audio.thisamericanlife.org/audio/563/563.mp3",
                "http://files.thisamericanlife.org/sites/default/files/episodes/563.jpg",
                41.4f, 52f,
                "He created stuff that never even appeared in the books.",
                new Podcast(
                        "The Problem We All Live With - Part Two",
                        "http://hackathon-audio.thisamericanlife.org/audio/563/563.mp3",
                        "http://files.thisamericanlife.org/sites/default/files/episodes/thumbnail/563.jpg"
                )
        ));
        final Collection[] collections = {new Collection(1, "My Awesome Clips", testClips)};
        mAdapter.setCollections(Arrays.asList(collections));
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
            return new CollectionViewHolder(mInflater.inflate(android.R.layout.simple_list_item_1, parent, false));
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

    static class CollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //
        // Fields
        //

        @Bind(android.R.id.text1)
        TextView textView;

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
            textView.setText(mCollection.title);
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
