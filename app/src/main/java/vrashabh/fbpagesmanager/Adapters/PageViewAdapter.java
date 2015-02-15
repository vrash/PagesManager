package vrashabh.fbpagesmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vrashabh.fbpagesmanager.ORMpackages.AccountsResponse;
import vrashabh.fbpagesmanager.R;

public class PageViewAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<AccountsResponse> data;


    public PageViewAdapter(Context context, ArrayList<AccountsResponse> data) {
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
        ViewHolderItem viewHolder;
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.pagesrow, null);
            viewHolder = new ViewHolderItem();
            viewHolder.pageName = (TextView) vi.findViewById(R.id.pagename);
            viewHolder.category = (TextView) vi.findViewById(R.id.pagetyper);
            vi.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) vi.getTag();

        }


        viewHolder.pageName.setText(data.get(position).getName().toString());
        viewHolder.pageName.setTextColor(vi.getResources().getColor(R.color.white));

        viewHolder.category.setText(data.get(position).getCategory().toString());
        viewHolder.category.setTextColor(vi.getResources().getColor(R.color.white));

        return vi;
    }

    //ViewHolder pattern
    static class ViewHolderItem {
        TextView pageName;
        TextView category;
    }
}