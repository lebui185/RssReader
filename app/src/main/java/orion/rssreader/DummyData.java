package orion.rssreader;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<RssFeed> getTodayFeeds() {
        List<RssFeed> rssFeeds = new ArrayList<>();

        return rssFeeds;
    }

    public static SubscribedFolder getFolderData(String name) {
        SubscribedFolder folder = new SubscribedFolder(name);
        folder.addItem(new RssChannel(null, "My_channel 1", null, null, null, null));
        folder.addItem(new RssChannel(null, "My_channel 2", null, null, null, null));
        folder.addItem(new RssChannel(null, "My_channel 3", null, null, null, null));
        return folder;
    }


    public static List<RssFeed> getRecentFeeds() {
        List<RssFeed> rssFeeds = null;

        return rssFeeds;
    }

    public static List<RssFeed> getBookmarkFeeds() {
        List<RssFeed> rssFeeds = null;

        return rssFeeds;
    }

    private static SubscribedFolder mRoot;

    static {
        mRoot = new SubscribedFolder("root");

        SubscribedFolder folderA = new SubscribedFolder("folder A");
        SubscribedFolder folderB = new SubscribedFolder("folder B");
        SubscribedFolder folderC = new SubscribedFolder("folder C");
        SubscribedFolder folderD = new SubscribedFolder("folder D");
        SubscribedFolder folderA2 = new SubscribedFolder("folder A");

        RssChannel channelC = new RssChannel(null, "channel C", null, null, null, null);
        RssChannel channelH = new RssChannel(null, "channel H", null, null, null, null);
        RssChannel channelF = new RssChannel(null, "channel F", null, null, null, null);
        RssChannel channelG = new RssChannel(null, "channel G", null, null, null, null);
        RssChannel channelA1 = new RssChannel(null, "channel A1", null, null, null, null);
        RssChannel channelA2 = new RssChannel(null, "channel A2", null, null, null, null);

        folderC.addItem(channelH);

        folderA.addItem(folderB);
        folderA.addItem(folderC);

        folderA2.addItem(channelF);
        folderA2.addItem(channelG);

        folderD.addItem(folderA2);

        mRoot.addItem(channelA1);
        mRoot.addItem(channelA2);
        mRoot.addItem(folderA);
        mRoot.addItem(folderD);
    }

    public static SubscribedFolder getRootSubscribedFolder() {
        return mRoot;
    }

    public static SubscribedItem selectItem(int id) {
        SubscribedItem item = null;

        return item;
    }

    public static List<SubscribedItem> selectChildren(int id) {
        List<SubscribedItem> items = new ArrayList<>();
        return items;
    }

    public static long insertSubscribeItem(SubscribedItem item) {
        return -1;
    }

    public static long removeSubscribeItem(int id) {
        return -1;
    }
}
