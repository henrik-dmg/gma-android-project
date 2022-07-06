package htw.gma_sose22.metronomekit

import android.app.Application
import com.google.android.material.color.DynamicColors
import htw.gma_sose22.metronomekit.database.BeatPatternDatabase
import htw.gma_sose22.metronomekit.database.BeatPatternRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MetronomeProApp : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { BeatPatternDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { BeatPatternRepository(database.beatPatternDao()) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

}