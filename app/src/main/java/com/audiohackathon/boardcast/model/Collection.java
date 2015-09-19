package com.audiohackathon.boardcast.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2015.09.19
 */
public class Collection implements Parcelable {
    //
    // Constants
    //

    /**
     * Implementation of {@link Creator} for generating instances of Collection from {@link
     * Parcelable} instances;
     */
    public static final Creator<Collection> CREATOR = new Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel source) {
            return new Collection(source);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };

    //
    // Fields
    //

    public int id;
    public String title;
    public List<Clip> clips;

    //
    // Constructors.
    //

    public Collection(int id, String title, List<Clip> clips) {
        this.id = id;
        this.title = title;
        this.clips = clips;
    }

    /**
     * Private constructor for restoring instance from {@link Parcel}.
     *
     * @param in {@link Parcel} from which to restore a $ instance.
     */
    public Collection(Parcel in) {
        id = in.readInt();
        title = in.readString();
        clips = new ArrayList<>();
        in.readList(clips, Clip.class.getClassLoader());
    }

    //
    // Parcelable implementation.
    //

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeInt(id);
        destination.writeString(title);
        destination.writeList(clips);
    }
}
