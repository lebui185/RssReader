package orion.rssreader;

public class RssChannel {
    private String mName;

    public RssChannel() {

    }

    public RssChannel(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
