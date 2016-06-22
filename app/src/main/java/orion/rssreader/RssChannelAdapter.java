package orion.rssreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class RssChannelAdapter extends RecyclerView.Adapter<RssChannelAdapter.ViewHolder>   {
    private Context mContext;
    private List<RssChannel> mRssChannelList;
    private RssChannelClickListener mRssChannelClickListener;

    public interface RssChannelClickListener {
        void onRssChannelClick(View view, int position, RssChannel channel);
    }

    public RssChannelAdapter(List<RssChannel> movies, Context context) {
        this.mRssChannelList = movies;
        this.mContext = context;
    }

    public void setRssChannelClickListener(RssChannelClickListener RssChannelClickListener) {
        mRssChannelClickListener = RssChannelClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_channel, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RssChannel rssChannel = mRssChannelList.get(position);
        holder.mRssChannelName.setText(rssChannel.getName());
    }

    @Override
    public int getItemCount() {
        return mRssChannelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mRssChannelName;
        public ImageButton mSubscribeChannelButton;

        public ViewHolder(View view) {
            super(view);
            mRssChannelName = (TextView) view.findViewById(R.id.rss_channel_name);
            mSubscribeChannelButton = (ImageButton) view.findViewById(R.id.subscribe_channel_button);

            mRssChannelName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mRssChannelClickListener.onRssChannelClick(view, position, mRssChannelList.get(position));
                }
            });

            mSubscribeChannelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mRssChannelClickListener.onRssChannelClick(view, position, mRssChannelList.get(position));
                }
            });

        }
    }
}
