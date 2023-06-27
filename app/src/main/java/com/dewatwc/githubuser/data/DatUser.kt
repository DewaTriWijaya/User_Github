package com.dewatwc.githubuser.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "data_user")
@Parcelize
data class DatUser (
    @PrimaryKey
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "avatar") var avatar: String,
    @ColumnInfo(name = "company") var company: String,
    @ColumnInfo(name = "location") var location: String,
    @ColumnInfo(name = "repositories") var repositories: String,
    @ColumnInfo(name = "followers") var followers: String,
    @ColumnInfo(name = "following") var following: String,
    @ColumnInfo(name = "html_url") var html_url: String
) : Parcelable