package orion.rssreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RssFeedAdapter extends RecyclerView.Adapter<RssFeedAdapter.ViewHolder> {
    private Context mContext;
    private List<RssFeed> mRssFeedList;
    private RssItemClickListener mRssItemClickListener;

    public interface RssItemClickListener {
        void onRssItemClick(View view, int position, RssFeed item);
    }

    public RssFeedAdapter(List<RssFeed> movies, Context context) {
        this.mRssFeedList = movies;
        this.mContext = context;
    }

    public void setRssItemClickListener(RssItemClickListener RssItemClickListener) {
        mRssItemClickListener = RssItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_feed, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RssFeed RssFeed = mRssFeedList.get(position);
        holder.mRssItemImage.setImageResource(RssFeed.getImageResource());
        holder.mRssItemTitle.setText(RssFeed.getTitle());
    }

    @Override
    public int getItemCount() {
        return mRssFeedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mRssItemImage;
        public TextView mRssItemTitle;

        public ViewHolder(View view) {
            super(view);
            mRssItemImage = (ImageView) view.findViewById(R.id.rss_item_image);
            mRssItemTitle = (TextView) view.findViewById(R.id.rss_item_title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mRssItemClickListener.onRssItemClick(view, position, mRssFeedList.get(position));
                }
            });
        }
    }
}
