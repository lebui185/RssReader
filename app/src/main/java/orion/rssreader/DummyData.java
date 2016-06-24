package orion.rssreader;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<RssFeed> getTodayFeeds() {
        List<RssFeed> rssFeeds = new ArrayList<>();

//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));

        return rssFeeds;
    }

    public static List<RssChannel> searchChannels(String query) {
        List<RssChannel> rssChannels = new ArrayList<>();

//        rssChannels.add(new RssChannel("Channel 1"));
//        rssChannels.add(new RssChannel("Channel 2"));
//        rssChannels.add(new RssChannel("Channel 3"));
//        rssChannels.add(new RssChannel("Channel 4"));
//        rssChannels.add(new RssChannel("Channel 5"));
//        rssChannels.add(new RssChannel("Channel 6"));
//        rssChannels.add(new RssChannel("Channel 7"));
//        rssChannels.add(new RssChannel("Channel 8"));

        return rssChannels;
    }

    public static List<RssFeed> getFeeds(RssChannel channel) {
        List<RssFeed> rssFeeds = new ArrayList<>();

        String feedTitle = channel.getTitle();

//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssFeeds.add(new RssFeed(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));

        return rssFeeds;
    }


    public static SubscribedFolder getRoot() {
        SubscribedFolder category = getFolderData("root");
        SubscribedFolder folder1 = getFolderData("Folder 1");
        SubscribedFolder folder2 = getFolderData("Folder 2");
        SubscribedFolder folder3 = getFolderData("Folder 3");
        SubscribedFolder folder4 = getFolderData("Folder 4");
        SubscribedFolder folder5 = getFolderData("Folder 5");

        category.addItem(folder1);
        category.addItem(folder2);
        category.addItem(folder3);
        category.addItem(folder4);
        folder1.addItem(folder5);

        return category;
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

        //rssFeeds.add(new RssFeed(1, "", ""));

        return rssFeeds;
    }

    public static List<RssFeed> getBookmarkFeeds() {
        List<RssFeed> rssFeeds = null;

        //rssFeeds.add(new RssFeed(1, "", ""));

        return rssFeeds;
    }

    public static List<RssFeed> getFeeds(String category) {
        List<RssFeed> rssFeeds = null;

        //rssFeeds.add(new RssFeed(1, "", ""));

        return rssFeeds;
    }
}
