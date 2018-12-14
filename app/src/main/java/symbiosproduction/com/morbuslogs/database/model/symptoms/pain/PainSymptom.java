package symbiosproduction.com.morbuslogs.database.model.symptoms.pain;


import java.util.HashMap;
import java.util.Map;

import symbiosproduction.com.morbuslogs.database.model.symptoms.AbstractSymptom;

public final class PainSymptom extends AbstractSymptom {

    private String intensity;
    private PainType painType;

    private final String SYMPTOM_NAME = "Pain";

    @Override
    public String getSymptomName() {
        return SYMPTOM_NAME;
    }
    //@TODO: Add picture of pain area?

    public PainSymptom(String intensity,
                       PainType painType,
                       String description,
                       Long duration,
                       String timeUnit,
                       String dateOfOccurrence) {

        this.intensity = intensity;
        this.painType = painType;
        this.dateOfOccurrence = dateOfOccurrence;
        this.duration = calculateDuration(duration,timeUnit);
        this.description = description;
    }

    @Override
    public Map<String, Object> toMap() {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("symptomName", SYMPTOM_NAME);
        dataMap.put("dateOfOccurrence", dateOfOccurrence);
        dataMap.put("duration", duration);
        dataMap.put("description", description);
        dataMap.put("intensity", intensity);
        dataMap.put("painType", painType.toString());


        return dataMap;
    }
}
