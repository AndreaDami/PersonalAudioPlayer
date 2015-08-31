package com.maialovic.personalaudioplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andrea on 07/08/14.
 */
public class CustomAdapterGender extends ArrayAdapter<String> {
    // View lookup cache
    private static class ViewHolder {
        TextView Name;
        TextView info;

    }

    public CustomAdapterGender(Context context, ArrayList<String> users) {
        super(context, R.layout.rowlayout3, users);
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
        // Populate the data into the template view using the data object
        viewHolder.Name.setText(user);
        int cont = 0;
        for (short pos = 0; pos < Player.songs.size(); pos++) {
            if (Player.songs.get(pos).getmGender().compareTo( user) == 0) {
                cont++;
            }
        }
        viewHolder.info.setText(cont + " songs");
        viewHolder.info.setTextSize(14);
        viewHolder.Name.setTextSize(18);

        // Return the completed view to render on screen
        return convertView;
    }
}
