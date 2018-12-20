package symbiosproduction.com.morbuslogs.database.models.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import symbiosproduction.com.morbuslogs.database.DBCollection;
import symbiosproduction.com.morbuslogs.database.ToMap;
import symbiosproduction.com.morbuslogs.database.models.symptoms.AbstractSymptom;

public class SymptomsLog implements DBCollection,ToMap {

    private String dateOfSubmission;
    private Map<String, Map<String,Object>> mapOfSymptoms;
    private Integer sizeOfMap = 1;

    private static final String DB_SUB_COLLECTION = "userLogs";


    public SymptomsLog() {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
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

    public String getMainCollection() {
        return DB_MAIN_COLLECTION;
    }

    @Override
    public String getSubCollection()
    {
        return DB_SUB_COLLECTION;
    }
}
