package orion.rssreader;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

public class RssChannelListFragment extends Fragment {
    private RecyclerView mRssChannelRecycler;
    private ProgressBar mProgressBar;

    public RssChannelListFragment() {
        // Required empty public constructor
    }

    public static RssChannelListFragment newInstance() {
        RssChannelListFragment fragment = new RssChannelListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setRssChannelList(List<RssChannel> items) {
        RssChannelAdapter adapter = new RssChannelAdapter(items, getContext());
        adapter.setRssChannelClickListener((RssChannelAdapter.RssChannelClickListener) getActivity());
        mRssChannelRecycler.setAdapter(adapter);
        mRssChannelRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRssChannelRecycler = (RecyclerView) getActivity().findViewById(R.id.channel_recycler);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.rss_channel_list_progressBar);
    }

    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rss_channel_list, container, false);
    }
}
