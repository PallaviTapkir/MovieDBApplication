package com.practice.codetoartassignment.model.response;

import com.google.gson.annotations.SerializedName;
import com.practice.codetoartassignment.model.MovieImages;

import java.util.List;

/**
 * Created by pallavi on 20/9/17.
 */

public class MovieImagesResponse {

    @SerializedName("backdrops")
    private List<MovieImages> backdrops;

    public List<MovieImages> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<MovieImages> backdrops) {
        this.backdrops = backdrops;
    }
}
