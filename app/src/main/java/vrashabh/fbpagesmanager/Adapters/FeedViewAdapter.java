package vrashabh.fbpagesmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.feedrow, null);
        if (data.get(position).getType() == "photo") {
            ImageView imageView = (ImageView) vi.findViewById(R.id.feedImage);
            Picasso.with(context).load(data.get(position).getPicture()).into(imageView);
        }

        return vi;
    }
}