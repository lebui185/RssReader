package orion.rssreader;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        SignInFragment.OnSignInListener,
        SignUpFragment.OnSignUpListener,
        RssItemAdapter.RssItemClickListener,
        RssChannelAdapter.RssChannelClickListener,
        RssCategoryAdapter.RssCategoryClickListener {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView mProfileUsername;
    private FragmentManager mFragmentManager;

    private SignInFragment mSignInFragment;
    private SignUpFragment mSignUpFragment;
    private RssChannelListFragment mRssChannelListFragment;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // logic init
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    mProfileUsername.setText(user.getEmail());
                    mNavigationView.getMenu().clear();
                    mNavigationView.inflateMenu(R.menu.menu_profile_signed_in);
                    mDrawerLayout.openDrawer(GravityCompat.START);
                } else {
                    // User is signed out
                    mProfileUsername.setText("");
                    mNavigationView.getMenu().clear();
                    mNavigationView.inflateMenu(R.menu.menu_profile_not_signed);
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                // ...
            }
        };
        // UI init
        setupWidgets();
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, HomeFragment.newInstance()).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mRssChannelListFragment = RssChannelListFragment.newInstance();
            mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, mRssChannelListFragment).commit();
            mFragmentManager.executePendingTransactions();
            mRssChannelListFragment.showProgress();

            String query = intent.getStringExtra(SearchManager.QUERY);

            FetchRssChannelTask fetchRssChannelTask = new FetchRssChannelTask(query);
            fetchRssChannelTask.setOnCompleteListener(new BaseAsyncTask.OnCompleteListener<List<RssChannel>>() {
                @Override
                public void onComplete(List<RssChannel> channels) {
                    mRssChannelListFragment.hideProgress();
                    mRssChannelListFragment.setRssChannelList(channels);
                }
            });

            fetchRssChannelTask.setOnExceptionListener(new BaseAsyncTask.OnExceptionListener() {
                @Override
                public void onException(Exception exception) {
                    Toast.makeText(MainActivity.this, "Oops! Something's wrong", Toast.LENGTH_SHORT).show();
                    exception.printStackTrace();
                }
            });

            fetchRssChannelTask.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setIconified(true);
                searchView.clearFocus();
                menu.findItem(R.id.action_search).collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
                mSignInFragment = (SignInFragment) fragment;
                mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                break;
            case R.id.nav_sign_up:
                fragment = SignUpFragment.newInstance();
                mSignUpFragment = (SignUpFragment) fragment;
                mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                break;
            case R.id.nav_today:
                fragment = (RssItemListFragment) RssItemListFragment.newInstance();
                mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                mFragmentManager.executePendingTransactions();
                RssItemListFragment rssItemListFragment = (RssItemListFragment) fragment;
                rssItemListFragment.setRssList(DummyData.getTodayFeeds());
                break;
            case R.id.nav_manage_subscription:
                fragment = (RssCategoryListFragment) RssCategoryListFragment.newInstance();
                mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                mFragmentManager.executePendingTransactions();
                RssCategoryListFragment rssCategoryListFragment = (RssCategoryListFragment) fragment;
                rssCategoryListFragment.setRssCategoryList(DummyData.getCategory(DummyData.getMyCategory()));
                break;
            case R.id.nav_sign_out:
                FirebaseAuth.getInstance().signOut();
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
    public void onSignIn(final String email, final String password) {
        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            mSignInFragment.showProgress();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            mSignInFragment.hideProgress();
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "Empty email or password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSignUp(final String email, final String password) {
        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            mSignUpFragment.showProgress();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Sign up failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            mSignUpFragment.hideProgress();
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "Empty email or password", Toast.LENGTH_SHORT).show();
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
        } else if (view instanceof ImageButton) {
            Toast.makeText(MainActivity.this, "Add channel " + position + " to bookmark", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRssCategoryClick(View view, int position, Object obj) {
        if (view instanceof ImageButton) {
            //When select the remove item button
            if (obj instanceof RssChannel) {
                RssChannel channel = (RssChannel) obj;
                Toast.makeText(MainActivity.this, "Remove channel: " + channel.getTitle() + " from category", Toast.LENGTH_SHORT).show();
            } else if (obj instanceof RssCategory) {
                RssCategory category = (RssCategory) obj;
                Toast.makeText(MainActivity.this, "Remove category: " + category.getName() + " from category", Toast.LENGTH_SHORT).show();
            } else {
                //Nothing todo here.
            }
        } else {
            if (obj instanceof RssChannel) {
                RssChannel channel = (RssChannel) obj;
                onRssChannelClick(new TextView(this), position, channel);
            } else if (obj instanceof RssCategory) {
                RssCategory category = (RssCategory) obj;
                Fragment fragment = (RssCategoryListFragment) RssCategoryListFragment.newInstance();
                mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                mFragmentManager.executePendingTransactions();
                RssCategoryListFragment rssCategoryListFragment = (RssCategoryListFragment) fragment;
                rssCategoryListFragment.setRssCategoryList(DummyData.getCategory(category));
            } else {
                //Do something else
            }
        }
    }
}
