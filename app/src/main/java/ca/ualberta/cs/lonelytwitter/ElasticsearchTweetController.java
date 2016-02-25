package ca.ualberta.cs.lonelytwitter;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import static io.searchbox.core.Index.*;

/**
 * Created by esports on 2/17/16.
 */
public class ElasticsearchTweetController {
    private static JestDroidClient client;  // android used the wrong http shit so we need to put this shit here

    //TODO: A function that gets tweets
    public static ArrayList<Tweet> getTweets() {
        verifyConfig();

        // TODO: DO THIS.
        return null;
    }

    /* Obsolete due to the function below this
     //TODO: A function that adds a tweet
    public static void addTweet(Tweet tweet) {
        verifyConfig();

        Index index = new Builder(tweet).index("testing").type("tweet").build();

        try {
            DocumentResult execute = client.execute(index);
            if (execute.isSucceeded()) {
                tweet.setId(execute.getId());
            } else {
                Log.e("TODO", "Our insert of tweet failed, oh no!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    public static class GetTweetsTask extends AsyncTask<String, Void, ArrayList<NormalTweet>> {
        @Override
        protected ArrayList<NormalTweet> doInBackground(String... params) {
            verifyConfig();

            // Hold (eventually) the tweets that we get back from Elasticsearch.
            ArrayList<NormalTweet> tweets = new ArrayList<NormalTweet>();

            // NOTE: A HUGE ASSUMPTION IS ABOUT TO BE MADE!
            // Assume that only one string is passed in
            /* added by sangsoo from: https://github.com/searchbox-io/Jest/blob/master/jest/src/test/java/io/searchbox/core/SearchIntegrationTest.java
        http://www.searchly.com/documentation/developer-api-guide/java-jest/

        String query = "{\n" +
                "    \"query\": {\n" +
                "        \"filtered\" : {\n" +
                "            \"query\" : {\n" +
                "                \"query_string\" : {\n" +
                "                    \"query\" : \"test\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"filter\" : {\n" +
                "                \"term\" : { \"user\" : \"kimchy\" }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        */
            // String search_string = params[0];


            // the following gets the top 10000 tweets matching the string passed in
            String search_string = "{\"from\":0,\"size\":10000,\"query\":{\"match\":{\"message\":\""+ params[0] + "\"}}}";

            Search search = new Search.Builder(search_string).addIndex("testing").addType("tweet").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()) {
                    List<NormalTweet> foundTweets = execute.getSourceAsObjectList(NormalTweet.class); // deprecated but it's okay to use
                    tweets.addAll(foundTweets);
                } else {
                    Log.i("TODO", "Search was unsuccessful, do something!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tweets;
        }

    }

    public static class AddTweetTask extends AsyncTask< Tweet, Void, Void>{ // must be capital V for void here.
        @Override
        protected Void doInBackground(Tweet... params){
            verifyConfig();

            //for (int i = 0; i < params.length; i++){} does the same thing as below: for C++ as well.
            // the (Tweet... <-- 3 dots means an array)

            for (Tweet tweet : params) {

                Index index = new Builder(tweet).index("testing").type("tweet").build();

                try {
                    DocumentResult execute = client.execute(index);
                    if (execute.isSucceeded()) {
                        tweet.setId(execute.getId());
                    } else {
                        Log.e("TODO", "Our insert of tweet failed, oh no!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }
    }

    // If no client, add one
    public static void verifyConfig(){
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080"); // shove your url  in there
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}

