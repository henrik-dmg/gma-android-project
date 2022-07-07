package htw.gma_sose22.metronomekit.database

import androidx.room.*
import htw.gma_sose22.metronomekit.beat.BeatPattern
import kotlinx.coroutines.flow.Flow

@Dao
interface BeatPatternDao {
    @Query("SELECT * FROM beatpattern ORDER BY createdAt")
    fun getAll(): List<BeatPattern>

    @Query("SELECT * FROM beatpattern ORDER BY createdAt")
    fun getAllUpdating(): Flow<List<BeatPattern>>

    @Query("SELECT * FROM beatpattern WHERE patternName LIKE :name LIMIT 1")
    suspend fun findByName(name: String): BeatPattern

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pattern: BeatPattern)

    @Delete
    suspend fun delete(pattern: BeatPattern)

    @Update
    suspend fun update(pattern: BeatPattern)

    @Query("DELETE FROM beatpattern")
    suspend fun deleteAll()
}