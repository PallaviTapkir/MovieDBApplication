package com.practice.codetoartassignment.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.practice.codetoartassignment.R;
import com.practice.codetoartassignment.model.MoviesModel;
import com.practice.codetoartassignment.model.MovieImages;
import com.practice.codetoartassignment.model.response.MovieImagesResponse;
import com.practice.codetoartassignment.rest_api.ApiClient;
import com.practice.codetoartassignment.rest_api.ApiInterface;
import com.practice.codetoartassignment.utils.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pallavi on 19/9/17.
 */

public class MovieDetailsActvity extends BaseActivity implements View.OnLayoutChangeListener {

    private static final String CLASSTAG = MovieDetailsActvity.class.getSimpleName();

    //Views declarartion
    private Toolbar toolbar;
    private AppCompatTextView moviewTitle;
    private long moviewId;
    private ProgressDialog progressDialog;
    private AppCompatImageView movieImage;
    private ViewFlipper viewFlipper;
    private AppCompatTextView movieTitle;
    private AppCompatTextView moviewOverview;
    private AppCompatRatingBar ratiingbar;
    private ToggleButton toggleButtonOne, toggleButtonTwo, toggleButtonThree, toggleButtonFour, toggleButtonFive;
    private AppCompatImageView flipperImageOne, flipperImageTwo, flipperImageThree, flipperImageFour, flipperImageFive, flipperImageSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_actvity);
        initView();

        //get data from previous activity using intent
        Intent data = getIntent();
        MoviesModel moviesModel = (MoviesModel) data.getSerializableExtra(MainActivity.MOVIE_DATA);
        moviewId = moviesModel.getId();
        moviewTitle.setText(moviesModel.getTitle());

        if (Utility.isNetworkAvailable()) {
            if (moviewId != 0) {
                updateView(moviesModel);
                fetchMovieImages(moviewId);
            } else {
                Toast.makeText(this, "Error : Movie id could not be zero.", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateView(MoviesModel moviesModel) {
        StringBuffer sb = new StringBuffer();
        sb.append("http://image.tmdb.org/t/p/w500").append(moviesModel.getPosterImagePath());
        Glide.with(this).load(sb.toString()).diskCacheStrategy(DiskCacheStrategy.RESULT).into(movieImage);
        movieTitle.setText(moviesModel.getTitle());
        moviewOverview.setText(moviesModel.getOverView());

        double popularity = moviesModel.getPopularity();
        float rate = (float) ((popularity * 5) / 100);
        ratiingbar.setRating(rate);
    }

    //fetching images related to movie id
    private void fetchMovieImages(long moviewId) {

        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieImagesResponse> call = apiInterface.getMovieImages(moviewId, getString(R.string.api_key));
        call.enqueue(new Callback<MovieImagesResponse>() {
            @Override
            public void onResponse(Call<MovieImagesResponse> call, Response<MovieImagesResponse> response) {
                progressDialog.dismiss();
                Log.e(CLASSTAG, response.body().getBackdrops().size() + "");
                setViewFlipper(response.body().getBackdrops());
            }

            @Override
            public void onFailure(Call<MovieImagesResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(CLASSTAG, t.getMessage());
                Toast.makeText(MovieDetailsActvity.this, "Erro : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setViewFlipper(List<MovieImages> backdrops) {
        StringBuilder stringBuilder = null;

        if (backdrops.size() > 0 && backdrops.size() <= 5) {
            Log.e(CLASSTAG, "Less than 5" + backdrops.size());
            if (backdrops.size() == 1) {
                viewFlipper.setVisibility(View.GONE);
                flipperImageSingle.setVisibility(View.VISIBLE);
                toggleButtonFive.setVisibility(View.GONE);
                toggleButtonFour.setVisibility(View.GONE);
                toggleButtonThree.setVisibility(View.GONE);
                toggleButtonTwo.setVisibility(View.GONE);
                toggleButtonOne.setVisibility(View.GONE);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(0).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageSingle);

            } else if (backdrops.size() == 2) {
                flipperImageOne.setVisibility(View.VISIBLE);
                flipperImageTwo.setVisibility(View.VISIBLE);
                flipperImageThree.setVisibility(View.GONE);
                flipperImageFour.setVisibility(View.GONE);
                flipperImageFive.setVisibility(View.GONE);
                toggleButtonFive.setVisibility(View.GONE);
                toggleButtonFour.setVisibility(View.GONE);
                toggleButtonThree.setVisibility(View.GONE);
                toggleButtonTwo.setVisibility(View.VISIBLE);
                toggleButtonOne.setVisibility(View.VISIBLE);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(0).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageOne);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(1).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageTwo);
                setFlipperProperty();

            } else if (backdrops.size() == 3) {
                flipperImageOne.setVisibility(View.VISIBLE);
                flipperImageTwo.setVisibility(View.VISIBLE);
                flipperImageThree.setVisibility(View.VISIBLE);
                flipperImageFour.setVisibility(View.GONE);
                flipperImageFive.setVisibility(View.GONE);
                toggleButtonFive.setVisibility(View.GONE);
                toggleButtonFour.setVisibility(View.GONE);
                toggleButtonThree.setVisibility(View.VISIBLE);
                toggleButtonTwo.setVisibility(View.VISIBLE);
                toggleButtonOne.setVisibility(View.VISIBLE);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(0).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageOne);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(1).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageTwo);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(2).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageThree);
                setFlipperProperty();

            } else if (backdrops.size() == 4) {
                flipperImageOne.setVisibility(View.VISIBLE);
                flipperImageTwo.setVisibility(View.VISIBLE);
                flipperImageThree.setVisibility(View.VISIBLE);
                flipperImageFour.setVisibility(View.VISIBLE);
                flipperImageFive.setVisibility(View.GONE);
                toggleButtonFive.setVisibility(View.GONE);
                toggleButtonFour.setVisibility(View.VISIBLE);
                toggleButtonThree.setVisibility(View.VISIBLE);
                toggleButtonTwo.setVisibility(View.VISIBLE);
                toggleButtonOne.setVisibility(View.VISIBLE);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(0).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageOne);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(1).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageTwo);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(2).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageThree);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(3).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageFour);
                setFlipperProperty();

            } else if (backdrops.size() == 5) {
                flipperImageOne.setVisibility(View.VISIBLE);
                flipperImageTwo.setVisibility(View.VISIBLE);
                flipperImageThree.setVisibility(View.VISIBLE);
                flipperImageFour.setVisibility(View.VISIBLE);
                flipperImageFive.setVisibility(View.VISIBLE);
                toggleButtonFive.setVisibility(View.VISIBLE);
                toggleButtonFour.setVisibility(View.VISIBLE);
                toggleButtonThree.setVisibility(View.VISIBLE);
                toggleButtonTwo.setVisibility(View.VISIBLE);
                toggleButtonOne.setVisibility(View.VISIBLE);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(0).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageOne);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(1).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageTwo);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(2).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageThree);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(3).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageFour);
                Glide.with(this).load("http://image.tmdb.org/t/p/w500" + backdrops.get(4).getFile_path()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageFive);
                setFlipperProperty();
            }

        } else {
            Log.e(CLASSTAG, "more than 5");
            for (int i = 0; i < 5; i++) {
                stringBuilder = new StringBuilder().append("http://image.tmdb.org/t/p/w500");
                switch (i) {
                    case 0:
                        stringBuilder.append(backdrops.get(0).getFile_path());
                        Glide.with(this).load(stringBuilder.toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageOne);
                        break;

                    case 1:
                        stringBuilder.append(backdrops.get(1).getFile_path());
                        Glide.with(this).load(stringBuilder.toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageTwo);
                        break;

                    case 2:
                        stringBuilder.append(backdrops.get(2).getFile_path());
                        Glide.with(this).load(stringBuilder.toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageThree);
                        break;

                    case 3:
                        stringBuilder.append(backdrops.get(3).getFile_path());
                        Glide.with(this).load(stringBuilder.toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageFour);
                        break;

                    case 4:
                        stringBuilder.append(backdrops.get(4).getFile_path());
                        Glide.with(this).load(stringBuilder.toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flipperImageFive);
                        break;
                }
            }
            setFlipperProperty();
        }
    }

    private void setFlipperProperty() {
        viewFlipper.setAutoStart(true);
        viewFlipper.startFlipping();
        viewFlipper.setFlipInterval(2000);

        viewFlipper.addOnLayoutChangeListener(this);
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_movie_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        moviewTitle = (AppCompatTextView) findViewById(R.id.movie_name_toolbar);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("fetching images...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        flipperImageSingle = (AppCompatImageView) findViewById(R.id.movie_image);

        movieImage = (AppCompatImageView) findViewById(R.id.movie_image);
        movieTitle = (AppCompatTextView) findViewById(R.id.title_name);
        moviewOverview = (AppCompatTextView) findViewById(R.id.movie_description);
        ratiingbar = (AppCompatRatingBar) findViewById(R.id.rating_bar);

        flipperImageOne = (AppCompatImageView) findViewById(R.id.movie_image_one);
        flipperImageTwo = (AppCompatImageView) findViewById(R.id.movie_image_two);
        flipperImageThree = (AppCompatImageView) findViewById(R.id.movie_image_three);
        flipperImageFour = (AppCompatImageView) findViewById(R.id.movie_imagefour);
        flipperImageFive = (AppCompatImageView) findViewById(R.id.movie_image_five);

        toggleButtonFive = (ToggleButton) findViewById(R.id.toggle_five);
        toggleButtonFour = (ToggleButton) findViewById(R.id.toggle_four);
        toggleButtonThree = (ToggleButton) findViewById(R.id.toggle_three);
        toggleButtonTwo = (ToggleButton) findViewById(R.id.toggle_two);
        toggleButtonOne = (ToggleButton) findViewById(R.id.toggle_one);
    }

    @Override
    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (viewFlipper.getCurrentView() == flipperImageOne) {
            toggleButtonOne.setChecked(true);
            toggleButtonTwo.setChecked(false);
            toggleButtonThree.setChecked(false);
            toggleButtonFour.setChecked(false);
            toggleButtonFive.setChecked(false);

        } else if (viewFlipper.getCurrentView() == flipperImageTwo) {
            toggleButtonOne.setChecked(false);
            toggleButtonTwo.setChecked(true);
            toggleButtonThree.setChecked(false);
            toggleButtonFour.setChecked(false);
            toggleButtonFive.setChecked(false);

        } else if (viewFlipper.getCurrentView() == flipperImageThree) {
            toggleButtonOne.setChecked(false);
            toggleButtonTwo.setChecked(false);
            toggleButtonThree.setChecked(true);
            toggleButtonFour.setChecked(false);
            toggleButtonFive.setChecked(false);

        } else if (viewFlipper.getCurrentView() == flipperImageFour) {
            toggleButtonOne.setChecked(false);
            toggleButtonTwo.setChecked(false);
            toggleButtonThree.setChecked(false);
            toggleButtonFour.setChecked(true);
            toggleButtonFive.setChecked(false);

        } else if (viewFlipper.getCurrentView() == flipperImageFive) {
            toggleButtonOne.setChecked(false);
            toggleButtonTwo.setChecked(false);
            toggleButtonThree.setChecked(false);
            toggleButtonFour.setChecked(false);
            toggleButtonFive.setChecked(true);
        }
    }
}
