package orion.rssreader;

// dummy RSS item

public class RssFeed {
    private int mImageResource;
    private String mTitle;
    private String mLink;
    private String mDescription;

    public RssFeed() {

    }

    public RssFeed(int mImageResource, String mTitle, String mDescription, String mLink) {
        this.mImageResource = mImageResource;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mLink = mLink;
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

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }
}
