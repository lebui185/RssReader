package orion.rssreader;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RssItemDetailFragment extends Fragment {

    private TextView mTitle;
    private ImageView mImage;
    private TextView mDescription;
    private Button mVisitWebsiteButton;

    public RssItemDetailFragment() {
        // Required empty public constructor
    }

    public static RssItemDetailFragment newInstance() {
        RssItemDetailFragment fragment = new RssItemDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitle = (TextView) getActivity().findViewById(R.id.detail_rss_title);
        mImage = (ImageView) getActivity().findViewById(R.id.detail_rss_image);
        mDescription = (TextView) getActivity().findViewById(R.id.detail_rss_description);
        mVisitWebsiteButton = (Button) getActivity().findViewById(R.id.visit_website_button);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_share).setVisible(true);
        menu.findItem(R.id.action_bookmark).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rss_item_detail, container, false);
    }

    public void setRss(RssItem item) {
        mTitle.setText(item.getTitle());
        mImage.setImageResource(item.getImageResource());
        mDescription.setText(item.getDescription());
    }
}
