package com.maialovic.personalaudioplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andrea on 16/08/14.
 */
public class CustomAdapterSinger extends ArrayAdapter<String> {
    // View lookup cache
    private static class ViewHolder {
        TextView Name;
        TextView info;
    }
    final ArrayList<Integer> alb;
    final ArrayList<Integer> songs_n;
    final ArrayList<String> author;

    public CustomAdapterSinger(Context context, ArrayList<String> gg, ArrayList<Integer> album, ArrayList<Integer> album_n) {
        super(context, R.layout.rowlayout3, gg);

        alb = new ArrayList<Integer>(album);
        songs_n = new ArrayList<Integer>(album_n);
        author = new ArrayList<String>(gg);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String user = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.rowlayout3, parent, false);
            viewHolder.Name = (TextView) convertView.findViewById(R.id.label);
            viewHolder.info = (TextView) convertView.findViewById(R.id.label2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.Name.setText(user);

        viewHolder.info.setText(alb.get(position) + " albums - " + songs_n.get(position) + " songs");
        viewHolder.info.setTextSize(14);
        viewHolder.Name.setTextSize(18);
        return convertView;
    }
}

