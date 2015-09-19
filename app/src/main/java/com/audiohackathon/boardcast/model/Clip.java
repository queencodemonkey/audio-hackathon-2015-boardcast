package com.audiohackathon.boardcast.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @since 2015.09.19
 */
public class Clip implements Parcelable {
    //
    // Constants
    //

    /**
     * Implementation of {@link Parcelable.Creator} for generating instances of Clip from {@link
     * Parcelable} instances;
     */
    public static final Parcelable.Creator<Clip> CREATOR = new Parcelable.Creator<Clip>() {
        @Override
        public Clip createFromParcel(Parcel source) {
            return new Clip(source);
        }

        @Override
        public Clip[] newArray(int size) {
            return new Clip[size];
        }
    };

    //
    // Fields
    //

    public int id;
    public String url;
    public String posterUrl;
    public float timeIn;
    public float timeOut;
    public String transcript;
    public Podcast podcast;

    //
    // Constructors.
    //

    public Clip(int id, String url, String posterUrl, float timeIn, float timeOut, String transcript,
                Podcast podcast) {
        this.id = id;
        this.url = url;
        this.posterUrl = posterUrl;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.transcript = transcript;
        this.podcast = podcast;
    }

    /**
     * Private constructor for restoring instance from {@link Parcel}.
     *
     * @param in {@link Parcel} from which to restore a $ instance.
     */
    public Clip(Parcel in) {
        id = in.readInt();
        url = in.readString();
        posterUrl = in.readString();
        timeIn = in.readFloat();
        timeOut = in.readFloat();
        transcript = in.readString();
        podcast = in.readParcelable(Podcast.class.getClassLoader());
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
        destination.writeString(url);
        destination.writeString(posterUrl);
        destination.writeFloat(timeIn);
        destination.writeFloat(timeOut);
        destination.writeString(transcript);
        destination.writeParcelable(podcast, 0);
    }
}
