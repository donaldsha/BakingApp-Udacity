package com.example.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Step implements Parcelable{
    @JsonProperty("videoURL")
    private String videoURL;

    @JsonProperty("description")
    private String description;

    @JsonProperty("id")
    private int id;

    @JsonProperty("shortDescription")
    private String shortDescription;

    @JsonProperty("thumbnailURL")
    private String thumbnailURL;

    public Step(){
        this.videoURL = "";
        this.description = "";
        this.id = 0;
        this.shortDescription = "";
        this.thumbnailURL = "";
    }

    protected Step(Parcel in) {
        this.videoURL = in.readString();
        this.description = in.readString();
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.videoURL);
        parcel.writeString(this.description);
        parcel.writeInt(this.id);
        parcel.writeString(this.shortDescription);
        parcel.writeString(this.thumbnailURL);
    }

    //getters and setters


    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public String toString(){
        return "Step{" +
                "videoURL='" + videoURL + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}
