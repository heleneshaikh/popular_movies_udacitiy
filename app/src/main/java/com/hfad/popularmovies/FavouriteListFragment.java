package com.hfad.popularmovies;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.hfad.popularmovies.Database.MovieDatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */

public class FavouriteListFragment extends ListFragment {
    Cursor cursor;
    SQLiteDatabase db;
    int movieId;

    //READ FROM DB

    public FavouriteListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_favourite, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //surround with  catch
        //READ FROM DB
        SQLiteOpenHelper openHelper = new MovieDatabaseHelper(getActivity());
        db = openHelper.getReadableDatabase();
        cursor = db.query("MOVIE",
                          new String[]{"_id", "MOVIE_ID"},
                          null, null, null, null, null);
        if (cursor.moveToFirst()) {
            movieId = cursor.getInt(1);
        }
        CursorAdapter adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.listview_layout,
                cursor,
                new String[]{"MOVIE_ID"},
                new int[]{android.R.id.text1},
                0);
        ListView listFavourites = getListView();
        listFavourites.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Call detail fragment and retrieve data from Retrofit via DB ID
        super.onListItemClick(l, v, position, id);
        if (MainActivity.isDualPane) {
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(DetailFragment.MOVIE_ID, movieId);
            bundle.putString(DetailFragment.FRAGMENT_TYPE, "FavouriteListFragment");
            detailFragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            transaction.replace(R.id.right_container, detailFragment);
            transaction.commit();
        } else {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(DetailFragment.MOVIE_ID, movieId);
            intent.putExtra(DetailFragment.FRAGMENT_TYPE, "FavouriteListFragment");
            startActivity(intent);
        }
    }
}