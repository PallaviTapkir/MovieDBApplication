package com.practice.codetoartassignment.model.response;

import com.google.gson.annotations.SerializedName;
import com.practice.codetoartassignment.model.MoviesModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pallavi on 19/9/17.
 */

public class MoviesModelResponse{

//    @SerializedName("results")
//    private MoviesModel[] results;

    @SerializedName("results")
    private List<MoviesModel> results;

    public void setResults(List<MoviesModel> results) {
        this.results = results;
    }

    public List<MoviesModel> getResults() {
        return results;
    }

    //    public MoviesModel[] getResults() {
//        return results;
//    }
//
//    public void setResults(MoviesModel[] results) {
//        this.results = results;
//    }
}
