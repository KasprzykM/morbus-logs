package symbiosproduction.com.morbuslogs.database.model.log;

import java.util.HashMap;
import java.util.Map;

import symbiosproduction.com.morbuslogs.database.DBCollection;
import symbiosproduction.com.morbuslogs.database.ToMap;
import symbiosproduction.com.morbuslogs.database.model.symptoms.AbstractSymptom;

public class SymptomsLog implements DBCollection,ToMap {

    private String dateOfSubmission;
    private String description;
    private Map<String, Map<String,Object>> mapOfSymptoms;
    private Integer sizeOfMap = 1;

    private final String DB_COLLECTION_LOG = "Logs";


    public SymptomsLog(String dateOfSubmission, String description) {
        this.dateOfSubmission = dateOfSubmission;
        this.description = description;
        mapOfSymptoms = new HashMap<>();
    }

    public String getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void addSymptom(AbstractSymptom abstractSymptom)
    {
        mapOfSymptoms.put( sizeOfMap.toString() ,abstractSymptom.toMap());
        sizeOfMap++;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("dateOfSubmission",dateOfSubmission);
        dataMap.put("description",description);
        dataMap.put("mapOfSymptoms",mapOfSymptoms);

        return dataMap;
    }

    @Override
    public String getCollection() {
        return DB_COLLECTION_LOG;
    }
}
