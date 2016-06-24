package orion.rssreader;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FetchRssFeedTask extends BaseAsyncTask<Void, Void, List<RssFeed>> {
    Context context;
    String mAddress;
    URL url;

    public FetchRssFeedTask(String addr) {
        mAddress = addr.substring(5);
    }

    @Override
    protected List<RssFeed> doAsyncTask() throws Exception {
        return ProcessXml(getData());
    }

    private List<RssFeed> ProcessXml(Document data) {
        if (data != null) {
            ArrayList<RssFeed> list = new ArrayList<>();
            Element root = data.getDocumentElement();
            NodeList channels = root.getElementsByTagName("channel");
            Node channel = channels.item(0);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node curChild = items.item(i);
                if (curChild.getNodeName().equalsIgnoreCase("item")) {
                    String title = null, desc = null, link = null;
                    NodeList itemChilds = curChild.getChildNodes();
                    for (int j = 0; j < itemChilds.getLength(); j++) {
                        Node cur = itemChilds.item(j);
                        if (cur.getNodeName().equalsIgnoreCase("title")) {
                            title = cur.getTextContent();
                        } else if (cur.getNodeName().equalsIgnoreCase("link")) {
                            link = cur.getTextContent();
                        } else if (cur.getNodeName().equalsIgnoreCase("description")) {
                            String regex = "/(<([^>]+)>)/ig";
                            desc = cur.getTextContent().replaceAll(regex, "");
                        }
                    }
                    if (title != null && link != null && desc != null) {
                        RssFeed item = new RssFeed(R.drawable.rss_icon, title, desc, link);
                        list.add(item);
                        title = desc = link = null;
                    }
                }
            }
            return list;
        }
        return null;
    }

    public Document getData() throws Exception {
        url = new URL(mAddress);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        InputStream inputStream = conn.getInputStream();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDoc = builder.parse(inputStream);
        return xmlDoc;
    }
}
