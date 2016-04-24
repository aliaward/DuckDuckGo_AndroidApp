package projects.androidstudio.award.duckduckgogetresults;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * Created by award on 4/23/2016.
 * MySimpleAdapter is a take on the SimpleAdapter class
 * reads in similar constructors to SimpleAdapter, except it allows for
 * ArrayList<HashMap<String, Object>> so that I could include a picture
 */
public class MySimpleAdapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater = null;

    /** Constructor definition
     * @param context - current context
     * @param data - populated with data from json
     * @param resource - layout to use
     * @param from - keys array
     * @param to - views ids array to map key values to
     */
    public MySimpleAdapter(Context context, ArrayList<? extends HashMap<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /** ViewHolder for holding views for listview */
    public static class ViewHolder
    {
        TextView result;
        TextView url;
        TextView height;
        TextView width;
        TextView firstUrl;
        TextView text;
        TextView name;
    }

    /** this class does all of the work
     * @param position - current position
     * @param convertView - inflated listview with appropriate views
     * @param parent - current parent
     * @return inflated and populated view
     * creates holder for views and inflates listview
     * to include types of views specified in list_view_layout
     * grabs data at current position, finds appropriate ids
     * for holder fields and sets data to fields
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new MySimpleAdapter.ViewHolder();
        if(convertView==null)
            convertView = inflater.inflate(R.layout.list_view_layout, parent, false);

        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);

        new DownloadTask((ImageView) convertView.findViewById(R.id.iconView))
                .execute((String) data.get("Icon"));
        holder.result = (TextView) convertView.findViewById(R.id.resultView);
        holder.url = (TextView) convertView.findViewById(R.id.urlView);
        holder.height = (TextView) convertView.findViewById(R.id.heightView);
        holder.width = (TextView) convertView.findViewById(R.id.widthView);
        holder.firstUrl = (TextView) convertView.findViewById(R.id.firstUrlView);
        holder.text = (TextView) convertView.findViewById(R.id.textView);
        holder.name = (TextView) convertView.findViewById(R.id.nameView);
        if(data.get("Name") != null)
            holder.name.setText(data.get("Name").toString());
        if (data.get("Result") != null) {
            holder.result.setText(Html.fromHtml(data.get("Result").toString()));
            holder.result.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (data.get("URL") != null)
            holder.url.setText(data.get("URL").toString());
        if (data.get("Height") != null)
            holder.height.setText(data.get("Height").toString());
        if (data.get("Width") != null)
            holder.width.setText(data.get("Width").toString());
        if (data.get("FirstURL") != null)
            holder.firstUrl.setText(Html.fromHtml(data.get("FirstURL").toString()));
        if (data.get("Text") != null)
            holder.text.setText(data.get("Text").toString());

        convertView.setTag(holder);
        return convertView;
    }

}