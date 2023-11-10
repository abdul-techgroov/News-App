package com.news.app.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.news.app.snippet.Database.DESCRIPTION
import com.news.app.snippet.Database.ID
import com.news.app.snippet.Database.IMAGE
import com.news.app.snippet.Database.NEWS
import com.news.app.snippet.Database.TITLE

@Entity(tableName = NEWS)
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long = 0L,
    @ColumnInfo(name = TITLE)
    val title: String,
    @ColumnInfo(name = DESCRIPTION)
    val description: String,
    @ColumnInfo(name = IMAGE, typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsEntity

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}