package orion.rssreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ho Vu Anh Khoa on 22/06/2016.
 */
public class SubscribedAdapter extends RecyclerView.Adapter<SubscribedAdapter.ViewHolder> {
    private Context mContext;
    private List<SubscribedItem> mRssCategoryList;
    private RssCategoryClickListener mRssCategoryClickListener;
    private RssCategoryLongClickListener mRssCategoryLongClickListener;

    public interface RssCategoryClickListener {
        void onRssCategoryClick(View view, int position, Object obj);
    }

    public interface RssCategoryLongClickListener {
        void onRssCategoryLongClick(View view, int position, Object obj);
    }

    public SubscribedAdapter(List<SubscribedItem> category, Context context) {
        this.mRssCategoryList = category;
        this.mContext = context;
    }

    public void setRssCategoryClickListener(RssCategoryClickListener rssCategoryClickListener) {
        mRssCategoryClickListener = rssCategoryClickListener;
    }

    public void setmRssCategoryLongClickListener(RssCategoryLongClickListener mRssCategoryLongClickListener) {
        this.mRssCategoryLongClickListener = mRssCategoryLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SubscribedItem rss = mRssCategoryList.get(position);
        if(rss instanceof SubscribedFolder){
            SubscribedFolder folder = (SubscribedFolder) rss;
            holder.mIconItem.setImageResource(R.drawable.rss_folder);
            holder.mRssName.setText(folder.getName());
        }else if (rss instanceof RssChannel){
            RssChannel channel = (RssChannel) rss;
            holder.mIconItem.setImageResource(R.drawable.rss_icon);
            holder.mRssName.setText(channel.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mRssCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mRssName;
        public ImageView mIconItem;

        public ViewHolder(View view) {
            super(view);
            mRssName = (TextView) view.findViewById(R.id.rss_name);
            mIconItem = (ImageView) view.findViewById(R.id.rss_icon);

            mRssName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mRssCategoryClickListener.onRssCategoryClick(view, position, mRssCategoryList.get(position));
                }
            });

            mRssName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    mRssCategoryLongClickListener.onRssCategoryLongClick(view, position, mRssCategoryList.get(position));
                    return true;
                }
            });

            mIconItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mRssCategoryClickListener.onRssCategoryClick(view, position, mRssCategoryList.get(position));
                }
            });

            mIconItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    mRssCategoryLongClickListener.onRssCategoryLongClick(view, position, mRssCategoryList.get(position));
                    return true;
                }
            });
        }
    }
}
