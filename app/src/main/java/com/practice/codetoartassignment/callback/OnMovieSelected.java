package com.practice.codetoartassignment.callback;

import com.practice.codetoartassignment.model.MoviesModel;

/**
 * Created by pallavi on 19/9/17.
 */

public interface OnMovieSelected {

    void onMovieSelected(int position, MoviesModel moviesModel);
}
