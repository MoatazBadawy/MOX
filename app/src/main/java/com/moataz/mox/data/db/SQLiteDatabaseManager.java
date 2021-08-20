package com.moataz.mox.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class SQLiteDatabaseManager {

    SQLHelper helper;

    public SQLiteDatabaseManager(Context context) {
        helper = new SQLHelper(context);
    }

    //----------------Favorites-----------------//

    public long insertFavorite(Favorite favorite){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHelper.TITLE, favorite.getTitle());
        contentValues.put(SQLHelper.Image, favorite.getImage());
        contentValues.put(SQLHelper.Link, favorite.getLink());
        contentValues.put(SQLHelper.Author, favorite.getAuthor());



        long id = db.insert(SQLHelper.TABLE_Name_FAVORITES, null, contentValues);
        db.close();

        //-----------Will return -1 if not success --------//
        return id;
    }

    public boolean isFavorite(String path){

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columns = {SQLHelper.Image};
        String[] args = {String.valueOf(path)};

        Cursor cursor = db.query(SQLHelper.TABLE_Name_FAVORITES, columns, SQLHelper.Image + " = ? ", args, null, null, null);

        boolean isFavorite = cursor.moveToFirst() && cursor.getCount() >= 1;

        cursor.close();

        return isFavorite;

    }

    public int deleteFavorite(String path){

        SQLiteDatabase db = helper.getWritableDatabase();

        String[] args = {String.valueOf(path)};

        int count = db.delete(SQLHelper.TABLE_Name_FAVORITES, SQLHelper.Image + " = ? ", args);

        db.close();

        return count;
    }

    public List<Favorite> getFavoriteData(){

        List<Favorite> favoriteList = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(SQLHelper.TABLE_Name_FAVORITES, null, null, null, null, null, null);

        while (cursor.moveToNext()){

            favoriteList.add(new Favorite(
                    cursor.getInt(cursor.getColumnIndex(SQLHelper.ID)),
                    cursor.getString(cursor.getColumnIndex(SQLHelper.TITLE)),
                    cursor.getString(cursor.getColumnIndex(SQLHelper.Image)),
                    cursor.getString(cursor.getColumnIndex(SQLHelper.Link)),
                    cursor.getString(cursor.getColumnIndex(SQLHelper.Author))

            ));
        }

        cursor.close();

        return favoriteList;
    }

    //--------------------------------//

    public class SQLHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "appDatabase";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_Name_FAVORITES = "favorites";

        private static final String ID = "id";

        private static final String TITLE = "title";
        private static final String Image = "path";
        private static final String Link = "link";
        private static final String Author = "author";

        private static final String CREATE_TABLE_FAVORITES = "CREATE TABLE " + TABLE_Name_FAVORITES + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                TITLE + " VARCHAR(250), " +
                Link + " VARCHAR(500), " +
                Author + " VARCHAR(250), " +
                Image + " VARCHAR(250));";
//-----------------------------------------------------------------------------//
        private static final String TABLE_Name_DOWNLOADS = "downloads";

        private static final String CREATE_TABLE_DOWNLOADS = "CREATE TABLE " + TABLE_Name_DOWNLOADS + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " VARCHAR(250), " +
                Link + " VARCHAR(500), " +
                Author + " VARCHAR(250), " +
                Image + " TEXT);";

        Context context;

        public SQLHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_FAVORITES);
            db.execSQL(CREATE_TABLE_DOWNLOADS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

}
