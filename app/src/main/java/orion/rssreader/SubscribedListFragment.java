package orion.rssreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SubscribedListFragment extends Fragment {
    private RecyclerView mRssCategoryRecycler;
    private TextView mTextViewPath;
    private String mPath;
    private SubscribedItemAdapter mAdapter;
    private List<SubscribedItem> mItems;
    private boolean mEnableActionOK;
    private boolean mEnableActionCreateFolder;

    private static final String ENABLE_ACTION_OK_KEY = "ENABLE_ACTION_OK_KEY";
    private static final String ENABLE_ACTION_CREATE_FOLDER_KEY = "ENABLE_ACTION_CREATE_FOLDER_KEY";

    public SubscribedListFragment() {
        // Required empty public constructor
    }

    public static SubscribedListFragment newInstance(boolean enableActionOK, boolean enableActionCreateFolder) {
        SubscribedListFragment fragment = new SubscribedListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ENABLE_ACTION_OK_KEY, enableActionOK);
        args.putBoolean(ENABLE_ACTION_CREATE_FOLDER_KEY, enableActionCreateFolder);
        fragment.setArguments(args);
        return fragment;
    }

    public void setSubscribedItemList(List<SubscribedItem> items) {
        mItems = items;
        mAdapter = new SubscribedItemAdapter(mItems, getContext());
        mAdapter.setOnSubsribedItemClickListener((SubscribedItemAdapter.OnSubsribedItemClickListener) getActivity());
        mAdapter.setmRssCategoryLongClickListener((SubscribedItemAdapter.OnSubscribedItemLongClickListener) getActivity());
        mRssCategoryRecycler.setAdapter(mAdapter);
        mRssCategoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void addItem(SubscribedItem item) {
        mItems.add(item);
        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(SubscribedItem toBeRemovedItem) {
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).getId() == toBeRemovedItem.getId()) {
                mItems.remove(i);
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
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
            mEnableActionOK = getArguments().getBoolean(ENABLE_ACTION_OK_KEY);
            mEnableActionCreateFolder = getArguments().getBoolean(ENABLE_ACTION_CREATE_FOLDER_KEY);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (mEnableActionOK) {
            menu.findItem(R.id.action_ok).setVisible(true);
        } else {
            menu.findItem(R.id.action_ok).setVisible(false);
        }
        if (mEnableActionCreateFolder) {
            menu.findItem(R.id.action_add_folder).setVisible(true);
        } else {
            menu.findItem(R.id.action_add_folder).setVisible(false);
        }
        menu.findItem(R.id.action_up).setVisible(true);

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscribed_list, container, false);
    }
}
