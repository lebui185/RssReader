package orion.rssreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RssItemAdapter extends RecyclerView.Adapter<RssItemAdapter.ViewHolder>  {
    private Context mContext;
    private List<RssItem> mRssItemList;
    private RssItemClickListener mRssItemClickListener;

    public interface RssItemClickListener {
        void onRssItemClick(View view, int position, RssItem item);
    }

    public RssItemAdapter(List<RssItem> movies, Context context) {
        this.mRssItemList = movies;
        this.mContext = context;
    }

    public void setRssItemClickListener(RssItemClickListener RssItemClickListener) {
        mRssItemClickListener = RssItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RssItem RssItem = mRssItemList.get(position);
        holder.mRssItemImage.setImageResource(RssItem.getImageResource());
        holder.mRssItemTitle.setText(RssItem.getTitle());
        holder.mRssItemDescription.setText(RssItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return mRssItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mRssItemImage;
        public TextView mRssItemTitle;
        public TextView mRssItemDescription;

        public ViewHolder(View view) {
            super(view);
            mRssItemImage = (ImageView)view.findViewById(R.id.rss_item_image);
            mRssItemTitle = (TextView)view.findViewById(R.id.rss_item_title);
            mRssItemDescription = (TextView) view.findViewById(R.id.rss_item_description);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mRssItemClickListener.onRssItemClick(view, position, mRssItemList.get(position));
                }
            });
        }
    }
}
