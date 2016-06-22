package orion.rssreader;

// dummy RSS item

public class RssItem {
    private int mImageResource;
    private String mTitle;
    private String mDescription;

    public RssItem() {

    }

    public RssItem(int mImageResource, String mTitle, String mDescription) {
        this.mImageResource = mImageResource;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public void setImageResource(int imageResource) {
        mImageResource = imageResource;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
