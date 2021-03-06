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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        RssFeedAdapter.RssItemClickListener,
        RssChannelAdapter.RssChannelClickListener,
        SubscribedItemAdapter.OnSubsribedItemClickListener,
        SubscribedItemAdapter.OnSubscribedItemLongClickListener {

    private DataAdapter mDataAdapter;

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager mFragmentManager;

    private RssChannelListFragment mRssChannelListFragment;
    private RssFeedListFragment mRssFeedListFragment;
    private SubscribedListFragment mSubscribedListFragment;

    private int mCurrentId;
    private RssChannel mCurrentChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataAdapter = new DataAdapter(this);

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
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        //Dummy back
        super.onBackPressed();
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
                    mRssChannelListFragment.hideProgress();
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
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_today:
                fragment = RssFeedListFragment.newInstance();
                mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                mFragmentManager.executePendingTransactions();
                RssFeedListFragment rssFeedListFragment = (RssFeedListFragment) fragment;
                rssFeedListFragment.setRssList(DummyData.getTodayFeeds());
                break;
            case R.id.nav_subscribing:
                fragment = SubscribedListFragment.newInstance(false, true);
                mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                mFragmentManager.executePendingTransactions();
                mSubscribedListFragment = (SubscribedListFragment) fragment;
                mSubscribedListFragment.setSubscribedItemList(mDataAdapter.getChildren(-1));
                mCurrentId = -1;
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
        } else {
            switch (item.getItemId()) {
                case R.id.action_ok:
                    int id = (int) mDataAdapter.createSubscribeChannel(mCurrentId, mCurrentChannel);
                    mCurrentChannel.setId(id);
                    mSubscribedListFragment.addItem(mCurrentChannel);
                    Toast.makeText(MainActivity.this, "Channel subscribed", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_up:
                    if (mCurrentId != -1) {
                        mCurrentId = mDataAdapter.getParentId(mCurrentId);
                        mSubscribedListFragment.setSubscribedItemList(mDataAdapter.getChildren(mCurrentId));
                    }
                    return true;
                case R.id.action_add_folder:
                    handleCreateFolderInSubscribedItems();
                    return true;
                case R.id.action_bookmark:

                    return true;
                case R.id.action_subscribe:
                    startSubscribing();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }

    private void startSubscribing() {
        setTitle("Choose folder");
        mSubscribedListFragment = SubscribedListFragment.newInstance(true, true);
        mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, mSubscribedListFragment).commit();
        mFragmentManager.executePendingTransactions();
        mCurrentId = -1;
        mSubscribedListFragment.setSubscribedItemList(mDataAdapter.getChildren(mCurrentId));
    }

    private void handleCreateFolderInSubscribedItems() {

        //Show dialog here to input your folder name
        DialogInput input = new DialogInput("Folder name: ", "", this);
        input.showDialog();

        //When it Ok
        input.setOnDialogInputSuccessListener(new DialogInput.OnDialogInputSuccessListener() {
            @Override
            public void onDialogInputSuccess(String name) {
                SubscribedFolder folder = new SubscribedFolder(name);
                int id = (int) mDataAdapter.createFolder(mCurrentId, folder);
                folder.setId(id);
                mSubscribedListFragment.addItem(folder);
            }
        });

        //When it Cancel, do nothing
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_ok).setVisible(false);
        menu.findItem(R.id.action_up).setVisible(false);
        menu.findItem(R.id.action_subscribe).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
        menu.findItem(R.id.action_bookmark).setVisible(false);
        menu.findItem(R.id.action_add_folder).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onRssItemClick(View view, int position, RssFeed item) {
        WebViewFragment webFragment = WebViewFragment.newInstance();
        mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, webFragment).commit();
        mFragmentManager.executePendingTransactions();
        webFragment.setWeb(item.getmLink());
    }

    @Override
    public void onRssChannelClick(View view, int position, RssChannel channel) {
        mCurrentChannel = channel;
        if (view instanceof TextView) {
            mRssFeedListFragment = RssFeedListFragment.newInstance(true);
            mFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, mRssFeedListFragment).commit();
            mFragmentManager.executePendingTransactions();
            mRssFeedListFragment.showProgress();

            FetchRssFeedTask fetchRssFeedTask = new FetchRssFeedTask(channel.getFeedId());
            fetchRssFeedTask.setOnCompleteListener(new BaseAsyncTask.OnCompleteListener<List<RssFeed>>() {
                @Override
                public void onComplete(List<RssFeed> feeds) {
                    mRssFeedListFragment.hideProgress();
                    mRssFeedListFragment.setRssList(feeds);
                }
            });

            fetchRssFeedTask.setOnExceptionListener(new BaseAsyncTask.OnExceptionListener() {
                @Override
                public void onException(Exception exception) {
                    mRssFeedListFragment.hideProgress();
                    Toast.makeText(MainActivity.this, "Oops! Something's wrong", Toast.LENGTH_SHORT).show();
                    Log.e("EXCEPTION", exception.getMessage());
                }
            });

            fetchRssFeedTask.execute();
        } else if (view instanceof ImageButton) {
            startSubscribing();
        }
    }

    @Override
    public void onSubscribedItemClick(View view, int position, SubscribedItem item) {
        if (item instanceof RssChannel) {
            RssChannel channel = mDataAdapter.getChannel(item.getId());
            onRssChannelClick(view, position, channel);
        } else if (item instanceof SubscribedFolder) {
            mCurrentId = item.getId();
            mSubscribedListFragment.setSubscribedItemList(mDataAdapter.getChildren(mCurrentId));
        }
    }

    @Override
    public void onSubscribedItemLongClick(View view, int position, SubscribedItem item) {
        //Show the dialog
        DialogQuestion dialogQuestion = new DialogQuestion(this, "Remove " + item.getName() + "?", "");
        dialogQuestion.showDialog();

        final SubscribedItem _item = item;

        //when ok
        dialogQuestion.setOnDialogQuestionSuccessListener(new DialogQuestion.OnDialogQuestionSuccessListener() {
            @Override
            public void onDialogQuestionSuccess() {
                mDataAdapter.removeItem(_item.getId());
                mSubscribedListFragment.removeItem(_item);
                Toast.makeText(MainActivity.this, "Removed " + _item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
