package htw.gma_sose22.metronomekit.database

import androidx.annotation.WorkerThread
import htw.gma_sose22.metronomekit.beat.BeatPattern
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class BeatPatternRepository(private val patternDao: BeatPatternDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allPatterns: Flow<List<BeatPattern>> = patternDao.getAllUpdating()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun insert(beatPattern: BeatPattern) {
        patternDao.insert(beatPattern)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun delete(beatPattern: BeatPattern) {
        patternDao.delete(beatPattern)
    }

}