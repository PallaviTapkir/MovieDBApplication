package com.practice.codetoartassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.practice.codetoartassignment.R;
import com.practice.codetoartassignment.adapter.MoviesAdapter;
import com.practice.codetoartassignment.callback.OnMovieSelected;
import com.practice.codetoartassignment.context_wrapper.AppController;
import com.practice.codetoartassignment.model.MoviesModel;
import com.practice.codetoartassignment.model.response.MoviesModelResponse;
import com.practice.codetoartassignment.rest_api.ApiClient;
import com.practice.codetoartassignment.rest_api.ApiInterface;
import com.practice.codetoartassignment.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pallavi on 19/9/17.
 */
public class MainActivity extends BaseActivity implements OnMovieSelected {

    private static final String CLASSTAG = MainActivity.class.getSimpleName();

    /*intent key*/
    public static final String MOVIE_DATA = "movie_data";

    //Views declaration
    private Toolbar toolbar;
    private RecyclerView movieListRecyclerView;
    private AppCompatTextView noMovies;
    private SwipeRefreshLayout refreshMovieList;
    private List<MoviesModel> movieList;
    private MoviesAdapter adapter;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting context to controller class
        AppController.setActivity(MainActivity.this);
        initView();

        if(Utility.isNetworkAvailable()) {
            fetchUpcomingMovies();
        }else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            movieListRecyclerView.setVisibility(View.GONE);
            noMovies.setVisibility(View.VISIBLE);
        }

        refreshMovieList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utility.isNetworkAvailable()) {
                    fetchUpcomingMovies();
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    movieListRecyclerView.setVisibility(View.GONE);
                    noMovies.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void fetchUpcomingMovies() {

        refreshMovieList.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesModelResponse> call = apiInterface.getMovieList(getString(R.string.api_key));
        call.enqueue(new Callback<MoviesModelResponse>() {
            @Override
            public void onResponse(Call<MoviesModelResponse> call, Response<MoviesModelResponse> response) {
                refreshMovieList.setRefreshing(false);
                Log.e(CLASSTAG, " Response");
                if (response != null) {
                    int responseSize = response.body().getResults().size();
                    Log.e(CLASSTAG, responseSize + "");
                    if (response.body().getResults().size() > 0) {
                        movieList = new ArrayList<MoviesModel>();
                        for (int i = 0; i < response.body().getResults().size(); i++) {

                            MoviesModel moviesModel = new MoviesModel();

                            moviesModel.setAdult(response.body().getResults().get(i).isAdult());
                            moviesModel.setBackdropPath(response.body().getResults().get(i).getBackdropPath());
                            moviesModel.setId(response.body().getResults().get(i).getId());
                            moviesModel.setMoviereleaseDate(response.body().getResults().get(i).getMoviereleaseDate());
                            moviesModel.setOriginalLanguage(response.body().getResults().get(i).getOriginalLanguage());
                            moviesModel.setOriginalTitle(response.body().getResults().get(i).getOriginalTitle());
                            moviesModel.setOverView(response.body().getResults().get(i).getOverView());
                            moviesModel.setPosterImagePath(response.body().getResults().get(i).getPosterImagePath());
                            moviesModel.setTitle(response.body().getResults().get(i).getTitle());
                            moviesModel.setVoteAvg(response.body().getResults().get(i).getVoteAvg());
                            moviesModel.setVoteCount(response.body().getResults().get(i).getVoteCount());
                            moviesModel.setPopularity(response.body().getResults().get(i).getPopularity());

                            movieList.add(moviesModel);
                        }
                    }
                }
                if (movieList != null && !movieList.isEmpty()) {

                    movieListRecyclerView.setVisibility(View.VISIBLE);
                    noMovies.setVisibility(View.GONE);
                    adapter = new MoviesAdapter(MainActivity.this, movieList);
                    movieListRecyclerView.setAdapter(adapter);
                    adapter.setOnMovieSelected(new OnMovieSelected() {
                        @Override
                        public void onMovieSelected(int position, MoviesModel moviesModel) {
                            Intent intent = new Intent(MainActivity.this, MovieDetailsActvity.class);
                            intent.putExtra(MOVIE_DATA, moviesModel);
                            startActivity(intent);
                        }
                    });
                }else {
                    movieListRecyclerView.setVisibility(View.GONE);
                    noMovies.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MoviesModelResponse> call, Throwable t) {
                refreshMovieList.setRefreshing(false);
                Log.e(CLASSTAG, t.getMessage());
            }
        });
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refreshMovieList = (SwipeRefreshLayout) findViewById(R.id.refresh_list);
        refreshMovieList.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorPrimaryDark));

        noMovies = (AppCompatTextView) findViewById(R.id.movies_not_available);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        movieListRecyclerView = (RecyclerView) findViewById(R.id.movies_list_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        movieListRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            startActivity(new Intent(MainActivity.this, InformationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieSelected(int position, MoviesModel moviesModel) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActvity.class);
        intent.putExtra(MOVIE_DATA, moviesModel);
        startActivity(intent);
    }
}
