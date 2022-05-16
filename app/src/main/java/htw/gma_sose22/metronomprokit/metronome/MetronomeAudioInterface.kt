package htw.gma_sose22.metronomprokit.metronome

import htw.gma_sose22.metronomprokit.audio.AudioWriteable
import htw.gma_sose22.metronomprokit.audio.StatelessAudioControllable

interface MetronomeAudioInterface: AudioWriteable, StatelessAudioControllable {}