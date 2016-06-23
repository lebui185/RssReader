package orion.rssreader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by Long on 6/23/2016.
 */
public class FetchRssFeedTask extends AsyncTask<Void, Void, List<RssItem>> {
    Context context;
    String mAddress;
    URL url;

    public FetchRssFeedTask(String addr) {
        mAddress = addr.substring(5);
    }

    @Override
    protected List<RssItem> doInBackground(Void... params) {
        return ProcessXml(getData());
    }

    private List<RssItem> ProcessXml(Document data) {
        if (data != null) {
            ArrayList<RssItem> list = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
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
                    if(title != null && link != null && desc != null) {
                        RssItem item = new RssItem(R.drawable.rss_icon, title, desc, link);
                        list.add(item);
                        title = desc = link = null;
                    }
                }
            }
            return list;
        }
        return null;
    }

    public Document getData() {
        try {
            url = new URL(mAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
