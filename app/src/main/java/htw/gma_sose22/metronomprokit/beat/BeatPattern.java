package htw.gma_sose22.metronomprokit.beat;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeatPattern {

    @JsonProperty("patternName")
    String patternName;

    @JsonProperty("createdAt")
    Date createdAt;

}