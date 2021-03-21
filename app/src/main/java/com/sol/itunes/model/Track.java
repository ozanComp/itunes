package com.sol.itunes.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.sol.itunes.R;
import com.sol.itunes.Utility;
import com.squareup.picasso.Picasso;

public class Track {
    private String wrapperType;
    private String kind;
    private String artistName;
    private String collectionName;
    private String trackName;
    private String artworkUrl100;
    private Double collectionPrice;
    private Double trackPrice;
    private String releaseDate;
    private String currency;
    private String description;
    private String longDescription;

    public Track() {
    }

    public String getWrapperType() {
        return wrapperType;
    }

    public void setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public Double getCollectionPrice() {
        return collectionPrice;
    }

    public void setCollectionPrice(Double collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    public Double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(Double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getReleaseDate() {
        return Utility.getFormattedDate(releaseDate);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @BindingAdapter({"artwork"})
    public static void loadImage(ImageView imageView, String imageURL) {
        Picasso.get()
                .load(imageURL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .fit()
                .into(imageView);
    }
}
