package orion.rssreader;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SignInFragment.OnSignInListener,
    RssItemAdapter.RssItemClickListener,
    RssChannelAdapter.RssChannelClickListener {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView mProfileUsername;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWidgets();

        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, HomeFragment.newInstance()).commit();

    }

    @Override
    public void onStart() {
        super.onStart();
        // search setup
        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            RssChannelListFragment channelListFragment = RssChannelListFragment.newInstance();
            mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, channelListFragment).commit();
            mFragmentManager.executePendingTransactions();
            channelListFragment.setRssChannelList(DummyData.searchChannels(query));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    private void setupWidgets() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    selectDrawerItem(item);
                    return false;
                }
            }
        );

        View navigationHeader = mNavigationView.getHeaderView(0);
        mProfileUsername = (TextView) navigationHeader.findViewById(R.id.profile_header_username_text);
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_sign_in:
                fragment = SignInFragment.newInstance();
                mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                break;
            case R.id.nav_sign_up:
                fragment = SignUpFragment.newInstance();
                mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                break;
            case R.id.nav_today:
                fragment = (RssItemListFragment) RssItemListFragment.newInstance();
                mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                mFragmentManager.executePendingTransactions();
                RssItemListFragment rssItemListFragment = (RssItemListFragment) fragment;
                rssItemListFragment.setRssList(DummyData.getTodayFeeds());
                break;
        }

        if (fragment != null) {
            item.setChecked(true);
            setTitle(item.getTitle());
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_subscribe).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
        menu.findItem(R.id.action_bookmark).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onSignIn(String username, String password) {
        if (IsCorrect(username, password)) {
            mProfileUsername.setText(username);
            mNavigationView.getMenu().clear();
            mNavigationView.inflateMenu(R.menu.menu_profile_signed_in);
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        else {

        }
    }

    private boolean IsCorrect(String username, String password) {
        // dummy
        return true;
    }

    @Override
    public void onRssItemClick(View view, int position, RssItem item) {
        RssItemDetailFragment detailFragment = RssItemDetailFragment.newInstance();
        mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, detailFragment).commit();
        mFragmentManager.executePendingTransactions();
        detailFragment.setRss(item);
    }

    @Override
    public void onRssChannelClick(View view, int position, RssChannel channel) {
        if (view instanceof TextView) {
            RssItemListFragment fragment = RssItemListFragment.newInstance(true);
            mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
            mFragmentManager.executePendingTransactions();
            fragment.setRssList(DummyData.getFeeds(channel));
        }
        else if (view instanceof ImageButton) {
            Toast.makeText(MainActivity.this, "Add channel " + position + " to bookmark", Toast.LENGTH_SHORT).show();
        }
    }
}
