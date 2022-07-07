package htw.gma_sose22.metronomekit.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.BeatPattern
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [BeatPattern::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(DatabaseConverters::class)
abstract class BeatPatternDatabase : RoomDatabase() {

    abstract fun beatPatternDao(): BeatPatternDao

    private class BeatPatternDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val beatPatternDao = database.beatPatternDao()

                    // Delete all content here.
                    beatPatternDao.deleteAll()

                    // Add sample patterns
                    val simpleBeat = Beat(120, 4u, 10u, setOf(0u), setOf())
                    val anotherSimpleBeat = Beat(120, 3u, 5u, setOf(0u), setOf(1u))

                    val pattern = BeatPattern(beats = arrayOf(simpleBeat, anotherSimpleBeat))
                    beatPatternDao.insert(pattern)
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: BeatPatternDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BeatPatternDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BeatPatternDatabase::class.java,
                    "pattern_database"
                ).addCallback(BeatPatternDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}