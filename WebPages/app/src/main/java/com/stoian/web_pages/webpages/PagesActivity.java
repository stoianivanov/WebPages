package com.stoian.web_pages.webpages;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

public class PagesActivity extends Activity {
    private GridView grid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);

        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(new PagesAdapter(this));
    }
}
