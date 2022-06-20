package by.happygnom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.happygnom.data.model.TagEntity

@Dao
interface TagsDao {

    @Query("SELECT * FROM tag ORDER BY tag.value ASC")
    fun getAll(): List<TagEntity>

    @Query("SELECT count(*) from tag")
    fun count(): Long

    @Query("SELECT * FROM tag WHERE tag.tagId==:tagId LIMIT 1")
    fun getById(tagId: Long): TagEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tags: List<TagEntity>)
}
