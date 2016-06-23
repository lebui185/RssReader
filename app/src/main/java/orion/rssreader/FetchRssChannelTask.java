package orion.rssreader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchRssChannelTask extends BaseAsyncTask<Void, Void, List<RssChannel>> {

    private static final String API_URL = "http://cloud.feedly.com/v3/search/";
    private String mSearchQuery;

    public FetchRssChannelTask(String query) {
        mSearchQuery = query;
    }

    @Override
    protected List<RssChannel> doAsyncTask() throws Exception {
        String response = getAPIResponse();
        return parseResponse(response);
    }

    private String getAPIResponse() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        URL url = null;
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;

        try {
            url = new URL(API_URL + "feeds?query=" + mSearchQuery);
            connection = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return stringBuilder.toString();
    }

    private List<RssChannel> parseResponse(String response) throws Exception {
        List<RssChannel> channels = new ArrayList<>();

        JSONObject jsonRootObject = new JSONObject(response);

        //Get the instance of JSONArray that contains JSONObjects
        JSONArray jsonArray = jsonRootObject.optJSONArray("results");

        //Iterate the jsonArray and print the info of JSONObjects
        for(int i=0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String feedId = jsonObject.optString("feedId").toString();
            String title = jsonObject.optString("title").toString();
            String website = jsonObject.optString("website").toString();
            String description = jsonObject.optString("description").toString();
            String iconUrl = jsonObject.optString("iconUrl").toString();
            String visualUrl = jsonObject.optString("visualUrl").toString();

            channels.add(new RssChannel(feedId, title, website, description, iconUrl, visualUrl));
        }

        return channels;
    }
}
