package com.developer.kimy.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.developer.kimy.popularmovies.Adapters.CustomMoviesAdapter;
import com.developer.kimy.popularmovies.Interface.MovieInterface;
import com.developer.kimy.popularmovies.Models.Movie;
import com.developer.kimy.popularmovies.Models.MovieResponse;
import com.developer.kimy.popularmovies.Network.APIClient;
import com.developer.kimy.popularmovies.Utils.MovieUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_main)
    public RecyclerView rvMain;

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    private static Retrofit retrofit;
    private static String API_KEY;
    public List<Movie> movies;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.popular_movies);
        API_KEY = getResources().getString(R.string.API_KEY);

        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        getPopularMovies();
    }

    // TODO - Implement savedInstancestate and restoreInstanceState - Part 2

    // TODO - Refactor popular movies and top rated movies code into one function - Part 2
    private void getPopularMovies() {
        if (MovieUtils.getInstance().isNetworkAvailable(this)) {
            if (retrofit == null) {
                retrofit = APIClient.getRetrofitInstance();
            }
            MovieInterface movieService = retrofit.create(MovieInterface.class);
            Call<MovieResponse> call = movieService.getPopularMovies(API_KEY, getResources().getString(R.string.LANGUAGE), currentPage);
            Log.i("Popular movies api", movieService.getPopularMovies(API_KEY, getResources().getString(R.string.LANGUAGE), 1).request().url().toString());
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        movies = response.body().getResults();
                        if (movies != null) {
                            generateMovieList(movies);
                            Log.d(TAG, "Number of popular movies received: " + movies.size());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.i("Network Connection Status", "Not available");
        }
    }

    private void getTopRatedMovies() {
        if (MovieUtils.getInstance().isNetworkAvailable(this)) {
            if (retrofit == null) {
                retrofit = APIClient.getRetrofitInstance();
            }
            MovieInterface movieService = retrofit.create(MovieInterface.class);
            Call<MovieResponse> call = movieService.getTopRatedMovies(API_KEY, getResources().getString(R.string.LANGUAGE), currentPage);
            Log.i("Top movies api", movieService.getTopRatedMovies(API_KEY, getResources().getString(R.string.LANGUAGE), 1).request().url().toString());
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        movies = response.body().getResults();
                        if (movies != null) {
                            generateMovieList(movies);
                            Log.d(TAG, "Number of top rated movies received: " + movies.size());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.i("Network Connection Status", "Not available");
        }
    }

    private void generateMovieList(final List<Movie> results) {
        CustomMoviesAdapter adapter = new CustomMoviesAdapter(this, results, new CustomMoviesAdapter.MovieItemClickListener() {
            @Override
            public void onMovieItemClick(int clickedItemIndex) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("Movie", results.get(clickedItemIndex));
                startActivity(intent);
            }
        });

        initializeGridView(adapter);
    }

    private void initializeGridView(CustomMoviesAdapter adapter) {
        rvMain.setHasFixedSize(true);
        rvMain.setLayoutManager(new GridLayoutManager(this, 2));
        rvMain.setAdapter(adapter);

        // TODO - Implement Pagination (onScrollListener) - Part 2
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.topRated:
                getTopRatedMovies();
                setTitle(R.string.toprated_movies);
                break;
            case R.id.popular:
                getPopularMovies();
                setTitle(R.string.popular_movies);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
