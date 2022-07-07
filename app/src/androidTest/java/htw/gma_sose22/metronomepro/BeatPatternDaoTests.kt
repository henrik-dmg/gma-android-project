package htw.gma_sose22.metronomepro

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.BeatPattern
import htw.gma_sose22.metronomekit.database.BeatPatternDao
import htw.gma_sose22.metronomekit.database.BeatPatternDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BeatPatternDaoTests {

    private lateinit var patternDao: BeatPatternDao
    private lateinit var database: BeatPatternDatabase

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(context, BeatPatternDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        patternDao = database.beatPatternDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun testInsertAndGetPattern() = runBlocking {
        val simpleBeat = Beat(120, 4u, 10u)
        val pattern = BeatPattern(patternName = "Simple Pattern", beats = arrayOf(simpleBeat))
        patternDao.insert(pattern)

        val allPatterns = patternDao.getAllUpdating().first()
        assertEquals(allPatterns[0], pattern)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllPatterns() = runBlocking {
        val simpleBeat = Beat(120, 4u, 10u)
        val pattern = BeatPattern(patternName = "Simple Pattern", beats = arrayOf(simpleBeat))
        patternDao.insert(pattern)
        val pattern2 = BeatPattern(patternName = "Another pattern", beats = arrayOf(simpleBeat))
        patternDao.insert(pattern2)

        val allPatterns = patternDao.getAllUpdating().first()
        assertEquals(allPatterns[0], pattern)
        assertEquals(allPatterns[1], pattern2)
    }

    @Test
    @Throws(Exception::class)
    fun testCorrectCreationOrder() = runBlocking {
        val simpleBeat = Beat(120, 4u, 10u)
        val pattern = BeatPattern(patternName = "Simple Pattern", beats = arrayOf(simpleBeat))
        patternDao.insert(pattern)
        val pattern2 = BeatPattern(patternName = "Another pattern", beats = arrayOf(simpleBeat))
        patternDao.insert(pattern2)

        val allPatterns = patternDao.getAllUpdating().first()
        assertTrue(allPatterns[0].createdAt < allPatterns[1].createdAt)
    }

    @Test
    @Throws(Exception::class)
    fun testDeleteAll() = runBlocking {
        val simpleBeat = Beat(120, 4u, 10u)
        val pattern = BeatPattern(patternName = "Simple Pattern", beats = arrayOf(simpleBeat))
        patternDao.insert(pattern)
        val pattern2 = BeatPattern(patternName = "Another pattern", beats = arrayOf(simpleBeat))
        patternDao.insert(pattern2)

        patternDao.deleteAll()

        val allPatterns = patternDao.getAllUpdating().first()
        assertTrue(allPatterns.isEmpty())
    }

}
