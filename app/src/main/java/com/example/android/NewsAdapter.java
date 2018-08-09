package com.example.android.archaeologynews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    private Context mContext;
    private ArrayList<News> mNewsList;

    public NewsAdapter(android.content.Context context, List<News> news) {
        super(context, 0, news);
        ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // find news item position in the list
        News currentNews = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID title_text_view.
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        // Get the title of the current news item and set it on the title textView
        titleTextView.setText(currentNews.getTitle());

        // Find the TextView in the list_item.xml layout with the ID section_text_view.
        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.section_text_view);
        // Get the section of the current news item and set it on the section textView
        sectionTextView.setText(currentNews.getSection());

        // Find the TextView in the list_item.xml layout with the ID author_text_view.
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_text_view);
        // Get the author of the current news item and set it on the author textView
        authorTextView.setText(currentNews.getAuthor());

        // Find the TextView in the list_item.xml layout with the ID date_text_view.
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        // Get the publication date of the current news item and set it on the date textView
        dateTextView.setText(currentNews.getDate());

        return listItemView;
    }
}