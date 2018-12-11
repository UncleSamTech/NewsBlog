package com.newsapp.samuel.newsblog.Controller;

import android.text.TextUtils;
import android.util.Log;

import com.newsapp.samuel.newsblog.Model.NewsBlogObjectClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtilsClass {

    public static final String JSON_RESPONSE = "http://www.thenewtimes.com.ng/wp-json/wp/v2/posts?_embed";
    public static final String LOG_TAG = QueryUtilsClass.class.getSimpleName();

    public QueryUtilsClass() {
    }

    /**
     * This method helps to convert our string address to url format for retreiving the json data
     *
     * @param urlAdress
     * @return url
     */
    private static URL createUrlAddress(String urlAdress) {

        URL url = null;
        try {
            url = new URL(urlAdress);
        } catch (MalformedURLException e) {
            Log.i("LOG_TAG", "Error as a result of : ", e);
        }
        return url;
    }


    /**
     * This method uses the HttpUrlConnection class to make network request
     *
     * @param url
     * @return jsonResponse
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        //check if url is null and exit early
        //return jsonResponse as empty string
        if (url == null) {
            return jsonResponse;
        }
/**
 * HttpURLConnection class is declared and assigned a null value
 * InputStream class is also used for reading data
 */
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        /**
         * line 88 the htttpURLConnection object holds the result of openConnection method called on the url object
         * line 89  the setRequestMethod is passed a GET because we are making a network request
         * line 90 the setReadTimeout is also set to 10000 milliseconds which is the time it can stay before it times out if no response
         * line 91 the setConnectTimeout is also set to 15000 milliseconds for allowable time if able to conect
         * line 92 the connect method is finally called to make a connection
         * line 93 a check is made to see if the response code received is 200 which means connection is ok
         * line 94 the condition if met, the inputStream is read and passed to the object input stream
         * line 95 the method @method readFromStream is passed the inputStream which reads it line by line builds it uses the String Builder and returns it as a string
         * the finally is used for disconnecting to avoid memory leaks and conserving our apps
         *
         *
         *
         */
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000 /*milliseconds*/);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error as a result of : " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.i(LOG_TAG, "Error as a result of ", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * This method takes a parameter of InputStream in UTF-8 standard reads it line by line, builds it with StringBuilder
     * class and returns a string of already read data
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

   static String img_src;


private static ArrayList<NewsBlogObjectClass> extractNewsDetails(String jsonValues){
ArrayList<NewsBlogObjectClass> newsBlogObjectClasses  = new ArrayList<>();

    if(TextUtils.isEmpty(jsonValues)){
        return null;
    }


    try {
        JSONArray newsRoot = new JSONArray(jsonValues);
        for(int i =0 ; i < newsRoot.length();i++){
            JSONObject values = newsRoot.getJSONObject(i);
            JSONObject title = values.optJSONObject("title");
            String news_title = title.optString("rendered");
            JSONObject media = values.optJSONObject("_embedded");
            JSONArray sizes= media.optJSONArray("wp:featuredmedia");
            for(int j = 0 ; j <sizes.length();j++){

                JSONObject thumb = sizes.optJSONObject(j);
                JSONObject full = thumb.optJSONObject("media_details");
                JSONObject final_link = full.optJSONObject("sizes");
                JSONObject final_full = final_link.optJSONObject("thumbnail");
                img_src = final_full.optString("source_url");}


            String  date = values.optString("date");
            int news_count = values.optInt("id");
            String news_string_count = String.valueOf(news_count);

            String news_link = values.optString("link");

            NewsBlogObjectClass newsBlogObjectClass = new NewsBlogObjectClass(news_title,news_link,img_src,news_string_count,date);
newsBlogObjectClasses.add(newsBlogObjectClass);
        }
    } catch (JSONException e) {
       Log.e(LOG_TAG,"Error as a result of :" + e.getMessage());
    }

return newsBlogObjectClasses;
}




public static ArrayList<NewsBlogObjectClass> fetchNewsData(String urlAddr){
    //create the url object
    URL url1 = createUrlAddress(urlAddr);


    String jsonResponse = "";

    //make http request and store the
    //response on a string object
    try {
        jsonResponse = makeHttpRequest(url1);


    } catch (IOException io) {
        Log.i(LOG_TAG, "Error as a result of : " +  io.getMessage());
    }

    ArrayList<NewsBlogObjectClass> newsBlogDetails = extractNewsDetails(jsonResponse);

    return newsBlogDetails;

}


}
