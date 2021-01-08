package com.nadikarim.submission3

import android.database.Cursor
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWER
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.NAME
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.REPOSITORY
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.nadikarim.submission3.data.model.Favorite
import java.util.ArrayList

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Favorite> {
        val favoriteList = ArrayList<Favorite>()

        notesCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val name = getString(getColumnIndexOrThrow(NAME))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val company = getString(getColumnIndexOrThrow(COMPANY))
                val location = getString(getColumnIndexOrThrow(LOCATION))
                val repository = getString(getColumnIndexOrThrow(REPOSITORY))
                val follower = getString(getColumnIndexOrThrow(FOLLOWER))
                val following = getString(getColumnIndexOrThrow(FOLLOWING))
                val favorite = getString(getColumnIndexOrThrow(FAVORITE))
                favoriteList.add(
                    Favorite(username, name, avatar, company, location, repository, follower, following, favorite)
                )
            }
        }
        return favoriteList
    }
}