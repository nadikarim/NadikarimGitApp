package com.nadikarim.consumerapp.data

import android.database.Cursor
import com.nadikarim.consumerapp.data.model.Favorite
import com.nadikarim.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.nadikarim.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.nadikarim.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import com.nadikarim.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWER
import com.nadikarim.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import com.nadikarim.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.nadikarim.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.NAME
import com.nadikarim.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.REPOSITORY
import com.nadikarim.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.USERNAME
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