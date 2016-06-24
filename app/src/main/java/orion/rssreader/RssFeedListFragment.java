package orion.rssreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

public class RssFeedListFragment extends Fragment {
    private RecyclerView mRssItemRecycler;
    private ProgressBar mProgressBar;
    private boolean mSubscribeEnabled;
    private static final String SUBSCRIBE_ENABLED_KEY = "SUBSCRIBE_ENABLED_KEY";
    private RssChannel mRssChannel;

    public interface OnRssItemClickListener {
        void onItemClick(View view, int position, RssFeed item);
    }

    public RssFeedListFragment() {

    }

    public static RssFeedListFragment newInstance() {
        return newInstance(false);
    }

    public static RssFeedListFragment newInstance(boolean subscribeEnabled) {
        RssFeedListFragment fragment = new RssFeedListFragment();
        Bundle args = new Bundle();
        args.putBoolean(SUBSCRIBE_ENABLED_KEY, subscribeEnabled);
        fragment.setArguments(args);
        return fragment;
    }

    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    public void setRssList(List<RssFeed> items) {
        RssFeedAdapter adapter = new RssFeedAdapter(items, getActivity());
        adapter.setRssItemClickListener((RssFeedAdapter.RssItemClickListener) getActivity());
        mRssItemRecycler.setAdapter(adapter);
        mRssItemRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mSubscribeEnabled = getArguments().getBoolean(SUBSCRIBE_ENABLED_KEY, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rss_feed_list, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (mSubscribeEnabled) {
            menu.findItem(R.id.action_subscribe).setVisible(true);
        } else {
            menu.findItem(R.id.action_subscribe).setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRssItemRecycler = (RecyclerView) getActivity().findViewById(R.id.rss_item_recycler);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.rss_feed_list_progressBar);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
