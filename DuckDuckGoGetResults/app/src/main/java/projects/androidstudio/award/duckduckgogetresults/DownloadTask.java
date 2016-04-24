package projects.androidstudio.award.duckduckgogetresults;

import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by award on 4/23/2016.
 * Asynchronously get Bitmap image (to avoid NetworkOnMainException
 * Set
 */
public class DownloadTask extends AsyncTask<String, Void, Boolean> {
    ImageView v;
    String url;
    Bitmap bm;

    /** Constructor
     * @param v - view passed in from MySimpleAdapter class
     */
    public DownloadTask(ImageView v) {
        this.v = v;
    }

    /** calls class that gets Bitmap image
     * in background
     * @param params - url from data in MySimpleAdapter class to load image
     * @return - returns true
     */
    @Override
    protected Boolean doInBackground(String... params) {
        url = params[0];
        bm = loadBitmap(url);
        return true;
    }

    /** after do in  backgroup
     * sets bitmap image to view
     * @param result -
     */
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        v.setImageBitmap(bm);
    }

    /** this does the work
     * takes in url from MySimpleAdapter class
     * as long as url is not empty
     * open up connection and get bitmap image
     * catch exception if it fails
     * @param url - from MySimpleAdapter class
     * @return - returns bitmap image or null
     */
    public static Bitmap loadBitmap(String url) {
        if(!url.equals("") && !url.isEmpty() && url.length() != 0) {
            try {
                URL newurl = new URL(url);
                return BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}