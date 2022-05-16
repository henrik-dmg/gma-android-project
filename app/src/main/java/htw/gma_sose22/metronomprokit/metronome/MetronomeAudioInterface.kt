package htw.gma_sose22.metronomprokit.metronome

import htw.gma_sose22.metronomprokit.audio.AudioControllable
import htw.gma_sose22.metronomprokit.audio.AudioWriteable

interface MetronomeAudioInterface: AudioWriteable, AudioControllable {}