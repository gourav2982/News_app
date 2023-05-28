package com.example.news_app;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class NewsModels {
    String title,descripton;
    String image;
    String published;
    String url;

    public String getPublished() {
        return published;
    }

    public String getTitle() {
        return title;
    }

    public String getDescripton() {
        return descripton;
    }

    public String getImage() {
        return image;
    }
}
