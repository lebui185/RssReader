package orion.rssreader;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<RssItem> getTodayFeeds() {
        List<RssItem> rssItems = new ArrayList<>();

//        rssItems.add(new RssItem(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, "Lorem Ipsum", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));

        return rssItems;
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

    public static List<RssItem> getFeeds(RssChannel channel) {
        List<RssItem> rssItems = new ArrayList<>();

        String feedTitle = channel.getTitle();

//        rssItems.add(new RssItem(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));
//        rssItems.add(new RssItem(R.drawable.rss_icon, feedTitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet"));

        return rssItems;
    }


    public static SubscribedFolder getRoot(){
        SubscribedFolder category = getFolderData("root");
        SubscribedFolder folder1 = getFolderData("Folder 1");
        SubscribedFolder folder2 = getFolderData("Folder 2");
        SubscribedFolder folder3= getFolderData("Folder 3");
        SubscribedFolder folder4= getFolderData("Folder 4");
        SubscribedFolder folder5= getFolderData("Folder 5");

        category.addItem(folder1);
        category.addItem(folder2);
        category.addItem(folder3);
        category.addItem(folder4);
        folder1.addItem(folder5);

        return category;
    }

    public static SubscribedFolder getFolderData(String name) {
        SubscribedFolder folder = new SubscribedFolder(name);
        folder.addItem(new RssChannel(null,"My_channel 1",null,null,null,null));
        folder.addItem(new RssChannel(null,"My_channel 2",null,null,null,null));
        folder.addItem(new RssChannel(null,"My_channel 3",null,null,null,null));
        return folder;
    }

    public static List<RssItem> getRecentFeeds() {
        List<RssItem> rssItems = null;

        //rssItems.add(new RssItem(1, "", ""));

        return rssItems;
    }

    public static List<RssItem> getBookmarkFeeds() {
        List<RssItem> rssItems = null;

        //rssItems.add(new RssItem(1, "", ""));

        return rssItems;
    }

    public static List<RssItem> getFeeds(String category) {
        List<RssItem> rssItems = null;

       //rssItems.add(new RssItem(1, "", ""));

        return rssItems;
    }
}
