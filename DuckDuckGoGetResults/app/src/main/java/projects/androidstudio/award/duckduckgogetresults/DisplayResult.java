package projects.androidstudio.award.duckduckgogetresults;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.loopj.android.http.*;
import org.json.JSONArray;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class gets the json data and parses and then calls the adapter to expand listview
 */
@TargetApi(23)
public class DisplayResult extends AppCompatActivity {

    ListView lview;

    /** onCreate
     * @param savedInstanceState - get instance state from last activity
     * gets json result from query passed over in intent (asynchronously)
     * set layout used (activity_display_result)
     * stores in ArrayList<HashMap<String,Object>>
     * using getMap method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        Intent intent = getIntent();
        String message = intent.getStringExtra(GetQueryActivity.EXTRA_MESSAGE);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        // does json call
        client.get(message, params, new JsonHttpResponseHandler() {
            /** if call is successful, do the work
             * @param statusCode - not used
             * @param headers - not used
             * @param response - jsonObject to parse
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //stores parsed results here
                ArrayList<HashMap<String,Object>> list_of_results = new ArrayList<>();
                // Handles JSON response here
                try {
                    //grab related topics
                    JSONArray related_topics = response.getJSONArray("RelatedTopics");
                    for (int i=0; i<related_topics.length(); i++) {
                        JSONObject relTopics = related_topics.optJSONObject(i);
                        //if its a result
                        if (relTopics.has("Result")) {
                            list_of_results.add(getMap(relTopics, "Results "));
                        }
                        //if its a topic
                        else if (relTopics.has("Topics")) {
                            //get jsonarray and then loop through topics
                            JSONArray newTopic = relTopics.getJSONArray("Topics");
                            for (int j = 0; j < newTopic.length(); j++) {
                                JSONObject topicOBJ = newTopic.getJSONObject(j);
                                //send corresponding topic name
                                list_of_results.add(getMap(topicOBJ, "Topics: " + relTopics.optString("Name")));
                            }
                        }
                    }
                }
                //catch exception
                catch (Exception e) {
                    e.printStackTrace();
                }

                // Keys used in HashMap
                String[] from = {"Icon", "Result", "URL", "Height", "Width", "FirstURL", "Text", "Name"};

                // Ids of views in list_view_layout
                int[] to = {R.id.iconView, R.id.resultView, R.id.urlView, R.id.heightView, R.id.widthView, R.id.firstUrlView, R.id.textView, R.id.nameView};

                //set adapter using MySimpleAdapter class, similar to SimpleAdapter class, but allows for HashMap<String, Object> for images
                MySimpleAdapter adapter = new MySimpleAdapter(DisplayResult.this, list_of_results, R.layout.list_view_layout, from, to);

                //set view
                lview = (ListView) findViewById(R.id.listView);

                //if view exists, setAdapter for veiw to created adapter from above
                if(lview != null) {
                    lview.setAdapter(adapter);
                }
            }

            /** onFailure method
             * @param statusCode - statuscode
             * @param headers - array of headers
             * @param res - string result
             * @param t - throwable t
             * handles potential failure of calling jsonHttpResponse
             * creates texview that shows res
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                TextView result = (TextView) findViewById(R.id.resultView);
                if(result != null) {
                    result.setText(res);
                }
            }

        });
    }

    /** getMap method for getting data and storing to hashmap to put into arraylist
     * @param relTopics - object to get data out of
     * @param type - Result or Topic - Name
     * @return - HashMap to put into ArrayList
     */
    public HashMap<String, Object> getMap(JSONObject relTopics, String type){
        String result = relTopics.optString("Result");
        JSONObject icon = relTopics.optJSONObject("Icon");
        String url = icon.optString("URL");
        String height = icon.optString("Height");
        String width = icon.optString("Width");
        String firstUrl = relTopics.optString("FirstURL");
        String text = relTopics.optString("Text");

        HashMap<String, Object> result_list = new HashMap<>();

        result_list.put("Result", "Result: " + result);
        result_list.put("URL", "Icon URL: " + url);
        result_list.put("Height", "Icon Height: " + height);
        result_list.put("Width", "Icon Width: " + width);
        result_list.put("FirstURL", "First URL: " + firstUrl);
        result_list.put("Text", "Text: " + text);
        result_list.put("Name", type);
        result_list.put("Icon", url);

        return result_list;
    }
}
