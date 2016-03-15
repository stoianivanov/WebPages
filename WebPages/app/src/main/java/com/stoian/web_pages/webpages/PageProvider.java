package com.stoian.web_pages.webpages;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by stoian on 16-3-12.
 */
public class PageProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.stoian.web_pages.Pages";
    static final String URL = "content://" + PROVIDER_NAME + "/pages";
    static final Uri CONTENT_URI = Uri.parse(URL);


    static final String _ID = "_id";
    static final String PAGE = "page";

    private static HashMap<String, String> PAGE_MAP;

    static final int PAGES = 1;
    static final int PAGES_ID = 2;


    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "pages", PAGES);
        uriMatcher.addURI(PROVIDER_NAME, "pages/#", PAGES_ID);
    }
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "Pages";
    static final String PAGES_TABLE_NAME = "pages";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + PAGES_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " page TEXT NOT NULL);";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  PAGES_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        Log.d("DB", "da");
        db = dbHelper.getWritableDatabase();
        return (db == null)? false: true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PAGES_TABLE_NAME);

        switch(uriMatcher.match(uri)){
            case PAGES:
                qb.setProjectionMap(PAGE_MAP);
                break;
            case PAGES_ID:
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if(s1 == null || s1 == ""){
            s1= PAGE;
        }

        Cursor cursor = qb.query(db, strings, s, strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all student records
             */
            case PAGES:
                return "vnd.android.cursor.dir/vnd.stoian.web_pages.pages";

            /**
             * Get a particular student
             */
            case PAGES_ID:
                return "vnd.android.cursor.item/vnd.stoian.web_pages.pages";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d("insert1", "1");
        long rowID =db.insert(PAGES_TABLE_NAME, "", contentValues);
        Log.d("insert", "1");
        if(rowID > 0){
            Log.d("insert", "1");
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            Log.d("insert", "1");
            getContext().getContentResolver().notifyChange(_uri, null);
            Log.d("insert", "1");
            return _uri;
        }
        Log.d("insert", "1");
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case PAGES:
                count = db.delete(PAGES_TABLE_NAME,s, strings);
                break;
            case PAGES_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(PAGES_TABLE_NAME, _ID + "=" + id +
                        (!TextUtils.isEmpty(s)?"AND (" + s + ")":""), strings);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case PAGES:
                count = db.update(PAGES_TABLE_NAME, contentValues, s, strings);
                break;
            case PAGES_ID:
                String id = uri.getPathSegments().get(1);
                count = db.update(PAGES_TABLE_NAME, contentValues, _ID + "=" + uri.getPathSegments().get(1) +
                (!TextUtils.isEmpty(s) ? "AND (" + s + ")":""), strings);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
