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

import java.util.List;

public class RssItemListFragment extends Fragment {
    private RecyclerView mRssItemRecycler;
    private boolean mSubscribeEnabled;
    private static final String SUBSCRIBE_ENABLED_KEY = "SUBSCRIBE_ENABLED_KEY";

    public interface OnRssItemClickListener {
        void onItemClick(View view, int position, RssItem item);
    }

    public RssItemListFragment() {

    }

    public static RssItemListFragment newInstance() {
        return newInstance(false);
    }

    public static RssItemListFragment newInstance(boolean subscribeEnabled) {
        RssItemListFragment fragment = new RssItemListFragment();
        Bundle args = new Bundle();
        args.putBoolean(SUBSCRIBE_ENABLED_KEY, subscribeEnabled);
        fragment.setArguments(args);
        return fragment;
    }

    public void setRssList(List<RssItem> items) {
        RssItemAdapter adapter = new RssItemAdapter(items, getActivity());
        adapter.setRssItemClickListener((RssItemAdapter.RssItemClickListener) getActivity());
        mRssItemRecycler.setAdapter(adapter);
        mRssItemRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mSubscribeEnabled = getArguments().getBoolean(SUBSCRIBE_ENABLED_KEY, false);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rss_item_list, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (mSubscribeEnabled) {
            menu.findItem(R.id.action_subscribe).setVisible(true);
        }
        else {
            menu.findItem(R.id.action_subscribe).setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRssItemRecycler = (RecyclerView) getActivity().findViewById(R.id.rss_item_recycler);
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
