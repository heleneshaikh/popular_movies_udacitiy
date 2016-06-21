package com.hfad.popularmovies;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Fragment popularFragment;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularFragment = new PopularFragment();
        generateTransaction(popularFragment);
        getActionBar().setTitle(R.string.topRated);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_list_topRated:
                if (isOnline()) {
                    Fragment topRatedFragment = new TopRatedFragment();
                    generateTransaction(topRatedFragment);
                    title = getResources().getString(R.string.topRated);
                    setActionBar(title);
                } else {
                    networkIssue();
                }
                return true;
            case R.id.action_list_popularity:
                if (isOnline()) {
                    popularFragment = new PopularFragment();
                    generateTransaction(popularFragment);
                    title = getResources().getString(R.string.popular);
                    setActionBar(title);
                } else {
                    networkIssue();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void setActionBar(String title) {
        //TODO: AB TITLE ON UP
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
    }

    private void generateTransaction(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.poster_container, fragment, "visible_fragment");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean isOnline() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = con.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void networkIssue() {
        Toast.makeText(this, "Network unavailable", Toast.LENGTH_LONG).show();
    }


}
