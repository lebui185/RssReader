package orion.rssreader;

public class RssCategory {
    String mName;

    public RssCategory(String name) {
        this.mName = name;
    }

    public RssCategory() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
