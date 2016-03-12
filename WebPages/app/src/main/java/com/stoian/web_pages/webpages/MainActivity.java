package com.stoian.web_pages.webpages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
        implements View.OnClickListener {
    private Button pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pages = (Button) findViewById(R.id.viewebpages);
        pages.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == pages.getId()){
            Intent intent = new Intent(this, PagesActivity.class);
            startActivity(intent);
        }
    }
}
