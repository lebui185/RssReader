package orion.rssreader;

/**
 * Created by Ho Vu Anh Khoa on 22/06/2016.
 */
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
