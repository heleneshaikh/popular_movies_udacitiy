package com.hfad.popularmovies;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.hfad.popularmovies.adapters.PosterAdapter;
import com.hfad.popularmovies.model.MessageEvent;
import com.hfad.popularmovies.model.Movie;
import com.hfad.popularmovies.model.MoviesAPI;
import com.hfad.popularmovies.model.QueryResult;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedFragment extends Fragment {
    private static final String ENDPOINT = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "561825fba9c2d42683bcbbd5b12dbd1e";
    private PosterAdapter adapter;
    public static List<Movie> movieList;

    public TopRatedFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_top_rated, container, false);
        adapter = new PosterAdapter(getActivity(), movieList);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        getTopRatedMovies();

        adapter.setListener(new PosterAdapter.Listener(){
            @Override
            public void onClick(int position) {
                if (MainActivity.isDualPane) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(DetailFragment.POSITION, position);
                    bundle.putString(DetailFragment.FRAGMENT_TYPE, "TopRatedFragment");
                    Fragment detailFragment = new DetailFragment();
                    detailFragment.setArguments(bundle);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.right_container, detailFragment, "detail_fragment");
                    transaction.commit();
                } else {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra(DetailFragment.POSITION, position);
                    intent.putExtra(DetailFragment.FRAGMENT_TYPE, "TopRatedFragment");
                    getActivity().startActivity(intent);
                }
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });
        return recyclerView;
    }

    private void getTopRatedMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAPI api = retrofit.create(MoviesAPI.class);
        movieList = new ArrayList<>();
        api.getFeedTopRated(API_KEY).enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                QueryResult result = response.body();
                movieList = result.getResults();
                adapter.setMovieList(movieList);
                EventBus.getDefault().post(new MessageEvent(0,"TopRatedFragment",0)); //SENDER
            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
