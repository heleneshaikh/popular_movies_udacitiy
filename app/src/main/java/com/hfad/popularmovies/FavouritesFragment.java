package com.hfad.popularmovies;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.popularmovies.database.MovieDatabaseHelper;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {
    Context context;
    //read from DB

    public FavouritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            MovieDatabaseHelper dbHelper = new MovieDatabaseHelper(getActivity());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.query("MOVIE",
                    new String[]{"MOVIE_ID", "VOTE_AVERAGE", "OVERVIEW", "ORIGINAL_TITLE",
                            "RELEASE_DATE", "POSTER_PATH"},
                    null, null, null, null, "ORIGINAL_TITLE ASC");

            if (cursor.moveToFirst()) {
                int movie_id = cursor.getInt(0);
                double vote_average = cursor.getDouble(1);
                String overview = cursor.getString(2);
                String original_title = cursor.getString(3);
                String release_date = cursor.getString(4);
                String poster_path = cursor.getString(5);

                View view = getView();

                TextView title = (TextView) view.findViewById(R.id.title);
                if (title != null) {
                    title.setText(original_title);
                }

                ImageView image = (ImageView) view.findViewById(R.id.iv_details);
                if (image != null) {
                    Picasso.with(context)
                            .load("https://image.tmdb.org/t/p/w185/" + poster_path)
                            .into(image);
                }

                TextView year = (TextView) view.findViewById(R.id.year);
                if (year != null) {
                    year.setText(release_date.substring(0, 4));
                }

                TextView vote = (TextView) view.findViewById(R.id.vote);
                if (vote != null) {
                    vote.setText(vote_average + "/10");
                }

                TextView overviews = (TextView) view.findViewById(R.id.overview);
                if (overviews != null) {
                    overviews.setText(overview);
                }
            }
            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

}
