package com.audiohack.boardcast.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @since 2015.09.19
 */
public class Podcast implements Parcelable {

    //
    // Constants
    //
    /**
     * Implementation of {@link Creator} for generating instances of Podcast from {@link Parcelable}
     * instances;
     */
    public static final Creator<Podcast> CREATOR = new Creator<Podcast>() {
        @Override
        public Podcast createFromParcel(Parcel source) {
            return new Podcast(source);
        }

        @Override
        public Podcast[] newArray(int size) {
            return new Podcast[size];
        }
    };

    //
    // Fields
    //

    public String title;
    public String url;
    public String posterUrl;

    //
    // Constructors.
    //

    public Podcast(String title, String url, String posterUrl) {
        this.title = title;
        this.url = url;
        this.posterUrl = posterUrl;
    }

    /**
     * Private constructor for restoring instance from {@link Parcel}.
     *
     * @param in {@link Parcel} from which to restore a $ instance.
     */
    public Podcast(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.posterUrl = in.readString();
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
        destination.writeString(title);
        destination.writeString(url);
        destination.writeString(posterUrl);
    }
}
