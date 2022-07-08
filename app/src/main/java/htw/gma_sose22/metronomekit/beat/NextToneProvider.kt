package htw.gma_sose22.metronomekit.beat

interface NextToneProvider {
    fun nextTone(): ToneMetadata?
}