package orion.rssreader;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Long on 6/23/2016.
 */
public class RetrieveFeedTask extends AsyncTask<String, Void, String> {
    String API_URL;

    public AsyncResponse delegate=null;

    RetrieveFeedTask() {
        API_URL = "http://cloud.feedly.com/v3/search/";
    }

    @Override
    protected String doInBackground(String... params) {
        // Do some validation here
        try {
            URL url = new URL(API_URL + "feeds?query=" + params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        if(delegate!=null)
        {
            delegate.postResult(response);
        }
        else
        {
            Log.e("ApiAccess", "You have not assigned AsyncResponse delegate");
        }
    }
}
