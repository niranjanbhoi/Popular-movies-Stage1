package com.developer.kimy.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.kimy.popularmovies.Models.Movie;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_details_moviePoster)
    ImageView moviePoster;

    @BindView(R.id.tv_details_MovieTitle)
    TextView movieTitle;

    @BindView(R.id.tv_details_Language)
    TextView movieLanguage;

    @BindView(R.id.tv_details_plot)
    TextView moviePlot;

    @BindView(R.id.tv_details_releaseDate)
    TextView movieReleaseDate;

    @BindView(R.id.tv_details_voteAverage)
    TextView movieVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();

        ButterKnife.bind(this);
        bindSelectedMovieData();
    }

    private void bindSelectedMovieData() {
        Intent intent = getIntent();
        Movie selectedMovie = intent.getParcelableExtra("Movie");

        movieTitle.setText(selectedMovie.getTitle());
        movieLanguage.setText(new StringBuilder("Language: ").append(selectedMovie.getOriginalLanguage()));
        moviePlot.setText(selectedMovie.getOverview());
        movieReleaseDate.setText(new StringBuilder("Release Date: ").append(selectedMovie.getReleaseDate()));
        movieVoteAverage.setText(new StringBuilder("Rating: ").append(selectedMovie.getVoteAverage()));

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(this.getResources().getString(R.string.IMAGE_BASE_URL) + selectedMovie.getBackdropPath())
                .placeholder((R.drawable.gradient_background))
                .error(R.drawable.ic_launcher_background)
                .into(moviePoster);
    }
}
