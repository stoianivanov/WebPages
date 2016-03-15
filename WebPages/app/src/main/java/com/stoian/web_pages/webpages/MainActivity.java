package com.stoian.web_pages.webpages;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity
        implements View.OnClickListener {
    private Button dButton;
    private Button pages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pages = (Button) findViewById(R.id.viewebpages);
        pages.setOnClickListener(this);
        dButton = (Button)findViewById(R.id.button);
        dButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == pages.getId()){
            Intent intent = new Intent(this, PagesActivity.class);
            startActivity(intent);
        }
        if(id == dButton.getId()){
            addWebPage();
        }
    }

    public void addWebPage(){
        ContentValues values = new ContentValues();
        Log.d("awp", "1");
        values.put(PageProvider.PAGE, ((EditText) findViewById(R.id.editText2)).getText().toString());
        Log.d("awp", "1");
        Uri uri = getContentResolver().insert(
                PageProvider.CONTENT_URI, values);
        Log.d("awp", "1");
        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }
}
