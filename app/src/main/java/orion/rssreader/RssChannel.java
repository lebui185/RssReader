package orion.rssreader;

public class RssChannel extends SubscribedItem {
    private String feedId;
    private String title;
    private String website;
    private String description;
    private String iconUrl;
    private String visualUrl;

    public RssChannel() {
    }

    public RssChannel(String feedId, String title, String website, String description, String iconUrl, String visualUrl) {
        this.feedId = feedId;
        this.title = title;
        this.website = website;
        this.description = description;
        this.iconUrl = iconUrl;
        this.visualUrl = visualUrl;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getVisualUrl() {
        return visualUrl;
    }

    public void setVisualUrl(String visualUrl) {
        this.visualUrl = visualUrl;
    }

    @Override
    public String getName() {
        return getTitle();
    }
}
