package com.practice.codetoartassignment.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.practice.codetoartassignment.R;
import com.practice.codetoartassignment.callback.OnMovieSelected;
import com.practice.codetoartassignment.model.MoviesModel;

import java.util.List;

/**
 * Created by pallavi on 19/9/17.
 */
public class MoviesAdapter extends RecyclerView.Adapter {

    private final String CLASSTAG = MoviesAdapter.class.getSimpleName();
    private final int VIEW_ITEM = 0;
    private List<MoviesModel> moviesList;
    private Context context;
    private OnMovieSelected onMovieSelected;

    public OnMovieSelected getOnMovieSelected() {
        return onMovieSelected;
    }

    public void setOnMovieSelected(OnMovieSelected onMovieSelected) {
        this.onMovieSelected = onMovieSelected;
    }

    public MoviesAdapter(Context context, List<MoviesModel> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);

            vh = new MoviewViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MoviesModel movie = (MoviesModel) moviesList.get(position);

        ((MoviewViewHolder)holder).moviesModel = movie;
        if (holder instanceof MoviewViewHolder) {

            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append("http://image.tmdb.org/t/p/w500").append(movie.getPosterImagePath());
            Log.e(CLASSTAG, "Image url " + stringBuffer.toString());

            if (stringBuffer.toString() != null && !stringBuffer.toString().isEmpty()) {
                Glide.with(context)
                        .load(stringBuffer.toString())
                        .into(((MoviewViewHolder) holder).posterImage);
            }

            ((MoviewViewHolder) holder).movieTitle.setText(movie.getTitle());
            ((MoviewViewHolder) holder).movieReleaseDate.setText(movie.getMoviereleaseDate());
            if (movie.isAdult()) {
                ((MoviewViewHolder) holder).allowedForAdult.setText("A");
            } else {
                ((MoviewViewHolder) holder).allowedForAdult.setText("U/A");
            }
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    public class MoviewViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public AppCompatImageView posterImage;
        public AppCompatImageView movieDetail;
        public AppCompatTextView movieTitle;
        public AppCompatTextView movieReleaseDate;
        public AppCompatTextView allowedForAdult;
        public LinearLayout movieLayout;
        public MoviesModel moviesModel;

        public MoviewViewHolder(View v) {
            super(v);
            posterImage = v.findViewById(R.id.movie_image_thumb);
            movieDetail = v.findViewById(R.id.movie_details_page);
            movieTitle = v.findViewById(R.id.movie_title);
            movieReleaseDate = v.findViewById(R.id.movie_release_date);
            allowedForAdult = v.findViewById(R.id.movie_adult_status);
            movieLayout = v.findViewById(R.id.linear_layout_movie);

            movieLayout.setOnClickListener(this);
            movieDetail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.linear_layout_movie:
                case R.id.movie_details_page:
                    if(getOnMovieSelected()!=null && moviesModel!=null){
                        getOnMovieSelected().onMovieSelected(getAdapterPosition(),moviesModel);
                    }
                    break;
            }
        }
    }
}