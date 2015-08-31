package com.maialovic.personalaudioplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andrea on 07/08/14.
 */
public class CustomAdapterAlbum extends ArrayAdapter<String> {
    // View lookup cache

    private static class ViewHolder {
        TextView Name;
        TextView info;
        ImageView icon;
    }

    public CustomAdapterAlbum(Context context, ArrayList<String> users) {
        super(context, R.layout.rowlayout2, users);
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
            convertView = inflater.inflate(R.layout.rowlayout2, parent, false);
            viewHolder.Name = (TextView) convertView.findViewById(R.id.label);
            viewHolder.info = (TextView) convertView.findViewById(R.id.label2);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.Name.setText(user);
        int cont = 0;
        int pos = 0, pos2;
        while (!(Player.songs_sorted_album.get(pos).getmAlbum().compareTo( user ) == 0))
            if (pos < Player.songs.size() - 1)
                pos++;

        pos2 = pos;

        while (Player.songs_sorted_album.get(pos2).getmAlbum().compareTo( user ) == 0){
            cont++;

            if (pos2 < Player.songs.size() - 1)
                pos2++;
            else
                break;
        }
        viewHolder.info.setText(cont + " songs");
        viewHolder.icon.setImageBitmap(Player.songs_sorted_album.get(pos).getmCover());
        viewHolder.info.setText(Player.songs_sorted_album.get(pos).getmAuthor() + " - " + cont + " songs");
        viewHolder.info.setTextSize(14);
        viewHolder.Name.setTextSize(18);
        // Return the completed view to render on screen
        return convertView;
    }
}
