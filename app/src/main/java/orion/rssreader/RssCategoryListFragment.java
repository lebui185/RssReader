package orion.rssreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Ho Vu Anh Khoa on 22/06/2016.
 */
public class RssCategoryListFragment extends Fragment{
    private RecyclerView mRssCategoryRecycler;

    public RssCategoryListFragment() {
        // Required empty public constructor
    }

    public static RssCategoryListFragment newInstance() {
        RssCategoryListFragment fragment = new RssCategoryListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setRssCategoryList(List<Object> items) {
        RssCategoryAdapter adapter = new RssCategoryAdapter(items, getContext());
        adapter.setRssCategoryClickListener((RssCategoryAdapter.RssCategoryClickListener) getActivity());
        mRssCategoryRecycler.setAdapter(adapter);
        mRssCategoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRssCategoryRecycler = (RecyclerView) getActivity().findViewById(R.id.category_recycler);
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
        return inflater.inflate(R.layout.fragment_rss_category_list, container, false);
    }
}
