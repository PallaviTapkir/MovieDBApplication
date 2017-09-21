package com.practice.codetoartassignment.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pallavi on 20/9/17.
 */

public class MovieImages {

    @SerializedName("file_path")
    private String file_path;

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
