package orion.rssreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SubscribedItemAdapter extends RecyclerView.Adapter<SubscribedItemAdapter.ViewHolder> {
    private Context mContext;
    private List<SubscribedItem> mSubscribedItemList;
    private OnSubsribedItemClickListener mOnSubsribedItemClickListener;
    private OnSubscribedItemLongClickListener mOnSubscribedItemLongClickListener;

    public interface OnSubsribedItemClickListener {
        void onSubscribedItemClick(View view, int position, SubscribedItem item);
    }

    public interface OnSubscribedItemLongClickListener {
        void onSubscribedItemLongClick(View view, int position, SubscribedItem item);
    }

    public SubscribedItemAdapter(List<SubscribedItem> items, Context context) {
        this.mSubscribedItemList = items;
        this.mContext = context;
    }

    public void setOnSubsribedItemClickListener(OnSubsribedItemClickListener onSubsribedItemClickListener) {
        mOnSubsribedItemClickListener = onSubsribedItemClickListener;
    }

    public void setmRssCategoryLongClickListener(OnSubscribedItemLongClickListener onSubscribedItemLongClickListener) {
        this.mOnSubscribedItemLongClickListener = onSubscribedItemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SubscribedItem rss = mSubscribedItemList.get(position);
        if (rss instanceof SubscribedFolder) {
            SubscribedFolder folder = (SubscribedFolder) rss;
            holder.mIconItem.setImageResource(R.drawable.ic_folder_black_48dp);
            holder.mRssName.setText(folder.getName());
        } else if (rss instanceof RssChannel) {
            RssChannel channel = (RssChannel) rss;
            holder.mRssName.setText(channel.getTitle());
            Picasso.with(mContext).load(channel.getIconUrl()).into(holder.mIconItem);
        }
    }

    @Override
    public int getItemCount() {
        return mSubscribedItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mRssName;
        public ImageView mIconItem;

        public ViewHolder(View view) {
            super(view);
            mRssName = (TextView) view.findViewById(R.id.rss_name);
            mIconItem = (ImageView) view.findViewById(R.id.subscribed_item_image);

            mRssName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mOnSubsribedItemClickListener.onSubscribedItemClick(view, position, mSubscribedItemList.get(position));
                }
            });

            mRssName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    mOnSubscribedItemLongClickListener.onSubscribedItemLongClick(view, position, mSubscribedItemList.get(position));
                    return true;
                }
            });

            mIconItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mOnSubsribedItemClickListener.onSubscribedItemClick(view, position, mSubscribedItemList.get(position));
                }
            });

            mIconItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    mOnSubscribedItemLongClickListener.onSubscribedItemLongClick(view, position, mSubscribedItemList.get(position));
                    return true;
                }
            });
        }
    }
}
