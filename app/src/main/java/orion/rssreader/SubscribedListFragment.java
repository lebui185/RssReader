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
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ho Vu Anh Khoa on 22/06/2016.
 */
public class SubscribedListFragment extends Fragment{
    private RecyclerView mRssCategoryRecycler;
    private TextView mTextViewPath;
    private String mPath;

    public SubscribedListFragment() {
        // Required empty public constructor
    }

    public static SubscribedListFragment newInstance(String path) {
        SubscribedListFragment fragment = new SubscribedListFragment();
        Bundle args = new Bundle();
        args.putString("PATH",path);
        fragment.setArguments(args);
        return fragment;
    }

    public void setRssCategoryList(List<SubscribedItem> items) {
        SubscribedAdapter adapter = new SubscribedAdapter(items, getContext());
        adapter.setRssCategoryClickListener((SubscribedAdapter.RssCategoryClickListener) getActivity());
        adapter.setmRssCategoryLongClickListener((SubscribedAdapter.RssCategoryLongClickListener) getActivity());

        mRssCategoryRecycler.setAdapter(adapter);
        mRssCategoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRssCategoryRecycler = (RecyclerView) getActivity().findViewById(R.id.category_recycler);



        mTextViewPath = (TextView) getActivity().findViewById(R.id.path);
        mTextViewPath.setText(mPath);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mPath = getArguments().getString("PATH");
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_add_folder).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rss_category_list, container, false);
    }
}
