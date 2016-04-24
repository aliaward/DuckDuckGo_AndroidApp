package projects.androidstudio.award.duckduckgogetresults;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by award on 4/23/2016.
 * Original activity to start app
 * uses activity_get_query layout
 */
@TargetApi(23)
public class GetQueryActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "projects.androidstudio.award.duckduckgogetresults.MESSAGE";

    /** onCreate
     * set content view to correct layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_query);
    }

    /** Called when the user clicks the Search button
     * Creates intent for the DisplayResult.class to handle the query
     * Grabs query
     * puts in intent and starts activity
     * */
    public void send_search(View view) {
        Intent intent = new Intent(this, DisplayResult.class);
        EditText editText = (EditText) findViewById(R.id.search_qry);
        String qry = "";
        if(editText != null) {
            qry = editText.getText().toString();
        }
        String message = "http://api.duckduckgo.com/?q=" + qry + "&format=json";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
