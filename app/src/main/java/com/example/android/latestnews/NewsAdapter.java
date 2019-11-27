package com.example.android.latestnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vanshika on 5/6/17.
 *
 * An {@link NewsAdapter} that populates a ListView with {@link News} objects
 */
public class NewsAdapter extends ArrayAdapter<News>{

    public NewsAdapter(Context context, ArrayList<News> news){
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        News currentNews = getItem(position);

        // Find text view that displays the story title by the id title_text
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text);
        //Set the text retrieved by JSON results on it.
        titleTextView.setText(currentNews.getNewsHeading());

        // Find text view that displays the story title by the id title_text
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description_text);
        //Set the text retrieved by JSON results on it.
        descriptionTextView.setText(currentNews.getNewsDescription());

        return listItemView;
    }
}
