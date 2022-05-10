package htw.gma_sose22.metronomprokit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Beat {

    @JsonProperty("tempo")
    Integer tempo;

    @JsonProperty("noteValue")
    Integer noteValue;

    @JsonProperty("noteCount")
    Integer noteCount;

    @JsonProperty("repetitions")
    Integer repetitions;

    @JsonProperty("emphasisedNotes")
    Integer[] emphasisedNotes;

    @JsonProperty("mutedNotes")
    Integer[] mutedNotes;

}
