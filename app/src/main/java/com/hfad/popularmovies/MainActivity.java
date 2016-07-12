package com.hfad.popularmovies;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.hfad.popularmovies.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    static final String ENDPOINT = "http://api.themoviedb.org/3/";
    static final String API_KEY = "561825fba9c2d42683bcbbd5b12dbd1e";
    private Fragment popularFragment;
    private String title;
    static boolean isDualPane;
    @BindView(R.id.right_container)View detailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        //View detailContainer = findViewById(R.id.right_container);
        ButterKnife.bind(this);
        if (detailContainer != null && detailContainer.getVisibility() == View.VISIBLE) {
            isDualPane = true;
        }

        if (savedInstanceState != null) {
            title = savedInstanceState.getString("title");
            getActionBar().setTitle(title);
        } else {
            popularFragment = new PopularFragment();
            generateTransaction(popularFragment);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_topRated:
                if (isOnline()) {
                    Fragment topRatedFragment = new TopRatedFragment();
                    generateTransaction(topRatedFragment);
                    title = getResources().getString(R.string.topRated);
                    setActionBar(title);
                } else {
                    networkIssue();
                }
                return true;
            case R.id.action_popular:
                if (isOnline()) {
                    popularFragment = new PopularFragment();
                    generateTransaction(popularFragment);
                    title = getResources().getString(R.string.popular);
                    setActionBar(title);
                } else {
                    networkIssue();
                }
                return true;
            case R.id.action_favourites:
                if (isOnline()) {
                    Fragment favouriteListFragment = new FavouriteListFragment();
                    generateTransaction(favouriteListFragment);
                    title = getResources().getString(R.string.favourites);
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
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() { //doesn't get recreated, so add a listener instead of onSavedInstanceState
                        FragmentManager manager = getFragmentManager();
                        Fragment fragment = manager.findFragmentByTag("visible_fragment");
                        if (fragment instanceof TopRatedFragment) {
                            actionBar.setTitle(R.string.topRated);
                        } else if (fragment instanceof PopularFragment) {
                            actionBar.setTitle(R.string.popular);
                        } else {
                            actionBar.setTitle(R.string.favourites);
                        }
                    }
                }
        );
    }

    private void generateTransaction(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.left_container, fragment, "visible_fragment");
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
        Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe //GET TICKET FOR BUS
    public void onMessageEvent(MessageEvent event) {
        if (MainActivity.isDualPane) {
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(DetailFragment.FRAGMENT_TYPE, event.fragmentType);
            bundle.putInt(DetailFragment.POSITION, event.position);
            detailFragment.setArguments(bundle);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.right_container, detailFragment);
            transaction.commit();
        }
    }
}
