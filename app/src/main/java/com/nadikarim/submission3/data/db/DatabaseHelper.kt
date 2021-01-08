package com.nadikarim.submission3.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user_db"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.FavoriteColumns.USERNAME} TEXT PRIMARY KEY  NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.AVATAR} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.COMPANY} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.LOCATION} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.REPOSITORY} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.FOLLOWER} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.FOLLOWING} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}