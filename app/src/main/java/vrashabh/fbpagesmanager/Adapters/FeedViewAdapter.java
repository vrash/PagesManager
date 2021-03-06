package vrashabh.fbpagesmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vrashabh.fbpagesmanager.ORMpackages.FeedData;
import vrashabh.fbpagesmanager.R;

public class FeedViewAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<FeedData> data;

    public FeedViewAdapter(Context context, ArrayList<FeedData> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolderItem viewHolder;
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.feedrow, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.feedrelative = (RelativeLayout) vi.findViewById(R.id.feedrelative);
            viewHolder.imageView = (ImageView) vi.findViewById(R.id.feedImage);
            viewHolder.feedName = (TextView) vi.findViewById(R.id.feedname);
            viewHolder.creationTime = (TextView) vi.findViewById(R.id.creationTime);
            viewHolder.feedType = (TextView) vi.findViewById(R.id.feedtype);
            vi.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) vi.getTag();
        }

        if (!data.get(position).isPublished())
            viewHolder.feedrelative.setBackgroundColor(vi.getResources().getColor(R.color.gray));
        else
            viewHolder.feedrelative.setBackgroundColor(vi.getResources().getColor(R.color.white));

        if (data.get(position).getType().equals("photo")) {

            Picasso.with(context).load(data.get(position).getPicture()).into(viewHolder.imageView);
            viewHolder.feedName.setText(data.get(position).getStory() == null ? "" : data.get(position).getStory().toString());
            //viewHolder.feedType.setText(data.get(position).getType() == null ? "" : data.get(position).getType().toString());
        } else if ((data.get(position).getType().equals("status"))) {

            viewHolder.feedName.setText(data.get(position).getMessage() == null ? "" : data.get(position).getMessage().toString());
            //viewHolder.feedType.setText(data.get(position).getType() == null ? "" : data.get(position).getType().toString());

        } else if ((data.get(position).getType().equals("link"))) {
            if (data.get(position).getPicture() != null)
                Picasso.with(context).load(data.get(position).getPicture()).into(viewHolder.imageView);
            viewHolder.feedName.setText(data.get(position).getMessage() == null ? "" : data.get(position).getMessage().toString());
            viewHolder.feedType.setText(data.get(position).getLink() == null ? "" : data.get(position).getLink().toString());
        }
        viewHolder.creationTime.setText(data.get(position).getCreated_time() == null ? "" : data.get(position).getCreated_time().toString());
        // TextView feedType = (TextView) vi.findViewById(R.id.feedtype);
        //feedType.setText(data.get(position).getLink().toString());

        return vi;
    }

    //ViewHolderPattern
    static class ViewHolderItem {
        TextView feedName;
        TextView creationTime;
        ImageView imageView;
        TextView feedType;
        RelativeLayout feedrelative;

    }
}