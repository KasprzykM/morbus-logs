package symbiosproduction.com.morbuslogs.database.model.symptoms.temperature;

import java.util.HashMap;
import java.util.Map;

import symbiosproduction.com.morbuslogs.database.model.symptoms.AbstractSymptom;

public final class AbnormalTempSymptom extends AbstractSymptom {


    private Long tempInCelsius;

    private final String SYMPTOM_NAME = "Abnormal Temperature";



    public AbnormalTempSymptom(String dateOfOccurence,
                               Long duration,
                               String description,
                               Long tempInCelsius)
    {
        this.dateOfOccurrence = dateOfOccurence;
        this.duration = duration;
        this.description = description;
        this.tempInCelsius = tempInCelsius;
    }


    public Long getTempInCelsius() {
        return tempInCelsius;
    }

    @Override
    public String getSymptomName()
    {
        return SYMPTOM_NAME;
    }

    @Override
    public Map<String, Object> toMap() {

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("symptomName", SYMPTOM_NAME);
        dataMap.put("dateOfOccurrence", dateOfOccurrence);
        dataMap.put("duration", duration);
        dataMap.put("description", description);
        dataMap.put("tempInCelsius", tempInCelsius);

        return dataMap;
    }
}
