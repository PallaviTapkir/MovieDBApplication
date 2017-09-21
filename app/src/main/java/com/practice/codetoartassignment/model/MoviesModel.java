package com.practice.codetoartassignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pallavi on 19/9/17.
 */

public class MoviesModel implements Serializable {

   @SerializedName("id")
    private int id;
    @SerializedName("vote_average")
    private float voteAvg;
    @SerializedName("title")
    private String title;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("overview")
    private String overView;
    @SerializedName("poster_path")
    private String posterImagePath;
    @SerializedName("release_date")
    private String moviereleaseDate;
    @SerializedName("adult")
    private boolean isAdult = false;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("popularity")
    private double popularity;

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(float voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
    }

    public String getMoviereleaseDate() {
        return moviereleaseDate;
    }

    public void setMoviereleaseDate(String moviereleaseDate) {
        this.moviereleaseDate = moviereleaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public static class GenerId{
        private int generId;

        public int getGenerId() {
            return generId;
        }

        public void setGenerId(int generId) {
            this.generId = generId;
        }
    }

    @Override
    public String toString() {
        return "MoviesModel{" +
                "id=" + id +
                ", voteAvg=" + voteAvg +
                ", title='" + title + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", overView='" + overView + '\'' +
                ", posterImagePath='" + posterImagePath + '\'' +
                ", moviereleaseDate='" + moviereleaseDate + '\'' +
                ", isAdult=" + isAdult +
                ", voteCount=" + voteCount +
                '}';
    }
}
