package com.stoian.web_pages.webpages;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class PagesAdapter extends BaseAdapter {

    private Context context;
    private String[] pages = {
            "https://www.facebook.com/",
            "http://www.blagoevgrad.eu/",
            "https://www.google.bg/"
    };

    public PagesAdapter(Context c){
        this.context = c;
    }
    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        WebView mView;
        
        if(view == null){
            mView = new WebView(context);
            mView.setLayoutParams(new GridView.LayoutParams(85, 85));
            mView.setPadding(8,8,8,8);

        } else {
            mView = (WebView) view;
        }
        mView.loadUrl(pages[i]);
        return  mView;
    }
}
