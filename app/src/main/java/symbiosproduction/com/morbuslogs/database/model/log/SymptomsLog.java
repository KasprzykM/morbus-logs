package symbiosproduction.com.morbuslogs.database.model.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import symbiosproduction.com.morbuslogs.database.DBCollection;
import symbiosproduction.com.morbuslogs.database.ToMap;
import symbiosproduction.com.morbuslogs.database.model.symptoms.AbstractSymptom;

public class SymptomsLog implements DBCollection,ToMap {

    private String dateOfSubmission;
    private Map<String, Map<String,Object>> mapOfSymptoms;
    private Integer sizeOfMap = 1;

    private static final String DB_COLLECTION_PATIENTS_DATA = "patientsData";
    private static final String DB_COLLECTION_LOG = "userLogs";


    public SymptomsLog() {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        //this.dateOfSubmission = dateOfSubmission;
        this.dateOfSubmission = ISO_8601_FORMAT.format(Calendar.getInstance().getTime());
        mapOfSymptoms = new HashMap<>();
    }

    public String getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void addSymptomList(List<AbstractSymptom> listOfSymptoms)
    {
        for (AbstractSymptom symptom:
             listOfSymptoms) {
            addSymptom(symptom);
        }
    }

    public void addSymptom(AbstractSymptom abstractSymptom)
    {
        //TODO: Possibly change key so its easier to fetch
        mapOfSymptoms.put( sizeOfMap.toString() ,abstractSymptom.toMap());
        sizeOfMap++;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("dateOfSubmission",dateOfSubmission);
        dataMap.put("mapOfSymptoms",mapOfSymptoms);

        return dataMap;
    }

    @Override
    public String getCollection() {
        return DB_COLLECTION_PATIENTS_DATA;
    }

    public String getSubCollection()
    {
        return DB_COLLECTION_LOG;
    }
}
